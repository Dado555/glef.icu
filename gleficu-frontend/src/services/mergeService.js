import axios from "axios";

const API_URL = 'http://localhost:5000/api/'
const MERGE_MOVIE_AND_SUBTITLE = API_URL + "merge"

class MergeService {
    merge(payload) {
        return axios.get(MERGE_MOVIE_AND_SUBTITLE + `?movie_name=${payload.movie}&subtitle_name=${payload.subtitle}`,
            {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("id_token"),
                }
            }
        );
    }
}
export const mergeService = new MergeService();