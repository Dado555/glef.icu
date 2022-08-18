<template>
  <div class="container mx-auto border-b border-gray-600 px-4 py-4">
    <h2 class="uppercase mt-5 text-yellow-500 text-lg font-semibold">
      Watched Movies
    </h2>

    <div class="grid grid-cols-1 sm:grid-cols-3 md:grid-cols-3 lg:grid-cols-7 gap-8">
      <MovieItem
          :key="movie.imdbID"
          v-for="movie in movies"
          :movie="movie"
      />
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

<script>
import MovieItem from "../items/MovieItem";
import {listsService} from "@/services/listsService";

let currentPage = 1;

export default {
  name: "WatchedMovies",
  components: {
    MovieItem,
  },
  props: {
    userId: {
      required: true,
    }
  },
  data: function() {
    return {
      movies: [],
    };
  },
  mounted() {
    this.fetchMovies();
  },
  methods: {
    fetchMovies() {
      let params = {
        page: currentPage,
        size: 20,
        userId: this.userId,
      }
      listsService.getWatchlist(params).then((response) => {
        this.movies = response.data.watchlist;
        // console.log(response);
      });
    },
    next() {
      currentPage += 1;
      console.log(currentPage);
      // this.fetchMovies(currentPage);
    },
    previous() {
      currentPage -= 1;
      console.log(currentPage);
      // this.fetchMovies(currentPage);
    },
  },
}
</script>

<style scoped>

</style>