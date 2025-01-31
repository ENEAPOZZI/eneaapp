package it.epicode.feste.config;




import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.epicode.feste.services.security.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;




@Component
public class JwtUtils {


    @Value("${jwt.key}")
    private String securityKey; // = "chiavesegreta123chiavesegreta123";
    @Value("${jwt.expirationMs}")
    private long expirationMs; // = 864000000;



    public String generateToken(Authentication auth) {
        byte[] keyBytes = securityKey.getBytes();
        Key key = Keys.hmacShaKeyFor(keyBytes);

        // recupero l'utente di sistema
        var user = (SecurityUserDetails) auth.getPrincipal();
        var token = Jwts.builder() // builder per il token
                .subject(user.getUsername()) // contenuto del token
                .issuedAt(new Date()) // data di rilascio
                .issuer("MySpringApplication") // nome di colui che ha scritto il token
                .expiration(new Date(new Date().getTime() + expirationMs)) // data di scadenza
                .signWith(key) // firma e cifra il token
                .compact();
        return token;
    }

    public boolean isTokenValid(String token) {
        try {
            byte[] keyBytes = securityKey.getBytes();
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);
            Jwts.parser() //
                    .verifyWith(key).requireIssuer("MySpringApplication");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        byte[] keyBytes = securityKey.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        var username = Jwts.parser() //
                .verifyWith(key).build() //
                .parseSignedClaims(token).getPayload().getSubject();
        return username;
    }
}
