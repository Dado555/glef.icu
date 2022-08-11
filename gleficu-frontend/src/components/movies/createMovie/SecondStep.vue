<template>
  <el-form :model="model" :rules="rules" ref="form">
    <el-form-item label="IMDb link" prop="imdb">
      <el-input type="url" v-model="model.imdb" placeholder="https://www.imdb.com/title/tt0099685/"></el-input>
    </el-form-item>
    <el-form-item label="Rotten Tomatoes link" prop="rotten">
      <el-input type="url" v-model="model.rotten" placeholder="https://www.rottentomatoes.com/m/1032176-goodfellas"></el-input>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: "SecondStep",
  data(){
    return {
      model: {
        imdb: '',
        rotten: ''
      },
      rules: {
        imdb: [{
          required: true,
          message: 'IMDb or Rotten Tomatoes link is required',
          trigger: 'change'
        },
          {
            type: 'url',
            message: 'Invalid url',
            trigger: 'change'
          }],
        rotten: [{
          required: false,
          message: 'IMDb or Rotten Tomatoes link is required',
          trigger: 'blur'
        },
          {
            type: 'url',
            message: 'Invalid url',
            trigger: 'change'
          }],
      }
    }},
  methods: {
    validate() {
      return new Promise((resolve, ) => { // reject
        this.$refs.form.validate((valid) => {
          this.$emit('on-validate', valid, this.model)
          resolve(valid);
        });
      })

    }
  }

}
</script>

<style>
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
