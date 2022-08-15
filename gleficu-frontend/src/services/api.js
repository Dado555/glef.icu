import axios from "axios";

export default axios.create({
  baseURL: "https://api.themoviedb.org/3",
  headers: {
    Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlZWUxMjMxODJkZjA2NDRkNWUxMzYwOGVjMDljNWRjMSIsInN1YiI6IjYyYTViZGMyNTM4NjZlMGRkZmFiM2ZkMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.9qaM-7Rs4ni8sNjGJa0rQ3TO3UlVcE11Mesf-wcwWDs`, // ${process.env.VUE_APP_TMDB_TOKEN}
  },
});
