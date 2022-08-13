package auth

import (
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
func GetToken(user *models.User) string {
	token := jwt.New(jwt.SigningMethodHS256)
	claims := token.Claims.(jwt.MapClaims)
	claims["uuid"] = user.UUID
	claims["exp"] = time.Now().Add(time.Hour * 24).Unix()
	signedToken, _ := token.SignedString(signingKey)
	return signedToken
}

// GetJSONToken create a JSON token string
func GetJSONToken(user *models.User) string {
	token := GetToken(user)
	jsonToken := "{\"id_token\": \"" + token + "\"}"
	return jsonToken
}

// GetUserClaimsFromContext return "user" claims as a map from request
func GetUserClaimsFromContext(req *http.Request) map[string]interface{} {
	claims := req.Context().Value("user").(*jwt.Token).Claims.(jwt.MapClaims)
	return claims
}
