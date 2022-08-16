<template>
  <div :class="{'flex space-x-4': variant === 'horizontal',}">
    <ul
        class="list-none p-1.5 rounded-lg text-center overflow-auto whitespace-nowrap"
        :class="{'flex items-center mb-6': variant === 'vertical',}">
      <li
          v-for="(tab, index) in tabList"
          :key="index"
          style="color: whitesmoke;"
          class="w-full px-4 py-1.5 rounded-lg"
          :class="{
          'bg-blue-500 shadow-xl': index + 1 === activeTab,
          'text-white': index + 1 !== activeTab,
        }"
      >
        <label
            :for="`${_uid}${index}`"
            v-text="tab"
            class="cursor-pointer block"
        />
        <input
            :id="`${_uid}${index}`"
            type="radio"
            :name="`${_uid}-tab`"
            :value="index + 1"
            v-model="activeTab"
            class="hidden"
        />
      </li>
    </ul>
    <template v-for="(tab, index) in tabList" >
      <div :key="index" v-if="activeTab === 1">
        <div class="login-box">
          <h2>Login</h2>
          <form>
            <div class="user-box">
              <input type="text" name="" required v-model="loginInfo.email">
              <label>Username</label>
            </div>
            <div class="user-box">
              <input type="password" name="" required v-model="loginInfo.password">
              <label>Password</label>
            </div>
            <a href="#" @click="login()">
              <span></span>
              <span></span>
              <span></span>
              <span></span>
              Submit
            </a>
          </form>
        </div>
      </div>
      <div :key="index+1" v-if="activeTab === 2">
        <div class="login-box">
          <h2>Register</h2>
          <form>
            <div class="user-box">
              <input type="text" name="" required v-model="registerInfo.email">
              <label>Username</label>
            </div>
            <div class="user-box">
              <input type="password" name="" required v-model="registerInfo.password">
              <label>Password</label>
            </div>
            <a href="#" @click="register()">
              <span></span>
              <span></span>
              <span></span>
              <span></span>
              Submit
            </a>
          </form>
        </div>
      </div>
    </template>
  </div>
</template>

<script>
import {authService} from "@/services/authService";
import jwtDecode from "jwt-decode";

export default {
  name: "Login",
  data: () => {
    return {
      valuePassword: String,
      loginInfo: {
        email: "",
        password: "",
      },
      error: '',
      registerInfo: {
        email: "",
        password: ""
      },
      text: "Wrong username/password combination.",
      activeTab: 1,
      tabList: ["Login", "Register"],
    };
  },
  props: {
    variant: {
      type: String,
      required: false,
      default: () => "vertical",
      validator: (value) => ["horizontal", "vertical"].includes(value),
    },
  },
  methods: {
    login() {
      let credentials = {
        username: this.loginInfo.email,
        password: this.loginInfo.password
      }
      authService.login(credentials).then(response => {
        console.log(response);
        this.loginAction(response, "/home", this);
      }, response => {
        this.error = response.statusText
      })
    },
    register() {
      let credentials = {
        username: this.registerInfo.email,
        password: this.registerInfo.password
      }
      authService.signup(credentials).then(response => {
        console.log(response);
        this.loginAction(response, "/home", this);
      }, response => {
        this.error = response.statusText
      })
    },
    loginAction(response, redirect, context) {
      let token = response.data.id_token;
      let decoded = jwtDecode(token);
      this.$store.dispatch("setUserLoggedIn", {username: decoded.username,
        authority: decoded.authority, token: token})
      if (redirect) {
        context.$router.replace(redirect)
      }
    }
  },
};
</script>

<style>
.login-box {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 400px;
  padding: 40px;
  transform: translate(-50%, -50%);
  background: rgba(0,0,0,.5);
  box-sizing: border-box;
  box-shadow: 0 15px 25px rgba(0,0,0,.6);
  border-radius: 10px;
}

.login-box h2 {
  margin: 0 0 30px;
  padding: 0;
  color: #fff;
  text-align: center;
}

.login-box .user-box {
  position: relative;
}

.login-box .user-box input {
  width: 100%;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  margin-bottom: 30px;
  border: none;
  border-bottom: 1px solid #fff;
  outline: none;
  background: transparent;
}
.login-box .user-box label {
  position: absolute;
  top:0;
  left: 0;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  pointer-events: none;
  transition: .5s;
}

.login-box .user-box input:focus ~ label,
.login-box .user-box input:valid ~ label {
  top: -20px;
  left: 0;
  color: #03e9f4;
  font-size: 12px;
}

.login-box form a {
  position: relative;
  display: inline-block;
  padding: 10px 20px;
  color: #03e9f4;
  font-size: 16px;
  text-decoration: none;
  text-transform: uppercase;
  overflow: hidden;
  transition: .5s;
  margin-top: 40px;
  letter-spacing: 4px
}

.login-box a:hover {
  background: #03e9f4;
  color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 5px #03e9f4,
  0 0 25px #03e9f4,
  0 0 50px #03e9f4,
  0 0 100px #03e9f4;
}

.login-box a span {
  position: absolute;
  display: block;
}

.login-box a span:nth-child(1) {
  top: 0;
  left: -100%;
  width: 100%;
  height: 2px;
  background: linear-gradient(90deg, transparent, #03e9f4);
  animation: btn-anim1 1s linear infinite;
}

@keyframes btn-anim1 {
  0% {
    left: -100%;
  }
  50%,100% {
    left: 100%;
  }
}

.login-box a span:nth-child(2) {
  top: -100%;
  right: 0;
  width: 2px;
  height: 100%;
  background: linear-gradient(180deg, transparent, #03e9f4);
  animation: btn-anim2 1s linear infinite;
  animation-delay: .25s
}

@keyframes btn-anim2 {
  0% {
    top: -100%;
  }
  50%,100% {
    top: 100%;
  }
}

.login-box a span:nth-child(3) {
  bottom: 0;
  right: -100%;
  width: 100%;
  height: 2px;
  background: linear-gradient(270deg, transparent, #03e9f4);
  animation: btn-anim3 1s linear infinite;
  animation-delay: .5s
}

@keyframes btn-anim3 {
  0% {
    right: -100%;
  }
  50%,100% {
    right: 100%;
  }
}

.login-box a span:nth-child(4) {
  bottom: -100%;
  left: 0;
  width: 2px;
  height: 100%;
  background: linear-gradient(360deg, transparent, #03e9f4);
  animation: btn-anim4 1s linear infinite;
  animation-delay: .75s
}

@keyframes btn-anim4 {
  0% {
    bottom: -100%;
  }
  50%,100% {
    bottom: 100%;
  }
}
</style>