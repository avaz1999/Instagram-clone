package uz.avaz.instagramclone.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtAuthenticationUtil {

    @Value("${com.example.jwtSecret}")
    private String jwtSecretKey;
    @Value("${com.example.jwtExpirationMs}")
    private int jwtExpirationMs;

    public boolean isValidToken(String tokenClient) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(tokenClient);
            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("Expired token!");
        } catch (MalformedJwtException malformedJwtException) {
            System.err.println("Invalid token!");
        } catch (SignatureException s) {
            System.err.println("Invalid secret key!");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            System.err.println("Unsupported token!");
        } catch (IllegalArgumentException ex) {
            System.err.println("Token is blank!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
    }
}
