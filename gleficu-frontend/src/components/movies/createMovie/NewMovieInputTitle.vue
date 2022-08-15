<template>
  <div id="newMovie" class="container mx-auto">
    <h1 style="font-size: x-large; color: whitesmoke" slot="title">Add New Movie</h1>
    <h2 style="font-size: large;" slot="title">Define title and year when it's released</h2>

    <el-form :model="model" :rules="rules" ref="form">
      <el-form-item label="Title of your movie: " prop="title">
        <el-input v-model="model.title" placeholder="goodfellas"></el-input>
      </el-form-item>
      <el-form-item label="Year released: " prop="year">
        <el-input v-model="model.year" placeholder="1990"></el-input>
      </el-form-item>
    </el-form>

    <el-button @click="submit()" style="background-color: rgba(245, 158, 11);border-color: rgba(245, 158, 11);color: white;">Submit</el-button>

    <div class="container mx-auto">
      <h2 class="uppercase mt-5 text-yellow-500 text-lg font-semibold">
        Result movie
      </h2>

      <movie-item-omdb movie=""/>
    </div>
  </div>
</template>

<script>
import MovieItemOmdb from "@/components/items/MovieItemOmdb";
export default {
  name: "NewMovieInputTitle",
  components: {MovieItemOmdb},
  data() {
    return {
      model: {
        title: '',
        year: '',
      },
      rules: {
        title: [{
          required: true,
          message: 'Title is required',
          trigger: 'blur'
        }],
        titleLink: [{
          required: true,
          message: 'Year is required',
          trigger: 'blur'
        }],
      }
    }
  },
  methods: {
    validate() {
      return new Promise((resolve, ) => { // reject
        this.$refs.form.validate((valid) => {
          this.$emit('on-validate', valid, this.model)
          resolve(valid);
        });
      })
    },
    submit() {

    }
  }
}
</script>

<style scoped>
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