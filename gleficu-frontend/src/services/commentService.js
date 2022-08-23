import axios from "axios";

const API_URL = 'http://localhost:3003/api/comments/'
const GET_COMMENTS_FOR_MOVIE = API_URL + 'getByMovie'
const GET_BAD_COMMENTS_FOR_USER = API_URL + 'getBadByUser'
const GET_FOR_USER = API_URL + 'getByUserAndMovie'
const ADD_COMMENT = API_URL + "save"
const DELETE_COMMENT = API_URL + "delete"
const UPDATE_COMMENT = API_URL + "update"

const ADD_COMPLAINT = API_URL + "addComplaint"
const GET_COMPLAINT = API_URL + "getComplaint"
const DELETE_COMPLAINT = API_URL + "deleteComplaint"

class CommentService {
    getCommentsByMovie(movieId) {
        return axios.get(GET_COMMENTS_FOR_MOVIE + `?movie_id=${movieId}`,
            {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("id_token"),
                }
            }
        );
    }

    getBadCommentsByUser(userId) {
        return axios.get(GET_BAD_COMMENTS_FOR_USER + `?user_id=${userId}`,
            {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("id_token"),
                }
            }
        );
    }

    getCommentByUserAndMovie(userId, movieId) {
        return axios.get(GET_FOR_USER + `?user_id=${userId}&movie_id=${movieId}`,
            {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("id_token"),
                }
            }
        );
    }

    addComment(comment_creation_dto) {
        return axios.post(ADD_COMMENT, comment_creation_dto,
            {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("id_token")
                }
            }
        );
    }

    deleteComment(commentId) {
        return axios.delete(DELETE_COMMENT + `?comment_id=${commentId}`,
            {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("id_token")
                }
            }
        );
    }

    updateComment(comment_update_dto) {
        return axios.put(UPDATE_COMMENT, comment_update_dto,
            {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("id_token")
                }
            }
        );
    }

    addComplaint(complaint_creation_dto) {
        return axios.post(ADD_COMPLAINT, complaint_creation_dto,
            {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("id_token")
                }
            }
        );
    }

    getComplaint(userId, commentId) {
        return axios.get(GET_COMPLAINT + `?user_id=${userId}&comment_id=${commentId}`,
            {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("id_token"),
                }
            }
        );
    }

    deleteComplaint(complaintId, commentId) {
        return axios.delete(DELETE_COMPLAINT + `?complaint_id=${complaintId}&comment_id=${commentId}`,
            {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("id_token")
                }
            }
        );
    }
}
export const commentService = new CommentService();