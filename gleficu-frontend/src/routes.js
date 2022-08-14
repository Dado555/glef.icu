import VueRouter from "vue-router";
import Home from "./components/front/Home";
import Movie from "./components/movies/Movie";
import Actors from "./components/actors/Actors";
import ActorDetail from "./components/actors/ActorDetail";
import Users from "@/components/users/Users";
import UserDetail from "@/components/users/UserDetail";
import NewMoviePage from "@/components/movies/createMovie/NewMoviePage";
import Attachment from "@/components/mergeMovieAndSubtitle/Attachment";
import RecommendMeMovie from "@/components/users/RecommendMeMovie";

// const roles = { USER, ADMIN.. };

// import { authService } from "./services/authService"
import Login from "@/components/Login";

// function requireAuth (to, from, next) {
//   if (!authService.isAuthenticated()) {
//     this.$router.replace('/login')
//   } else {
//     next()
//   }
// }

let router = new VueRouter({
  mode: "history",
  routes: [
    {
      path: "/home",
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
      path: "/add-movie",
      name: "addMovie",
      component: NewMoviePage,
      meta: {}
    },
    {
      path: "/merge-movie",
      name: "mergeMovie",
      component: Attachment,
      meta: {}
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
    {
      path: "/users",
      name: "users",
      component: Users,
      meta: {
        authenticated: false,
        authorities: []
      }
    },
    {
      path: "/user/:id",
      name: "user",
      component: UserDetail,
      meta: {
        authenticated: false,
        authorities: [],
      },
    },
    {
      path: "/recommend-movie",
      name: "recommendMovie",
      component: RecommendMeMovie,
      meta: {}
    },
    {
      path: '/',
      name: 'login',
      component: Login
    },
    // {
    //   path: '/userinfo',
    //   name: 'userinfo',
    //   component: UserInfo,
    //   beforeEnter: requireAuth
    // }
  ],
});

export default router;
