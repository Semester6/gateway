package watcherz.gateway.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "secret";
    private final String tokenPrefix = "Bearer ";
    private final String headerString = "Authorization";

    public String verifyToken(String token) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("AuthenticationService")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("userId").asString();
    }
}
//    @PostConstruct
//    public void init() {
//        this.key = Keys.hmacShaKeyFor(secret.getBytes());
//    }

//    public Claims getAllClaimsFromToken(String token) {
//        return OAuth2ResourceServerProperties.Jwt.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//    }
//
//    private boolean isTokenExpired(String token) {
//        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
//    }
//
//    public boolean isInvalid(String token) {
//        return this.isTokenExpired(token);
//    }

