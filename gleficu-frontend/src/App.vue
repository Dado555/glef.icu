<template>
  <div id="app">
    <div class="flex justify-between border-b border-gray-500" v-if="!loginRoute()">
      <Navbar />
      <Searchbar v-if="searchShow()"/> <!--@logout="logout"-->
    </div>

    <router-view/> <!--v-if="loggedIn"-->
<!--    <login @login="loggedIn=true" v-if="!loggedIn"/>-->
  </div>
</template>

<script>
import Navbar from "./components/header/Navbar";
import Searchbar from "./components/header/Searchbar";
import {authService} from "@/services/authService";
// import Login from "./components/Login.vue";

export default {
  name: "App",
  components: {
    // Login,
    Navbar,
    Searchbar,
  },

  data() {
    return {
      loggedIn: false,
      roles: ""
    };
  },

  methods: {
    isAuthenticated() {
      return authService.isAuthenticated()
    },
    searchShow() {
      return this.$route.name === "home" || this.$route.name === "users"
    },
    loginRoute() {
      console.log(this.$route.name)
      return this.$route.name === "login"
      // return this.$route
    }
  }
};
</script>

<style>

</style>
