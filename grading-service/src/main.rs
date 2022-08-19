#[macro_use] extern crate rocket;
use rocket::{Build, Rocket};

mod funcs;
mod routes;

#[launch]
fn rocket() -> Rocket<Build> {
    std::thread::spawn(|| { funcs::create_database_comments().unwrap(); }).join().expect("Thread panicked.");
    rocket::build()
        .mount("/api/comments", routes![routes::create_comment, routes::delete_comment,
            routes::update_comment, routes::get_all_comments_for_movie, routes::get_bad_comments_for_user,
            routes::get_comment_for_user_and_movie, routes::create_complaint, routes::delete_complaint,
            routes::get_complaint_for_user_and_comment])
        .register("/api/comments", catchers![routes::not_found, routes::server_error])
}