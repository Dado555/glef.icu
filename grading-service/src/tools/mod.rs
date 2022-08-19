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

pub fn create_database_comments() -> Result<(), Error> {

    let mut db_client = Client::connect("postgresql://postgres:root@localhost:5432/commentService", NoTls)?;

    // db_client.batch_execute("DROP TABLE IF EXISTS comments")?;

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