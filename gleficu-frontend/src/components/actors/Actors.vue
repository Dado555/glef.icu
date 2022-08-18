<template>
  <div class="container mx-auto px-4 py-16">
    <h2 class="text-yellow-500 text-lg font-semibold">
      POPULAR ACTORS
    </h2>

    <div
      class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-7 gap-6"
    >
      <ActorItem :key="actor.id" v-for="actor in this.actors" :actor="actor" />
    </div>

    <div class="text-center mt-5">
      <a href="" v-on:click.prevent="previous()">
        Previous
      </a>
      <a href="" v-on:click.prevent="next()" class="ml-5">
        Next
      </a>
    </div>
    <!-- <div class="flex justify-center">
      <div class="spinner">11</div>
    </div> -->
  </div>
</template>

<script>
let currentPage = 1;

import ActorItem from "../items/ActorItem";
export default {
  data() {
    return {
      actors: [],
    };
  },
  components: {
    ActorItem,
  },

  mounted() {
    this.fetchActors(currentPage);
    // this.scroll();
  },

  methods: {
    async fetchActors(page) {
      try {
        const response = await this.$http.get(
          "https://api.themoviedb.org/3/person/popular?page=" + page
        );
        this.actors = response.data.results;
        // this.actors.push(...response.data.results);
      } catch (error) {
        // console.log(error);
      }
    },

    scroll() {
      window.onscroll = () => {
        let bottomOfWindow =
          document.documentElement.scrollTop + window.innerHeight ===
          document.documentElement.offsetHeight;

        if (bottomOfWindow) {
          currentPage += 1;
          this.fetchActors((currentPage += 1));
        }
      };
    },

    next() {
      currentPage += 1;
      this.fetchActors(currentPage);
    },
    previous() {
      currentPage -= 1;
      this.fetchActors(currentPage);
    },
  },
};
</script>

<style></style>
