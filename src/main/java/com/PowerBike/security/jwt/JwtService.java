package com.PowerBike.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //Se traen los valores del aplicaction properties con la etiqueta value
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    public String getToken(UserDetails user) {
        return getToken1(new HashMap<>(), user);
    }

    private String getToken1(Map<String, Object> extractClaims, UserDetails user) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(user.getUsername())                              //Sujeto que crea el token
                .setIssuedAt(new Date(System.currentTimeMillis()))          //Fecha de creacion del token con hora
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)      //se encripta nuevamente en HS256
                .compact();
    }

    //Metodo para obtener los claims(contenido) del token retorna el bogy del token
    public Key getSignatureKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);        //se desencripta la secret key
        return Keys.hmacShaKeyFor(keyBytes);                        //se encripta nuevamente
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Metodo para extraer la fecha de expiracion del token
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    //Metodo para verificar que el token sea valido se extrae la fecha del token
    //y se compara con la fecha actual del sistema
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

}
