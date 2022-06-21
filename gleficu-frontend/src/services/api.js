import axios from "axios";

export default axios.create({
  baseURL: "https://api.themoviedb.org/3",
  headers: {
    Authorization: `Bearer ${process.env.VUE_APP_TMDB_READ_TOKEN}`, // ${process.env.VUE_APP_TMDB_TOKEN}
  },
});
