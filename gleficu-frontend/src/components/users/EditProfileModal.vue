<template>
  <div
      style="background-color: rgba(0, 0, 0, .5);"
      class="fixed top-0 left-0 w-full h-full flex items-center shadow-lg overflow-y-auto z-50"
      tabindex="0"
      v-show="value"
      id="editProfile">
    <div class="container mx-auto lg:px-32 rounded-lg overflow-y-auto">
      <div class="bg-gray-900 rounded">
        <div class="flex justify-end pr-4 pt-2">
          <button @click="close" class="text-3xl leading-none hover:text-gray-300">&times;</button>
        </div>
        <div class="modal-body px-8 py-8">
          <div class="responsive-container overflow-hidden relative">
            <h1 style="font-size: x-large; color: whitesmoke" slot="title">Edit User Profile</h1>
            <h2 style="font-size: large;" slot="title">Define New User Informations</h2>
            <el-form :model="model" :rules="rules" ref="form">
              <el-row type="flex" class="row-bg" style="margin-top: 10px">
                <el-col :span="12">
                  <el-form-item label="Age" prop="age">
                    <el-input style="width:34em" v-model="model.age"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="Gender" prop="gender">
                    <el-input style="width:34em" v-model="model.gender"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row type="flex" class="row-bg" style="margin-top: 10px;">
                <el-col :span="24">
                  <el-form-item label="Favourite tags (separated by comma) " prop="favouriteTag">
                    <el-input style="width:64em" v-model="model.favouriteTag"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
            <button class="rounded bg-yellow-500 text-black cursor-auto" style="height: 40px;width: 100px;" @click="submitUserEdit()">Submit</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {userService} from "@/services/userService";

export default {
  name: "EditProfileModal",
  props: {
    value: {
      required: true,
    },
    userId: {
      required: true,
    }
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
    },
    submitUserEdit() {
      alert("Updating user...");
      // update user
      let payload = {
        gender: this.model.gender,
        age: parseInt(this.model.age),
        favouriteTags: this.model.favouriteTag
      }
      // console.log(payload);
      userService.updateUser(this.userId, payload).then(() => {
        alert("User updated!");
        this.$emit("updatedUser");
      })
      this.close();
    },
    getUserDb(id) {
      userService.getById(id).then((response) => {
        this.model.gender = response.data.gender;
        this.model.age = response.data.age;
        this.model.favouriteTag = response.data.favouriteTags;
      })
    },
  },

  data() {
    return {
      finalModel: {},
      activeTabIndex: 0,
      model: {
        gender: '',
        age: '',
        favouriteTag: ''
      },
      rules: {
        gender: [{
          required: true,
          message: 'Gender is required',
          trigger: 'change'
        }],
        age: [{
          required: true,
          message: 'Age is required',
          trigger: 'change'
        }],
        favouriteTag: [{
          required: true,
          message: 'Favourite tag is required',
          trigger: 'change'
        }],
      },
    };
  },

  mounted() {
    this.getUserDb(this.userId);
  }
}
</script>

<style scoped>
#editProfile {
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
</style>