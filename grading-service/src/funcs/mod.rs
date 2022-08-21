use postgres::{Client, NoTls, Error};
use serde::{Serialize, Deserialize};
use chrono::prelude::{DateTime, Utc};

#[derive(Serialize, Deserialize)]
pub struct Comment {
    pub id: i32,
    pub movie_id: String,
    pub user_id: i32,
    pub created_at: String,
    pub updated_at: String,
    pub deleted_at: String,
    pub text: String,
    pub like_stars: i32,
    pub reports_number: i32
}

#[derive(Serialize, Deserialize)]
pub struct CommentCreationDTO {
    pub movie_id: String,
    pub user_id: i32,
    pub text: String,
    pub like_stars: i32
}

#[derive(Serialize, Deserialize)]
pub struct CommentUpdateDTO {
    pub id: i32,
    pub text: String,
    pub like_stars: i32,
    pub reports_number: i32
}

#[derive(Serialize, Deserialize)]
pub struct CommentGetDTO {
    pub user_id: i32,
    pub movie_id: String
}

#[derive(Serialize, Deserialize)]
pub struct CommentGetForMovieDTO {
    pub movie_id: String
}

#[derive(Serialize, Deserialize)]
pub struct CommentDeleteDTO {
    pub id: i32,
    pub user_id: i32
}

#[derive(Serialize, Deserialize)]
pub struct CommentReportDTO {
    pub id: i32,
    pub reports_number: i32
}

#[derive(Serialize)]
pub struct Response {
    pub message: String,
    pub status_code: i32
}

#[derive(Serialize, Deserialize)]
pub struct Report {
    pub id: i32,
    pub comment_id: i32,
    pub user_id: i32,
    pub created_at: String,
    pub deleted_at: String
}

#[derive(Serialize, Deserialize)]
pub struct ReportCreateDTO {
    pub comment_id: i32,
    pub user_id: i32
}

pub fn create_database_comments() -> Result<(), Error> {

    let mut db_client = Client::connect("postgresql://postgres:root@localhost:5432/commentService", NoTls)?;

    // db_client.batch_execute("DROP TABLE IF EXISTS comments")?;

    db_client.batch_execute("
        CREATE TABLE IF NOT EXISTS reports (
            id SERIAL PRIMARY KEY,
            comment_id INTEGER,
            user_id INTEGER,
            created_at VARCHAR,
            deleted_at VARCHAR
        )
    ")?;

    db_client.batch_execute("
        CREATE TABLE IF NOT EXISTS comments (
            id SERIAL PRIMARY KEY,
            movie_id VARCHAR,
            user_id INTEGER,
            created_at VARCHAR,
            updated_at VARCHAR,
            deleted_at VARCHAR,
            text VARCHAR,
            like_stars INTEGER,
            reports_number INTEGER
        )
    ")?;

    db_client.close()?;

    Ok(())
}

pub fn create_comment(comment: rocket::serde::json::Json<CommentCreationDTO>) -> Result<String, Error> {
    let mut db_client = Client::connect("postgresql://postgres:root@localhost:5432/commentService", NoTls)?;
    let time_now = std::time::SystemTime::now();
    let formatter: DateTime<Utc> = time_now.clone().into();
    let iso_format_date = formatter.format("%+").to_string();
    db_client.execute("INSERT INTO comments (movie_id, user_id, created_at, updated_at, deleted_at, text, like_stars, reports_number) VALUES ($1, $2, $3, $4, $5, $6, $7, $8)", &[&comment.movie_id, &comment.user_id, &iso_format_date, &(""), &(""), &comment.text, &comment.like_stars, &(0)])?;
    db_client.close()?;

    Ok(serde_json::to_string(&Response {message: "Comment created successfully!".to_string(), status_code: 201}).unwrap())
}

pub fn update_comment(comment: rocket::serde::json::Json<CommentUpdateDTO>) -> Result<String, Error> {
    let mut db_client = Client::connect("postgresql://postgres:root@localhost:5432/commentService", NoTls)?;
    let time_now = std::time::SystemTime::now();
    let formatter: DateTime<Utc> = time_now.clone().into();
    let iso_format_date = formatter.format("%+").to_string();
    db_client.execute("UPDATE comments SET updated_at = $1, text = $2, like_stars = $3, reports_number = $4 WHERE id = $5", &[&iso_format_date, &comment.text, &comment.like_stars, &comment.reports_number, &comment.id])?;
    db_client.close()?;

    Ok(serde_json::to_string(&Response {message: "Comment updated successfully!".to_string(), status_code: 201}).unwrap())
}

pub fn delete_comment(comment_id: i32) -> Result<String, Error> {
    let mut db_client = Client::connect("postgresql://postgres:root@localhost:5432/commentService", NoTls)?;
    db_client.execute("DELETE FROM comments WHERE id = $1", &[&comment_id])?;
    db_client.close()?;

    Ok(serde_json::to_string(&Response {message: "Comment deleted successfully!".to_string(), status_code: 200}).unwrap())
}

pub fn report_comment(report: rocket::serde::json::Json<ReportCreateDTO>) -> Result<String, Error> {
    let mut db_client = Client::connect("postgresql://postgres:root@localhost:5432/commentService", NoTls)?;
    let time_now = std::time::SystemTime::now();
    let formatter: DateTime<Utc> = time_now.clone().into();
    let iso_format_date = formatter.format("%+").to_string();
    db_client.execute("INSERT INTO reports (comment_id, user_id, created_at, deleted_at) VALUES ($1, $2, $3, $4)", &[&report.comment_id, &report.user_id, &iso_format_date, &("")])?;
    
    for row in db_client.query("SELECT reports_number FROM comments WHERE id = $1", &[&report.comment_id])? {
        let rep_num: i32 = row.get(0);
        let new_rep_num: i32 = rep_num + 1;
        db_client.execute("UPDATE comments SET reports_number = $1 WHERE id = $2", &[&new_rep_num, &report.comment_id])?;
    }
    
    db_client.close()?;

    Ok(serde_json::to_string(&Response {message: "Comment reported successfully!".to_string(), status_code: 200}).unwrap())
}

pub fn delete_report(report_id: i32, comment_id: i32) -> Result<String, Error> {
    let mut db_client = Client::connect("postgresql://postgres:root@localhost:5432/commentService", NoTls)?;
    db_client.execute("DELETE FROM reports WHERE id = $1", &[&report_id])?;

    for row in db_client.query("SELECT reports_number FROM comments WHERE id = $1", &[&comment_id])? {
        let rep_num: i32 = row.get(0);
        let new_rep_num: i32 = rep_num - 1;
        db_client.execute("UPDATE comments SET reports_number = $1 WHERE id = $2", &[&new_rep_num, &comment_id])?;
    }

    db_client.close()?;

    Ok(serde_json::to_string(&Response {message: "Report deleted successfully!".to_string(), status_code: 200}).unwrap())
}

pub fn get_report_for_user_and_comment(user_id_in: i32, comment_id_in: i32) -> Result<String, Error> {
    let mut db_client = Client::connect("postgresql://postgres:root@localhost:5432/commentService", NoTls)?;

    let mut ret: Vec<Report> = vec![];
    for row in db_client.query("SELECT * FROM reports WHERE user_id = $1 AND comment_id = $2", &[&user_id_in, &comment_id_in])? {
        let id: i32 = row.get(0);
        let comment_id: i32 = row.get(1);
        let user_id: i32 = row.get(2);
        let created_at: &str = row.get(3);
        let deleted_at: &str = row.get(4);

        ret.push(Report {
            id,
            comment_id,
            user_id,
            created_at: (created_at.to_string()),
            deleted_at: (deleted_at.to_string()),
        });
    }
    db_client.close()?;

    Ok(serde_json::to_string(&ret).unwrap())
}

pub fn get_comment_for_user_and_movie(user_id_in: i32, movie_id_in: String) -> Result<String, Error> {
    let mut db_client = Client::connect("postgresql://postgres:root@localhost:5432/commentService", NoTls)?;

    let mut ret: Vec<Comment> = vec![];
    for row in db_client.query("SELECT * FROM comments WHERE user_id = $1 AND movie_id = $2", &[&user_id_in, &movie_id_in])? {
        let id: i32 = row.get(0);
        let movie_id: String = row.get(1);
        let user_id: i32 = row.get(2);
        let created_at: &str = row.get(3);
        let updated_at: &str = row.get(4);
        let deleted_at: &str = row.get(5);
        let text : &str = row.get(6);
        let like_stars: i32 = row.get(7);
        let reports_number: i32 = row.get(8);

        ret.push(Comment {
            id,
            movie_id,
            user_id,
            created_at: (created_at.to_string()),
            updated_at: (updated_at.to_string()),
            deleted_at: (deleted_at.to_string()),
            text: (text.to_string()),
            like_stars,
            reports_number
        });
    }
    db_client.close()?;

    Ok(serde_json::to_string(&ret).unwrap())
}

pub fn get_all_comments_for_movie(movie_id_in: String) -> Result<String, Error> {
    let mut db_client = Client::connect("postgresql://postgres:root@localhost:5432/commentService", NoTls)?;

    let mut ret: Vec<Comment> = vec![];
    for row in db_client.query("SELECT * FROM comments WHERE movie_id = $1", &[&movie_id_in])? {
        let id: i32 = row.get(0);
        let movie_id: String = row.get(1);
        let user_id: i32 = row.get(2);
        let created_at: &str = row.get(3);
        let updated_at: &str = row.get(4);
        let deleted_at: &str = row.get(5);
        let text : &str = row.get(6);
        let like_stars: i32 = row.get(7);
        let reports_number: i32 = row.get(8);

        ret.push(Comment {
            id,
            movie_id,
            user_id,
            created_at: (created_at.to_string()),
            updated_at: (updated_at.to_string()),
            deleted_at: (deleted_at.to_string()),
            text: (text.to_string()),
            like_stars,
            reports_number
        });
    }
    db_client.close()?;

    Ok(serde_json::to_string(&ret).unwrap())
}

pub fn get_bad_comments_for_user(user_id_in: i32) -> Result<String, Error> {
    let mut db_client = Client::connect("postgresql://postgres:root@localhost:5432/commentService", NoTls)?;

    let mut ret: Vec<Comment> = vec![];
    for row in db_client.query("SELECT * FROM comments WHERE user_id = $1 AND reports_number > 0", &[&user_id_in])? {
        let id: i32 = row.get(0);
        let movie_id: String = row.get(1);
        let user_id: i32 = row.get(2);
        let created_at: &str = row.get(3);
        let updated_at: &str = row.get(4);
        let deleted_at: &str = row.get(5);
        let text : &str = row.get(6);
        let like_stars: i32 = row.get(7);
        let reports_number: i32 = row.get(8);

        ret.push(Comment {
            id,
            movie_id,
            user_id,
            created_at: (created_at.to_string()),
            updated_at: (updated_at.to_string()),
            deleted_at: (deleted_at.to_string()),
            text: (text.to_string()),
            like_stars,
            reports_number
        });
    }
    db_client.close()?;

    Ok(serde_json::to_string(&ret).unwrap())
}