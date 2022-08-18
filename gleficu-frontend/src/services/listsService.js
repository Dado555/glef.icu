import axios from "axios";

const API_URL = 'http://localhost:3002/api/lists/'
const GET_WATCHLIST = API_URL + 'getWatchlist'
const GET_WISHLIST = API_URL + 'getWishlist'
const GET_WATCHLIST_ITEM = API_URL + "getWatchlistItem"
const GET_WISHLIST_ITEM = API_URL + "getWishlistItem"
const SAVE_WATCHLIST_ITEM = API_URL + "saveWatchlistItem"
const SAVE_WISHLIST_ITEM = API_URL + "saveWishlistItem"
const DELETE_WATCHLIST_ITEM = API_URL + "deleteWatchlistItem"
const DELETE_WISHLIST_ITEM = API_URL + "deleteWishlistItem"

class ListsService {
    getWatchlist(params) { // page, size, userId
        return axios.get(GET_WATCHLIST, {
            params: params,
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            }});
    }

    getWishlist(params) { // page, size, userId
        return axios.get(GET_WISHLIST, {
            params: params,
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            }});
    }

    getWatchlistItem(params) { // userId, movieId
        return axios.get(GET_WATCHLIST_ITEM, {
            params: params,
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            }});
    }

    getWishlistItem(params) { // userId, movieId
        return axios.get(GET_WISHLIST_ITEM, {
            params: params,
            headers: {
                Authorization: "Bearer " + localStorage.getItem("id_token"),
            }});
    }

    saveWatchlistItem(payload) {
        return axios.post(SAVE_WATCHLIST_ITEM, payload, {
            headers: { Authorization: "Bearer " + localStorage.getItem("id_token") }
        })
    }

    saveWishlistItem(payload) {
        return axios.post(SAVE_WISHLIST_ITEM, payload, {
            headers: { Authorization: "Bearer " + localStorage.getItem("id_token") }
        })
    }

    deleteWatchlistItem(params) {
        return axios.delete(DELETE_WATCHLIST_ITEM, {
            params: params,
            headers: { Authorization: "Bearer " + localStorage.getItem("id_token") }
        })
    }

    deleteWishlistItem(params) {
        return axios.delete(DELETE_WISHLIST_ITEM, {
            params: params,
            headers: { Authorization: "Bearer " + localStorage.getItem("id_token") }
        })
    }
}
export const listsService = new ListsService();