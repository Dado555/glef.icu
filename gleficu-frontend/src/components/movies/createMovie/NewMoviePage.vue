<template>
  <div id="newMovie" class="container mx-auto">
<!--    <pre v-html="prettyJSON"></pre>-->
    <form-wizard @on-complete="onComplete"
                 validate-on-back
                 ref="wizard"
                 :start-index.sync="activeTabIndex"
                 shape="circle" color="rgba(245, 158, 11)" error-color="#ff4949">
      <h1 style="font-size: x-large; color: whitesmoke" slot="title">Add New Movie</h1>
      <h2 style="font-size: large;" slot="title">Define needed informations to add new movie</h2>

      <tab-content title="Movie and title links" icon="ti-video-clapper"
                   :before-change="() => validate('firstStep')" style="color: white">
        <first-step ref="firstStep" @on-validate="onStepValidate"></first-step>
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
  </div>
</template>

<script>
import FirstStep from "@/components/movies/createMovie/FirstStep";
import SecondStep from "@/components/movies/createMovie/SecondStep";
import prettyJSON from "@/components/movies/createMovie/prettyJson";

export default {
  name: "NewMoviePage",
  components: {
    FirstStep,
    SecondStep,
  },
  data() {
    return {
      finalModel: {},
      activeTabIndex: 0
    };
  },
  computed: {
    prettyJSON() {
      return prettyJSON(this.finalModel);
    }
  },
  methods: {
    onComplete() {
      alert("Yay. Done!");
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
    }
  }
};
</script>

<style>
#newMovie {
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

.panel {
  margin-bottom: 20px;
  background-color: #fff;
  border: 1px solid transparent;
  border-radius: 4px;
  -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.05);
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.05);
  border-color: #ddd;
}

.panel-heading {
  color: #333;
  background-color: #f5f5f5;
  border-color: #ddd;

  padding: 10px 15px;
  border-bottom: 1px solid transparent;
  border-top-left-radius: 3px;
  border-top-right-radius: 3px;
}

.panel-body {
  padding: 15px;
}
</style>