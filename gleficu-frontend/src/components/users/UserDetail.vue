<template>
  <div>
    <div class="container mx-auto px-4 py-16 flex">
      <div class="flex-none">
        <img src="@/assets/images/avatar.jpg" alt=""/>
      </div>

      <div class="ml-20">
        <h2 class="text-4xl font-semibold">
          {{ this.user.username }}
        </h2>

        <div class="flex">
          <svg class="fill-current text-gray-400 hover:text-white w-4" viewBox="0 0 448 512">
            <path
                d="M448 384c-28.02 0-31.26-32-74.5-32-43.43 0-46.825 32-74.75 32-27.695 0-31.454-32-74.75-32-42.842 0-47.218 32-74.5 32-28.148 0-31.202-32-74.75-32-43.547 0-46.653 32-74.75 32v-80c0-26.5 21.5-48 48-48h16V112h64v144h64V112h64v144h64V112h64v144h16c26.5 0 48 21.5 48 48v80zm0 128H0v-96c43.356 0 46.767-32 74.75-32 27.951 0 31.253 32 74.75 32 42.843 0 47.217-32 74.5-32 28.148 0 31.201 32 74.75 32 43.357 0 46.767-32 74.75-32 27.488 0 31.252 32 74.5 32v96zM96 96c-17.75 0-32-14.25-32-32 0-31 32-23 32-64 12 0 32 29.5 32 56s-14.25 40-32 40zm128 0c-17.75 0-32-14.25-32-32 0-31 32-23 32-64 12 0 32 29.5 32 56s-14.25 40-32 40zm128 0c-17.75 0-32-14.25-32-32 0-31 32-23 32-64 12 0 32 29.5 32 56s-14.25 40-32 40z"
            />
          </svg>
          <span class="ml-2 text-gray-400 text-sm">1988-12-16 (31)</span>
        </div>
        <p class="text-gray-300 mt-8">
          User with username <b>{{ this.user.username }}</b> has role {{ this.user.roleId === 1 ? "ADMIN" : "USER" }},
          banned status is <b>{{ this.user.banned }}</b>, can be banned status is <b> {{ this.user.canBeBanned }} </b>,
          user gender is <b> {{ this.user.gender }} </b>, {{ this.user.gender === "male" ? "his" : "her"}} age is
          <b>{{ this.user.age }} </b> and {{ this.user.gender === "male" ? "his" : "her"}} favourite tags are :
          <b> {{ this.user.favouriteTags }} </b>
        </p>

        <div class="mt-5" v-if="canEditSettings()">
          <a href="#" style="margin-left: 0" class="rounded bg-red-500 px-5 py-3 inline-flex text-black ml-5" @click.prevent="openEditProfile()">
            <img src="@/assets/images/Pencil-icon.png" alt=""/>
            <span class="ml-3">Edit Profile Settings</span>
          </a>
        </div>
      </div>
    </div>

    <div class="comment-space container mx-auto  border-b border-gray-600 px-4 py-4">
      <p style="font-size: x-large">Inappropriate comments ({{ this.commentsSizeDb }})</p>
    </div>
    <div v-if="commentsSizeDb > 0">
      <div class="container mx-auto  border-b border-gray-600 px-4 py-4" :key="comment.id" v-for="comment in commentsDb">
        <Comment @commentUpdated="commentUpdated()" class="message" :comment-db="comment"/>
        <button class="px-4 py-1.5 rounded bg-red-700 bg-white shadow-xl cursor-pointer" v-if="adminPrivileges()" @click="banUser()">Ban user</button>
        <button v-if="canUnban()" class="px-4 py-1.5 rounded bg-red-700 bg-white shadow-xl cursor-pointer" @click="unbanUser()">Unban user</button>
      </div>
    </div>

    <WatchedMovies v-if="this.userId !== -1" :user-id="this.userId"/>

    <WishlistMovies v-if="this.userId !== -1" :user-id="this.userId"/>

    <EditProfileModal v-if="this.userId !== -1" v-model="editProfile" :user-id="this.userId" v-on:updatedUser="refresh()"/>
    <!--  -->
  </div>
</template>

<script>
import WatchedMovies from "@/components/users/WatchedMovies";
import WishlistMovies from "@/components/users/WishlistMovies";
import EditProfileModal from "@/components/users/EditProfileModal";
import Comment from "@/components/comments/Comment";
import {authService} from "@/services/authService";
import {userService} from "@/services/userService";
import {commentService} from "@/services/commentService";

export default {
  name: "UserDetail",
  components: {Comment, EditProfileModal, WishlistMovies, WatchedMovies},
  data() {
    return {
      user: {},
      editProfile: false,
      userId: -1,
      commentsDb: [],
      commentsSizeDb: 0,
    };
  },

  mounted() {
    // console.log("User ID: " + this.getUserId();
    this.userId = parseInt(this.$route.params.id);
    this.getUserDb(parseInt(this.$route.params.id)); // parseInt(this.getUserId())
    this.getUserInappropriateComments(parseInt(this.$route.params.id));
  },

  methods: {
    openEditProfile() {
      this.editProfile = true;
    },

    getUserId() {
      return authService.getJwtField("userId");
    },

    canEditSettings() {
      let username = authService.getJwtField("username");
      return username === this.user.username;
    },

    getUserDb(id) {
      userService.getById(id).then((response) => {
        this.user = response.data;
        // console.log(response.data);
      })
    },

    adminPrivileges() {
      return authService.getJwtField("authority") === "ADMIN" && this.commentsSizeDb > 0 && !this.user.banned;
    },

    refresh() {
      this.getUserDb(this.userId);
    },

    commentUpdated() {
      this.getUserInappropriateComments();
    },

    getUserInappropriateComments(userId) {
      commentService.getBadCommentsByUser(userId).then((response) => {
        this.commentsDb = response.data;
        this.commentsSizeDb = response.data.length;
      });
    },

    banUser() {
      userService.banUser(this.user.username, true).then(() => {
        alert("User " + this.user.username + " banned!");
        this.refresh();
      });
    },

    canUnban() {
      return this.user.banned && authService.getJwtField("authority") === "ADMIN";
    },

    unbanUser() {
      userService.banUser(this.user.username, false).then(() => {
        alert("User " + this.user.username + " unbanned!");
        this.refresh();
      })
    }
  },
}
</script>

<style scoped>

</style>