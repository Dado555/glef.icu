<template>
  <div class="flex">
    <router-link to="/">
      <img src="@/assets/images/logo.png" alt="" style="cursor: pointer"/>
    </router-link>

    <ul class="flex mt-5">
      <li>
        <router-link to="/" class="ml-5">
          Movies
        </router-link>
      </li>
      <li>
        <router-link v-if="isAdmin()" to="/add-movie" class="ml-5">
          Add New Movie
        </router-link>
      </li>
      <li>
        <router-link to="/actors" class="ml-5">
          Actors
        </router-link>
      </li>
      <li>
        <router-link v-if="isAdmin()" to="/users" class="ml-5">
          Users
        </router-link>
      </li>
      <li>
        <router-link v-if="isUser()" to="/merge-movie" class="ml-5">
          Merge M&S
        </router-link>
      </li>
      <li>
        <router-link v-if="isUser()" to="/recommend-movie" class="ml-5">
          Recommend Me Movie
        </router-link>
      </li>

      <!--      <li>-->
<!--        <router-link to="/profile" class="ml-5">-->
<!--          Profile-->
<!--        </router-link>-->
<!--      </li>-->
    </ul>
  </div>
</template>

<script>
import { authService } from '@/services/authService'

export default {
  methods: {
    logout() {
      authService.logout(this)
    },
    isAdmin() {
      return authService.isAdmin()
    },
    isUser() {
      return authService.isUser()
    }
  }
};
</script>

<style></style>
