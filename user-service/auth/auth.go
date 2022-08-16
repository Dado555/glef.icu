package auth

import (
	"errors"
	"github.com/Dado555/glef.icu/user-service/models"
	jwtMiddleware "github.com/auth0/go-jwt-middleware"
	"github.com/form3tech-oss/jwt-go"
	"net/http"
	"time"
)

// signingKey set up a global string for our secret
var signingKey = []byte("oiviaiuvhauviopwe")

// JwtMiddleware handler for jwt tokens
var JwtMiddleware = jwtMiddleware.New(jwtMiddleware.Options{
	ValidationKeyGetter: func(token *jwt.Token) (interface{}, error) {
		return signingKey, nil
	},
	UserProperty:  "user",
	Debug:         false,
	SigningMethod: jwt.SigningMethodHS256,
})

// GetToken create a jwt token with user claims
func GetToken(user *models.User, roleName string) string {
	token := jwt.New(jwt.SigningMethodHS256)
	claims := token.Claims.(jwt.MapClaims)
	claims["exp"] = time.Now().Add(time.Hour * 24).Unix()
	claims["iat"] = time.Now().Unix()
	claims["username"] = user.Username
	claims["authority"] = roleName
	signedToken, _ := token.SignedString(signingKey)
	return signedToken
}

func ExtractJWT(request *http.Request) (*jwt.Token, error) {
	authorizationHeader := request.Header.Get("Authorization")
	if len(authorizationHeader) == 0 {
		return nil, errors.New("no Authorization header found")
	}

	tokenString := authorizationHeader[7:]
	token, err := jwt.ParseWithClaims(tokenString, &models.JwtClaims{},
		func(t *jwt.Token) (interface{}, error) {
			return signingKey, nil
		})

	return token, err
}

// GetJSONToken create a JSON token string
func GetJSONToken(user *models.User, roleName string) string {
	token := GetToken(user, roleName)
	jsonToken := "{\"id_token\": \"" + token + "\"}"
	return jsonToken
}

// GetUserClaimsFromContext return "user" claims as a map from request
func GetUserClaimsFromContext(req *http.Request) map[string]interface{} {
	claims := req.Context().Value("user").(*jwt.Token).Claims.(jwt.MapClaims)
	return claims
}
