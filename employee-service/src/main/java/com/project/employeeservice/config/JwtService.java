package com.project.employeeservice.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "47585054526f366a7539496f45676d306e62513237565245397a6764564e634d";

    //Token'dan ismi çıkartmak için
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);//subject kullanıcı adı için

    }

    //Tüm claim'leri çıkartmak için
    private Claims extractAllClaims(String token) {
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignInKey())//json web tokenleri imzalamak için gereken kısım ve jwt'nin signature kısmı
                .build()
                .parseClaimsJws(token)//jwt'yi çözmek için
                .getBody();//jwt içerisindeki claims'leri claim nesnesi döndürmek için
    }

    //-->signinkey için
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //sadece 1 tane claim'i çıkartmak için.
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        //parametrede function nesnesi claims olarak alıp herhangi tipte bir değer döndürmek için.
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    //Extra detayları olan claim'ler içeren Token oluşturmak için
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims) //ekstra claims'leri eklemek için map'liyoruz
                .setSubject(userDetails.getUsername())
                .claim("roles",userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Sadece user'a ait bir token için(extra claimsi yok)
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    //Token içerisindeki Expiration claims'ini çıkartmak için
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    //Token'in zamanı geçip geçmediğini kontrol edip değer döndürmek için.
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Token geçerli mi geçersiz mi kontrol etmek için.
    public Boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }




}
