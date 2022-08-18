<template>
  <div id="movieItem" class="container mx-auto">
    <img :src="movie.Poster" style="padding-left: 40%" class="hover:opacity-75 tansition easy-in-out duration-150" alt="No Image"/>

    <h3>{{ movie.Title }}</h3>

    <div class="flex" style="padding-left: 45%">
      <svg class="fill-current text-yellow-500 w-4 h-4 mt-1" viewBox="0 0 24 24">
        <g data-name="Layer 2">
          <path
              d="M17.56 21a1 1 0 01-.46-.11L12 18.22l-5.1 2.67a1 1 0 01-1.45-1.06l1-5.63-4.12-4a1 1 0 01-.25-1 1 1 0 01.81-.68l5.7-.83 2.51-5.13a1 1 0 011.8 0l2.54 5.12 5.7.83a1 1 0 01.81.68 1 1 0 01-.25 1l-4.12 4 1 5.63a1 1 0 01-.4 1 1 1 0 01-.62.18z"
              data-name="star"
          />
        </g>
      </svg>
      <span class="ml-2">{{ movie.ImdbRating * 10 }}% | {{ movie.Released }} </span>
      <br />
    </div>

    <div class="flex" style="padding-left: 45%">
      <span class="text-sm text-gray-500">
        {{ movie.Genre }}
      </span>
    </div>

    <el-button @click="saveMovie()"
               style="background-color: rgba(245, 158, 11); margin-top: 20px; margin-bottom: 150px;
               border-color: rgba(245, 158, 11);color: white;">
      Save movie
    </el-button>
  </div>
</template>

<script>
import {movieService} from "@/services/movieService";

export default {
  name: "MovieItemOmdb",
  props: {
    movie: {
      required: true,
    },
  },
  methods: {
    saveMovie() {
      // console.log(this.movie);
      this.fetchKeywords(this.movie.ImdbID);
    },
    open() {
      this.$toast.open({
        message: "Movie saved successfully!",
        type: "success",
        duration: 3000,
        dismissible: true
      })
    },
    async fetchKeywords(movieId) {
      await this.$http.get("/movie/" + movieId + "/keywords").then((response) => {
        // console.log("Keywords: ");
        // console.log(response.data.keywords);

        this.movie.Tags = response.data.keywords;
        movieService.saveMovie(this.movie).then(() => {
          // console.log(response);
          // console.log("MOVIE SAVED");
          this.open();
        });
      });
    },
  }
}
</script>

<style scoped>
#movieItem {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  /*color: #2c3e50;*/
  color: whitesmoke;
  margin-top: 60px;
}

body {
  font-family: Menlo, Monaco, "Courier New", monospace;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  margin: 0;
}

pre {
  overflow: auto;
}
pre .string {
  color: #885800;
}
pre .number {
  color: blue;
}
pre .boolean {
  color: magenta;
}
pre .null {
  color: red;
}
pre .key {
  color: green;
}
</style>