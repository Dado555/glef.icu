<template>
  <div class="container mx-auto border-b border-gray-600 px-4 py-4">
    <h2 class="uppercase mt-5 text-yellow-500 text-lg font-semibold">
      Movies On Wishlist
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
</template>

<script>
import MovieItem from "@/components/items/MovieItem";

let currentPage = 1;

export default {
  name: "WishlistMovies",
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

</style>