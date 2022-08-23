<template>
  <div class="relative" style="margin-right: 50px">
    <button @click="isOpen = !isOpen" class="focus:outline-none">
      <img src="@/assets/images/avatar.jpg" alt="" class="h-10 rounded-full mr-1"/>
    </button>

    <button @click="isOpen = false" v-if="isOpen"
      class="fixed top-0 right-0 bottom-0 w-full h-full cursor-default bg-black opacity-50 "></button>

    <div v-if="isOpen" class="absolute bg-white py-2 rounded-lg w-48 right-0 mr-3 shadow-xl">
      <a v-if="isUser()"
        @click="redirectProfile()"
        class="text-gray-500 block px-4 py-2 hover:bg-indigo-500 hover:text-white"
        >Account Settings
      </a>
      <a @click.prevent="logout"
        v-if="!isUnauthorized()"
        class="text-gray-500 block px-4 py-2 hover:bg-indigo-500 hover:text-white"
        >Logout
      </a>
      <a v-if="isUnauthorized()"
         class="text-gray-500 block px-4 py-2 hover:bg-indigo-500 hover:text-white"
         @click="redirectLogin()">
        Login
      </a>
    </div>
  </div>
</template>

<script>
import {authService} from "@/services/authService"

export default {
  mounted() {
    if(authService.isAuthenticated()) {
      this.loggedIn = true;
    }
  },

  created() {
    const handleEscape = (e) => {
      if (e.key === "Esc" || e.key === "Escape") {
        this.isOpen = false;
      }
    };

    document.addEventListener("keydown", handleEscape);

    this.$once("hook:beforeDestroy", () => {
      document.removeEventListener("keydown", handleEscape);
    });
  },
  data() {
    return {
      isOpen: false,
      loggedIn: true,
      roles: ""
    };
  },

  methods: {
    logout() {
      this.loggedIn = false;
      this.roles = "";
      this.$store.dispatch("setLogoutUser");
      this.$router.push({ name: "login" }).catch(() => {});
    },
    redirectProfile() {
      this.$router.push('/user/' + this.getUserId().toString()).catch(() => {});
    },
    redirectLogin() {
      this.$router.push('/login');
    },
    isUnauthorized() {
      return authService.isUnregistered()
    },
    isUser() {
      return authService.isUser();
    },
    getUserId() {
      return authService.getJwtField("userId");
    },
  },
};
</script>

<style></style>
