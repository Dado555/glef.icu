<template>
  <el-form :model="model" :rules="rules" ref="form">
    <el-form-item label="Download link (yts.mx or equivalent)" prop="downloadLink">
      <el-input v-model="model.downloadLink" placeholder="https://yts.mx/movies/goodfellas-1990"></el-input>
    </el-form-item>
    <el-form-item label="Title link (titlovi.com or equivalent)" prop="titleLink">
      <el-input v-model="model.titleLink" placeholder="https://titlovi.com/titlovi/goodfellas-194538/"></el-input>
    </el-form-item>
  </el-form>
</template>

<script>

export default {
  name: "FirstStep",
  data(){
    return {
      model: {
        downloadLink: '',
        titleLink: '',
      },
      rules: {
        downloadLink: [{
          required: true,
          message: 'Download link is required',
          trigger: 'blur'
        }],
        titleLink: [{
          required: true,
          message: 'Title link is required',
          trigger: 'blur'
        }],
        // email: [{
        //   required: true,
        //   message: 'Email is required',
        //   trigger: 'blur'
        // },
        //   {
        //     type: 'email',
        //     message: 'Invalid email',
        //     trigger: 'change'
        //   }],
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
