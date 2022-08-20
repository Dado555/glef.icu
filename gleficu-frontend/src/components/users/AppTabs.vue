<template>
  <div :class="{'flex space-x-4': variant === 'horizontal',}">
    <ul
        class="list-none bg-blue-900 bg-opacity-30 p-1.5 rounded-lg text-center overflow-auto whitespace-nowrap"
        :class="{'flex items-center mb-6': variant === 'vertical',}">
      <li
          v-for="(tab, index) in tabList"
          :key="index"
          class="w-full px-4 py-1.5 rounded-lg"
          :class="{
          'bg-yellow-500 shadow-xl': index + 1 === activeTab,
          'text-white': index + 1 !== activeTab,
        }"
      >
        <label
            :for="`${_uid}${index}`"
            v-text="tab"
            class="cursor-pointer block"
        />
        <input
            :id="`${_uid}${index}`"
            type="radio"
            :name="`${_uid}-tab`"
            :value="index + 1"
            v-model="activeTab"
            class="hidden"
        />
      </li>
    </ul>
    <template v-for="(tab, index) in tabList" >
      <div :key="index" v-if="index + 1 === activeTab">
        <div v-if="activeTab === 1" class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
          <UserItem :key="user.id" v-for="user in users" :user="user" />
          <slot :name="`tabPanel-${index + 1}`" />
        </div>

        <div v-if="activeTab === 2" class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
          <UserItem :key="user.id" v-for="user in bannedUsers" :user="user" />
          <slot :name="`tabPanel-${index + 1}`" />
        </div>

        <div class="text-center mt-5">
          <a href="" v-on:click.prevent="previous()">
            Previous
          </a>
          <a href="" v-on:click.prevent="next()" class="ml-5">
            Next
          </a>
        </div>
      </div>
    </template>
  </div>
</template>

<script>
import UserItem from "@/components/items/UserItem";
import {userService} from "@/services/userService";

let currentPage = 1;

export default {
  name: "AppTabs",

  props: {
    variant: {
      type: String,
      required: false,
      default: () => "vertical",
      validator: (value) => ["horizontal", "vertical"].includes(value),
    },
  },

  data() {
    return {
      activeTab: 1,
      tabList: ["All users", "Banned users"], // "Ban user"
      users: [],
      size: 20,
      bannedUsers: []
    };
  },

  components: {
    UserItem
  },

  mounted() {
    this.getUsers(currentPage);
    this.getBannedUsers(currentPage);
    // this.fetchUsers(currentPage);
    // this.scroll();
  },

  methods: {
    async fetchUsers(page) {
      try {
        const response = await this.$http.get(
            "https://api.themoviedb.org/3/person/popular?page=" + page
        );
        this.users = response.data.results;
      } catch (error) {
        console.log(error);
      }
    },

    getUsers() {
      let params = {
        page: currentPage,
        size: this.size
      }

      userService.getUsersPage(params).then((response) => {
        // console.log(response);
        let page = response.data;
        this.users = page.users;
      });
    },

    getBannedUsers() {
      let params = {
        page: currentPage,
        size: this.size
      }

      userService.getBannedUsersPage(params).then((response) => {
        // console.log(response);
        let page = response.data;
        this.bannedUsers = page.users;
      });
    },

    scroll() {
      window.onscroll = () => {
        let bottomOfWindow =
            document.documentElement.scrollTop + window.innerHeight ===
            document.documentElement.offsetHeight;

        if (bottomOfWindow) {
          currentPage += 1;
          //this.fetchUsers((currentPage += 1));
          this.getUsers(currentPage);
        }
      };
    },

    next() {
      if(this.users.len < 20) {
        console.log("skip")
      } else {
        currentPage += 1;
        // this.fetchUsers(currentPage);
        this.getUsers(currentPage);
      }
    },
    previous() {
      if(currentPage === 1) {
        console.log("skip")
      } else {
        currentPage -= 1;
        // this.fetchUsers(currentPage);
        this.getUsers(currentPage);
      }
    },
  },
};
</script>