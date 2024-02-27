package site.hclub.hyndai.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.hclub.hyndai.exception.UserException;

import static site.hclub.hyndai.exception.UserExceptionType.NO_TOKEN_EXCEPTION;

@Slf4j
@Service
public class JwtTokenResolver {

    private final String secretKey;

    public JwtTokenResolver(@Value("${jwt-secret}") String secretKey) {
        this.secretKey = secretKey;
    }


    public String resolveToken(String token) {
        
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();


            return claims.getSubject();

        } catch (NullPointerException e) {
            throw new UserException(NO_TOKEN_EXCEPTION);
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
        } catch (Exception e) {
            log.info("Error parsing JWT: " + e.getMessage());
        }
        return null;
    }
}
