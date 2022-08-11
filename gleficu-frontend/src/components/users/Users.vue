<template>
  <div class="container mx-auto px-4 py-16">
    <app-tabs class="w-12/12 lg:w-12/12 mx-auto mb-16" :tabList="tabList" :users="users" :variant="'vertical'">
      <template/>
<!--      <template v-slot:tabPanel-1> Content 1 </template>-->
<!--      <template v-slot:tabPanel-2> Content 2 </template>-->
<!--      <template v-slot:tabPanel-3> Content 3 </template>-->
    </app-tabs>
<!--    <h2 class="text-yellow-500 text-lg font-semibold">-->
<!--      All users-->
<!--    </h2>-->

<!--    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">-->
<!--      <UserItem :key="user.id" v-for="user in this.users" :user="user" />-->
<!--    </div>-->

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

<script>
// import UserItem from "@/components/items/UserItem";
import AppTabs from "@/components/users/AppTabs";

let currentPage = 1;

export default {
  name: "Users",

  data() {
    return {
      users: [],
      tabList: ["All users", "Ban user", "Banned users"]
    };
  },

  components: {
    // UserItem,
    AppTabs
  },

  mounted() {
    this.fetchUsers(currentPage);
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

    scroll() {
      window.onscroll = () => {
        let bottomOfWindow =
            document.documentElement.scrollTop + window.innerHeight ===
            document.documentElement.offsetHeight;

        if (bottomOfWindow) {
          currentPage += 1;
          this.fetchUsers((currentPage += 1));
        }
      };
    },

    next() {
      currentPage += 1;
      this.fetchUsers(currentPage);
    },
    previous() {
      currentPage -= 1;
      this.fetchUsers(currentPage);
    },
  },
}
</script>

<style scoped>

</style>