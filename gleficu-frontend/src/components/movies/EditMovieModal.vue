<template>
  <div
      style="background-color: rgba(0, 0, 0, .5);"
      class="fixed top-0 left-0 w-full h-full flex items-center shadow-lg overflow-y-auto  z-50"
      tabindex="0"
      v-show="value"
      id="editMovie"
  >
    <div class="container mx-auto lg:px-32 rounded-lg overflow-y-auto">
      <div class="bg-gray-900 rounded">
        <div class="flex justify-end pr-4 pt-2">
          <button
              @click="close"
              class="text-3xl leading-none hover:text-gray-300"
          >
            &times;
          </button>
        </div>
        <div class="modal-body px-8 py-8">
          <div class="responsive-container overflow-hidden relative">
            <form-wizard @on-complete="onComplete"
                         validate-on-back
                         ref="wizard"
                         :start-index.sync="activeTabIndex"
                         shape="circle" color="rgba(245, 158, 11)" error-color="#ff4949">
              <h1 style="font-size: x-large; color: whitesmoke" slot="title">Edit movie</h1>
              <h2 style="font-size: large;" slot="title">Define needed informations to edit movie</h2>

              <tab-content title="Movie and title links" icon="ti-video-clapper"
                           :before-change="() => validate('firstStep')" style="color: white">
                <first-step ref="firstStep" @on-validate="onStepValidate" v-if="this.movieDb"
                :magnet-link="this.movieDb.torrentLinks" :subtitle-link="this.movieDb.titleLinks">
                </first-step>
                <!--        <el-button @click="forceClearError" style="background-color: rgb(32, 160, 255);border-color: rgb(32, 160, 255);color: white;">Try to clear the error</el-button>-->
              </tab-content>
              <tab-content title="Movie sites (IMDb or Rotten Tomatoes)" icon="ti-world"
                           :before-change="() => validate('secondStep')">
                <second-step ref="secondStep" @on-validate="onStepValidate"></second-step>
              </tab-content>
              <tab-content title="Last step" icon="ti-check">
                Your data
                <pre v-html="prettyJSON"></pre>
              </tab-content>

            </form-wizard>
<!--            <img :src="mediaURL" v-if="!isVideo" />-->
<!--            <iframe-->
<!--                class="responsive-iframe absolute top-0 left-0 w-full h-full"-->
<!--                style="border:0;"-->
<!--                allow="autoplay; encrypted-media"-->
<!--                allowfullscreen-->
<!--                v-if="isVideo"-->
<!--                :src="mediaURL"-->
<!--            ></iframe>-->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FirstStep from "@/components/movies/createMovie/FirstStep";
import SecondStep from "@/components/movies/createMovie/SecondStep";
import prettyJSON from "@/components/movies/createMovie/prettyJson";
import {movieService} from "@/services/movieService";

export default {
  name: "EditMovieModal",
  props: {
    value: {
      required: true,
    },
    imdbId: {
      required: true,
    }
  },
  methods: {
    close() {
      this.$emit("input", !this.value);
    },
    onComplete() {
      alert("Updating movie!");
      // console.log(this.finalModel);
      // update movie
      let payload = {
        imdbLink: this.finalModel.imdb,
        rottenLink: this.finalModel.rotten,
        torrentLinks: this.finalModel.downloadLink,
        titleLinks: this.finalModel.titleLink
      }
      // console.log(payload);
      movieService.updateMovie(this.imdbId, payload).then(() => {
        alert("Updated movie!");
      });

      this.close();
    },
    forceClearError() {
      this.$refs.wizard.tabs[this.activeTabIndex].validationError = null;
    },
    validate(ref) {
      return this.$refs[ref].validate();
    },
    onStepValidate(validated, model) {
      if (validated) {
        this.finalModel = { ...this.finalModel, ...model };
      }
    },
    getMovieDb(imdbId) {
      movieService.getMovieByImdbId(imdbId).then((response)=> {
        this.movieDb = response.data;
        // console.log(response.data);
      })
    },
  },

  data() {
    return {
      finalModel: {},
      activeTabIndex: 0,
      movieDb: null
    };
  },
  computed: {
    prettyJSON() {
      return prettyJSON(this.finalModel);
    }
  },
  mounted() {
    this.getMovieDb(this.imdbId);
  },
  components: {
    FirstStep,
    SecondStep,
  },
}
</script>

<style scoped>
#editMovie {
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