import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import routes from "@/routes";

import "@/assets/css/styles.css"

Vue.config.productionTip = false

new Vue({
  vuetify,
  router: routes,
  render: h => h(App)
}).$mount('#app')
