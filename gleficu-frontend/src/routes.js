import VueRouter from "vue-router";
import Home from "./components/front/Home";
import Movie from "./components/movies/Movie";
import Actors from "./components/actors/Actors";
import ActorDetail from "./components/actors/ActorDetail";

// const roles = { USER, ADMIN.. };

let router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "home",
      component: Home,
      meta: {
        authenticated: false,
        authorities: [],
      },
    },
    {
      path: "/movie/:id",
      name: "movie",
      component: Movie,
      meta: {
        authenticated: false,
        authorities: [],
      },
    },
    {
      path: "/actors",
      name: "actors",
      component: Actors,
      meta: {
        authenticated: false,
        authorities: [],
      },
    },
    {
      path: "/actor/:id",
      name: "actor",
      component: ActorDetail,
      meta: {
        authenticated: false,
        authorities: [],
      },
    },
  ],
});

export default router;
