<template>
  <div class="container mx-auto px-4 py-16">
    <voerro-tags-input id="recommendMovieSearch" element-id="tags" v-model="selectedTags" :existing-tags="existingTags"
                       :typeahead="true" style="margin-bottom: 30px" @change="onChange($event)"/>

    <button id="recommendMovieBtn" class="rounded bg-yellow-500 text-white cursor-auto" @click="recommendMovie()">Recommend Me A Movie!</button>
    <br/> <br/>

    <div class="container mx-auto border-b border-gray-600 px-4 py-4">
      <popular-movies/>
<!--      <h2 class="uppercase mt-5 text-yellow-500 text-lg font-semibold">-->
<!--        Recommended Movies-->
<!--      </h2>-->

<!--      <div class="grid grid-cols-1 sm:grid-cols-3 md:grid-cols-3 lg:grid-cols-7 gap-8">-->
<!--        <MovieItem-->
<!--            :key="movie.id"-->
<!--            v-for="movie in movies"-->
<!--            :movie="movie"-->
<!--            :genres="genres"-->
<!--        />-->
<!--      </div>-->

<!--      <div class="text-center mt-5">-->
<!--        <a href="" v-on:click.prevent="previous()">-->
<!--          Previous-->
<!--        </a>-->
<!--        <a href="" v-on:click.prevent="next()" class="ml-5">-->
<!--          Next-->
<!--        </a>-->
<!--      </div>-->
    </div>
  </div>
</template>

<script>
// import MovieItem from "../items/MovieItem";
import PopularMovies from "@/components/front/PopularMovies";
import {movieService} from "@/services/movieService";
import VoerroTagsInput from "@voerro/vue-tagsinput";
import {authService} from "@/services/authService";
import {javaMovieRecommend} from "@/services/javaMovieRecommend";

let currentPage = 1;

export default {
  name: "RecommendMeMovie",
  components: {
    PopularMovies,
    VoerroTagsInput
    // MovieItem,
  },

  data: function() {
    return {
      movies: [],
      searchResult: [],
      searchTerm: "",
      showSearchResult: false,
      searchResultType: "none",
      selectedTags: [],
      existingTags: [
        { key: 'web-development', value: 'Web Development' },
        { key: 'php', value: 'PHP' },
        { key: 'javascript', value: 'JavaScript' },
      ],
    };
  },

  mounted() {
    this.keyboardEvents();
  },

  methods: {
    recommendMovie() {
      let userId = authService.getJwtField("userId");
      if(this.selectedTags.length < 1 || userId === null) {
        alert("Input some tags!")
      } else {
        let tempTags = [];
        for(let t of this.selectedTags) {
          tempTags.push(t.key);
        }
        let payload = {
          tags: tempTags,
          userId: userId
        };
        console.log(payload);
        javaMovieRecommend.getMovieRecommends(payload).then((response) => {
          console.log(response.data);
        });
      }
    },

    onChange(value) {
      if(value.length > 2) {
        clearTimeout(this.debounce);
        this.debounce = setTimeout(() => {
          this.fetchSearch(value);
        }, 600);
      }
    },

    async fetchSearch(name) {
      try {
        movieService.searchTags(name).then((response) => {
          this.existingTags = [];
          for(let str of response.data) {
            let temp = {key: str, value: str};
            this.existingTags.push(temp);
          }
        });
      } catch (error) {
        console.log(error);
      }
    },

    next() {
      currentPage += 1;
      console.log(currentPage);
    },

    previous() {
      currentPage -= 1;
      console.log(currentPage);
    },
  },
}
</script>

<style scoped>
#recommendMovieBtn, #recommendMovieSearch{
  margin-left: 10%;
  width: 1200px;
  height: 80px;
  cursor: pointer;
  font-size: larger;
}

</style>