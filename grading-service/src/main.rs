#[macro_use] extern crate rocket;
use rocket::{Build, Rocket};
use rocket::http::Header;
use rocket::{Request, Response};
use rocket::fairing::{Fairing, Info, Kind};

mod funcs;
mod routes;

pub struct CORS;

#[rocket::async_trait]
impl Fairing for CORS {
    fn info(&self) -> Info {
        Info {
            name: "Attaching CORS headers to responses",
            kind: Kind::Response
        }
    }

    async fn on_response<'r>(&self, _request: &'r Request<'_>, response: &mut Response<'r>) {
        response.set_header(Header::new("Access-Control-Allow-Origin", "*"));
        response.set_header(Header::new("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS"));
        response.set_header(Header::new("Access-Control-Allow-Headers", "*"));
        // response.set_header(Header::new("Access-Control-Allow-Credentials", "true"));
    }
}

#[launch]
fn rocket() -> Rocket<Build> {
    std::thread::spawn(|| { funcs::create_database_comments().unwrap(); }).join().expect("Thread panicked.");
    rocket::build()
        .attach(CORS)
        .mount("/api/comments", routes![routes::create_comment, routes::delete_comment,
            routes::update_comment, routes::get_all_comments_for_movie, routes::get_bad_comments_for_user,
            routes::get_comment_for_user_and_movie, routes::create_complaint, routes::delete_complaint,
            routes::get_complaint_for_user_and_comment])
        .register("/api/comments", catchers![routes::not_found, routes::server_error])
}