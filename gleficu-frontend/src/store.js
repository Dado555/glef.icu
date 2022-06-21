import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isUserLoggedIn: true,
  },
  mutations: {
    SET_USER_LOGGED_IN(state, payload) {
      state.isUserLoggedIn = payload;
    },
  },
  actions: {
    setUserLoggedIn({ commit }, loggedIn) {
      commit("SET_USER_LOGGED_IN", loggedIn);
    },
  },
  getters: {
    isUserLoggedIn(state) {
      return state.isUserLoggedIn;
    },
  },
});
