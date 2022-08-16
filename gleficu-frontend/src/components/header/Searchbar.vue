<template>
  <div class="mt-5 flex relative">
    <input
      ref="searchBox"
      type="text"
      class="rounded-full bg-gray-600 px-7 w-50 h-10 mr-3 focus:outline-none focus:outline-shawod"
      placeholder="Search.."
      style="width: 350px"
      @input="debounceSearch"
      v-model="searchTerm"
      @focus="handleFocus"
    />
    <div class="absolute top-0">
      <svg
        class="fill-current w-4 text-gray-300 mt-2 ml-2 mt-3"
        viewBox="0 0 24 24"
      >
        <path
          class="heroicon-ui"
          d="M16.32 14.9l5.39 5.4a1 1 0 01-1.42 1.4l-5.38-5.38a8 8 0 111.41-1.41zM10 16a6 6 0 100-12 6 6 0 000 12z"
        />
      </svg>
    </div>

    <div class="absolute mt-12 rounded bg-gray-600 w-60 z-50">
      <ul class="mt-3" v-if="showSearchResult">
        <li :key="movie.imdbID" v-for="movie in searchResult">
          <router-link
            :to="`/movie/${movie.imdbID}`"
            @click.native="showSearchResult = false"
            class="flex items-center border-b border-gray-500 p-1"
          >
            <img :src="movie.poster" alt="" class="w-10" />
            <span class="ml-3">{{ movie.title }}</span>
          </router-link>
        </li>
      </ul>
      <ul class="px-3" v-if="searchResult.length === 0 && showSearchResult">
        <li>No result found for "{{ searchTerm }}"</li>
      </ul>
    </div>

    <account-drop-down/>
  </div>
</template>

<script>
import AccountDropDown from "@/components/header/AccountDropDown";
import {movieService} from "@/services/movieService";

export default {
  components: {AccountDropDown},
  data() {
    return {
      searchResult: [],
      searchTerm: "",
      showSearchResult: false,
    };
  },
  mounted() {
    this.keyboardEvents();
  },
  methods: {
    debounceSearch(event) {
      clearTimeout(this.debounce);
      this.debounce = setTimeout(() => {
        if (event.target.value.length > 3) {
          this.fetchSearch(event.target.value);
        } else {
          this.showSearchResult = false;
        }
      }, 600);
    },

    async fetchSearch(term) {
      try {
        let searchTerm = {
          title: term
        }
        movieService.searchMovies(searchTerm).then((response) => {
          console.log(response);
          console.log(response.data);
          this.searchResult = response.data;
          this.showSearchResult = response.data ? true : false
        });
        // const response = await this.$http.get("/search/movie?query=" + term);
        // this.searchResult = response.data.results;
        // this.showSearchResult = response.data.results ? true : false;
      } catch (error) {
        console.log(error);
      }
    },

    handleFocus() {
      if (this.searchTerm !== "") {
        this.showSearchResult = true;
      }
    },
    keyboardEvents() {
      let windowObj = this;

      window.addEventListener("click", (e) => {
        if (!this.$el.contains(e.target)) {
          this.showSearchResult = false;
        }
      });

      window.addEventListener("keypress", (e) => {
        if (e.keyCode === "47") {
          e.preventDefault();
          windowObj.$refs.searchBox.focus();
        }
      });
      window.addEventListener("keydown", (e) => {
        if (e.key === "Escape") {
          this.showSearchResult = false;
        }
      });
    },
  },
};
</script>

<style></style>
