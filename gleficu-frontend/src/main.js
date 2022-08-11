import Vue from "vue";
import VueRouter from "vue-router";
import router from "./routes";
import App from "./App.vue";
import "@/assets/css/styles.css";
import "@/assets/css/elementui.css"
import "@/assets/css/themifyicons.css"
import api from "./services/api";

import axios from "axios";
import jwt_decode from "jwt-decode";

import Element from 'element-ui'
import FormWizard from "vue-form-wizard";
import "vue-form-wizard/dist/vue-form-wizard.min.css";
import locale from 'element-ui/lib/locale/lang/en'

Vue.prototype.$http = api;
Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(Element, { locale })
Vue.use(FormWizard);

Vue.filter("capitalize", function (param) {
  if (!param) return "N/A";
  else return param.charAt(0).toUpperCase() + param.slice(1).toLowerCase();
});

Vue.filter("removeUnderscore", (param) => {
  if (!param) return "N/A";
  else return param.replaceAll("_", " ");
});
// Configure axios to always include JWT when sending a request
axios.interceptors.request.use(
    (config) => {
      let jwt = localStorage.getItem("jwt");
      if (jwt) {
        if (config.headers) {
          config.headers.Authorization = `Bearer ${jwt}`;
        }
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
);

// Authority guard
router.beforeEach((to, from, next) => {
  const { authenticated, authorities } = to.meta;

  if (authenticated) {
    let jwt = localStorage.getItem("jwt");
    if (jwt) {
      let decodedToken = jwt_decode(jwt);
      if (authorities.some((element) => decodedToken.roles.includes(element))) {
        next();
      } else {
        next({ name: "Login" });
      }
    } else {
      next({ name: "Login" });
    }
  } else {
    next();
  }
});

new Vue({
  render: (h) => h(App),
  router: router,
}).$mount("#app");
