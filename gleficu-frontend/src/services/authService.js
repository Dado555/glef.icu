const API_URL = 'http://localhost:3000/api/user/'
const LOGIN_URL = API_URL + 'login'
const SIGNUP_URL = API_URL + 'signup'

class AuthService {
    login (context, credentials, redirect) {
        context.$http.post(LOGIN_URL, credentials).then(response => {
            console.log(response);
            localStorage.setItem('id_token', response.data.id_token)
            localStorage.setItem('username', credentials.username)
            if (redirect) {
                context.$router.replace(redirect)
            } else {
                this.$emit('login');
            }
        }, response => {
            context.error = response.statusText
        })
    }

    signup (context, credentials, redirect) {
        context.$http.post(SIGNUP_URL, credentials).then(response => {
            console.log(response);
            localStorage.setItem('id_token', response.data.id_token)
            localStorage.setItem('username', credentials.username)
            if (redirect) {
                context.$router.replace(redirect)
            } else {
                this.$emit('login');
            }
        }, response => {
            context.error = response.statusText
        })
    }

    logout (context) {
        localStorage.removeItem('id_token')
        localStorage.removeItem('username')
        context.$router.replace('/')
    }

    isAuthenticated () {
        let jwt = localStorage.getItem('id_token')
        if (jwt) {
            return true
        }
        return false
    }

    getAuthHeader () {
        return {
            'Authorization': 'Bearer ' + localStorage.getItem('id_token')
        }
    }
}

export const authService = new AuthService();