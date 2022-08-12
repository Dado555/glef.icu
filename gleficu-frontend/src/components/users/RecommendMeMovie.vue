<template>
  <div class="container mx-auto px-4 py-16">
    <button id="recommendMovieBtn" class="rounded bg-yellow-500 text-white cursor-auto">Recommend Me A Movie!</button>
    <br/> <br/>

    <div class="container mx-auto border-b border-gray-600 px-4 py-4">
      <h2 class="uppercase mt-5 text-yellow-500 text-lg font-semibold">
        Recommended Movies
      </h2>

      <div class="grid grid-cols-1 sm:grid-cols-3 md:grid-cols-3 lg:grid-cols-7 gap-8">
        <MovieItem
            :key="movie.id"
            v-for="movie in movies"
            :movie="movie"
            :genres="genres"
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
  </div>
</template>

<script>
import MovieItem from "../items/MovieItem";

let currentPage = 1;

export default {
  name: "RecommendMeMovie",
  components: {
    MovieItem,
  },

  data: function() {
    return {
      movies: [],
      genres: [],
    };
  },

  async mounted() {
    await this.fetchGenres();
    try {
      const response = await this.$http.get("/movie/popular");
      this.movies = response.data.results;
    } catch (error) {
      console.log(error);
    }
  },

  methods: {
    async fetchGenres() {
      try {
        const response = await this.$http.get("/genre/movie/list");
        this.genres = response.data.genres;
        console.log("Genres: " + this.genres.toString());
      } catch (error) {
        console.log(error);
      }
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
#recommendMovieBtn {
  margin-left: 10%;
  width: 1200px;
  height: 80px;
  cursor: pointer;
  font-size: larger;
}

</style>