use rocket::fairing::{Fairing, Info, Kind};
use rocket::http::Header;
use rocket::{Build, Request, Response, Rocket};

#[macro_use]
extern crate rocket;

mod funcs;
mod routes;

#[launch]
fn rocket() -> Rocket<Build> {
    std::thread::spawn(|| { funcs::create_database_comments().unwrap(); }).join().expect("Thread panicked.");

    rocket::build()
        .attach(Cors)
        //.mount("/", routes![index, all_options, insert])
        .mount("/api/comments", routes![all_options ,routes::create_comment, routes::delete_comment,
            routes::update_comment, routes::get_all_comments_for_movie, routes::get_bad_comments_for_user,
            routes::get_comment_for_user_and_movie, routes::create_complaint, routes::delete_complaint,
            routes::get_complaint_for_user_and_comment])
        .register("/api/comments", catchers![routes::not_found, routes::server_error])
}

/// Catches all OPTION requests in order to get the CORS related Fairing triggered.
#[options("/<_..>")]
fn all_options() {
    /* Intentionally left empty */
}

pub struct Cors;

#[rocket::async_trait]
impl Fairing for Cors {
    fn info(&self) -> Info {
        Info {
            name: "Cross-Origin-Resource-Sharing Fairing",
            kind: Kind::Response,
        }
    }

    async fn on_response<'r>(&self, _request: &'r Request<'_>, response: &mut Response<'r>) {
        response.set_header(Header::new("Access-Control-Allow-Origin", "*"));
        response.set_header(Header::new(
            "Access-Control-Allow-Methods",
            "POST, PATCH, PUT, DELETE, HEAD, OPTIONS, GET",
        ));
        response.set_header(Header::new("Access-Control-Allow-Headers", "*"));
        response.set_header(Header::new("Access-Control-Allow-Credentials", "true"));
    }
}
