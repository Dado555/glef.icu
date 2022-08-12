<template>
  <div
      style="background-color: rgba(0, 0, 0, .5);"
      class="fixed top-0 left-0 w-full h-full flex items-center shadow-lg overflow-y-auto z-50"
      tabindex="0"
      v-show="value"
      id="reportComment">
    <div class="container mx-auto lg:px-32 rounded-lg overflow-y-auto">
      <div class="bg-gray-900 rounded">
        <div class="flex justify-end pr-4 pt-2">
          <button @click="close" class="text-3xl leading-none hover:text-gray-300">&times;</button>
        </div>
        <div class="modal-body px-8 py-8">
          <div class="responsive-container overflow-hidden relative">
            <h1 style="font-size: x-large; color: whitesmoke" slot="title">Report comment</h1>
            <h2 style="font-size: large;" slot="title">Define reason for complaint</h2>
            <el-form :model="model" :rules="rules" ref="form">
              <el-form-item label="Reason for report" prop="reason">
                <el-input v-model="model.reason" placeholder="I find it..."></el-input>
              </el-form-item>
<!--              <el-form-item label="How inappropriate it is?" prop="inappropriate">-->
<!--                <el-input v-model="model.inappropriate" placeholder="Little"></el-input>-->
<!--              </el-form-item>-->
              <el-form-item label="How inappropriate it is?" prop="inappropriate">
                <el-select v-model="model.inappropriate">
                  <el-option v-for="inap in inappropriateLevels"
                             :key="inap.value"
                             :label="inap.label"
                             :value="inap.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-form>
            <button class="rounded bg-yellow-500 text-black cursor-auto" style="height: 40px;width: 100px;">Submit</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import prettyJSON from "@/components/movies/createMovie/prettyJson";

export default {
  name: "ReportCommentModal",
  props: {
    value: {
      required: true,
    },
  },
  methods: {
    close() {
      this.$emit("input", !this.value);
    },
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
  },
  data() {
    return {
      finalModel: {},
      activeTabIndex: 0,
      model: {
        reason: '',
        inappropriate: '',
      },
      rules: {
        reason: [{
          required: true,
          message: 'Reason is required',
          trigger: 'blur'
        }],
        inappropriate: [{
          required: true,
          message: 'Inappropriate level is required',
          trigger: 'change'
        }],
      },
      inappropriateLevels: [
        {value: '', label: '- level -'},
        {value: 'low', label: 'Low level'},
        {value: 'medium', label: 'Medium level'},
        {value: 'high', label: 'High level'}],
    };
  },
  computed: {
    prettyJSON() {
      return prettyJSON(this.finalModel);
    }
  },
}
</script>

<style scoped>
#reportComment {
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