import axios from "axios";

const API_URL = 'http://localhost:3004/api/recommend/'
const GET_GENRES = API_URL + "genre-by-tags"

class JavaMovieRecommend {
    getMovieRecommends(payload) {
        return axios.get(GET_GENRES + `?user_id=${payload.userId}&tags=${payload.tags}`,
            {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("id_token"),
                }
            }
        );
    }
}
export const javaMovieRecommend = new JavaMovieRecommend();