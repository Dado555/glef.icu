use rocket::response::{content, status};
use rocket::http::Status;
use rocket::{catch, post, get, put, delete};
use crate::funcs;

#[catch(404)]
pub fn not_found() -> content::RawJson<String> {
    content::RawJson(serde_json::to_string(&funcs::Response{message: "Not found.".to_string(), status_code: 404}).unwrap())
}

#[catch(500)]
pub fn server_error() -> content::RawJson<String> {
    content::RawJson(serde_json::to_string(&funcs::Response{message: "Internal server error.".to_string(), status_code: 500}).unwrap())
}

#[post("/save", format="json", data="<comment_creation_dto>")]
pub fn create_comment(comment_creation_dto: rocket::serde::json::Json<funcs::CommentCreationDTO>) -> status::Custom<content::RawJson<String>> {
    std::thread::spawn(move || {
        status::Custom(Status::Created, content::RawJson(funcs::create_comment(comment_creation_dto).unwrap()))
    }).join().unwrap()
}

#[delete("/delete?<comment_id>")]
pub fn delete_comment(comment_id: i32) -> content::RawJson<String> {
    std::thread::spawn(move || {
        content::RawJson(funcs::delete_comment(comment_id).unwrap())
    }).join().unwrap()
}

#[put("/update", format="json", data="<comment_update_dto>")]
pub fn update_comment(comment_update_dto: rocket::serde::json::Json<funcs::CommentUpdateDTO>) -> content::RawJson<String> {
    std::thread::spawn(move || {
        content::RawJson(funcs::update_comment(comment_update_dto).unwrap())
    }).join().unwrap()
}

#[get("/getByMovie?<movie_id>")]
pub fn get_all_comments_for_movie(movie_id: String) -> content::RawJson<String> {
    std::thread::spawn(move || {
        content::RawJson(funcs::get_all_comments_for_movie(movie_id).unwrap())
    }).join().unwrap()
}

#[get("/getBadByUser?<user_id>")]
pub fn get_bad_comments_for_user(user_id: i32) -> content::RawJson<String> {
    std::thread::spawn(move || {
        content::RawJson(funcs::get_bad_comments_for_user(user_id).unwrap())
    }).join().unwrap()
}

#[get("/getByUserAndMovie?<user_id>&<movie_id>")]
pub fn get_comment_for_user_and_movie(user_id: i32, movie_id: String) -> content::RawJson<String> {
    std::thread::spawn(move || {
        content::RawJson(funcs::get_comment_for_user_and_movie(user_id, movie_id).unwrap())
    }).join().unwrap()
}

#[post("/addComplaint", format="json", data="<complaint_creation_dto>")]
pub fn create_complaint(complaint_creation_dto: rocket::serde::json::Json<funcs::ReportCreateDTO>) -> status::Custom<content::RawJson<String>> {
    std::thread::spawn(move || {
        status::Custom(Status::Created, content::RawJson(funcs::report_comment(complaint_creation_dto).unwrap()))
    }).join().unwrap()
}

#[delete("/deleteComplaint?<complaint_id>&<comment_id>")]
pub fn delete_complaint(complaint_id: i32, comment_id: i32) -> content::RawJson<String> {
    std::thread::spawn(move || {
        content::RawJson(funcs::delete_report(complaint_id, comment_id).unwrap())
    }).join().unwrap()
}

#[get("/getComplaint?<user_id>&<comment_id>")]
pub fn get_complaint_for_user_and_comment(user_id: i32, comment_id: i32) -> content::RawJson<String> {
    std::thread::spawn(move || {
        content::RawJson(funcs::get_report_for_user_and_comment(user_id, comment_id).unwrap())
    }).join().unwrap()
}