<template>
  <div class="mx-5">
    <h2 class="uppercase mt-5 text-yellow-500 text-lg font-semibold">
      Popular Movies
    </h2>

    <div class="grid grid-cols-1 sm:grid-cols-3 md:grid-cols-3 lg:grid-cols-6 gap-8">
      <MovieItem
        :key="movie.id"
        v-for="movie in movies"
        :movie="movie"
      />
    </div>
  </div>
</template>

<script>
import MovieItem from "../items/MovieItem";
import {movieService} from "@/services/movieService";

export default {
  components: {
    MovieItem,
  },

  data: function() {
    return {
      movies: [],
      genres: [],
      page: 1,
      size: 20
    };
  },
  async mounted() {
    this.getMovies()
    // this.getAllMovies();
  },

  methods: {
    getMovies() {
      let params = {
        page: this.page,
        size: this.size
      }

      movieService.getMovies(params).then((response) => {
        console.log(response);
        let page = response.data;
        this.movies = page.movies;
      });
    },
    getAllMovies() {
      movieService.getAllMovies().then((response) => {
        console.log(response);
        this.movies = response.data.movies;
      })
    }
  },
};
</script>

<style></style>
