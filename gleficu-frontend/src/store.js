import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isUserLoggedIn: false,
    user: {
      username: undefined,
      authority: undefined,
    },
  },
  mutations: {
    SET_USER_LOGGED_IN(state, payload) {
      state.isUserLoggedIn = true;
      state.user.username = payload.username;
      state.user.authority = payload.authority;
      localStorage.setItem("id_token", payload.token);
    },
    LOGOUT_USER(state) {
      state.isUserLoggedIn = false;
      state.user.username = undefined;
      state.user.authority = undefined;
      localStorage.removeItem("id_token");
    },
  },
  actions: {
    setUserLoggedIn(store, user) {
      store.commit("SET_USER_LOGGED_IN", user);
    },
    setLogoutUser(store) {
      store.commit("LOGOUT_USER");
    },
  },
  getters: {
    isUserLoggedIn(state) {
      return state.isUserLoggedIn;
    },
  },
});
