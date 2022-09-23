package com.alwaysbedream.survbackend.utils;

import java.util.Date;

import com.alwaysbedream.survbackend.entity.User.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {
    // create function for generate JWT Token by User Data
    public static String generateToken(User user){
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
            .setIssuedAt(new Date(timestamp))
            .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
            .claim("user_id", user.getId())
            .claim("firstname", user.getFirstname())
            .claim("lastname", user.getLastname())
            .claim("email", user.getEmail())
            .compact();
        return token;
    }
    // parsing token into object data
    public static Object retriveToken(String token){
        return Jwts.parser().setSigningKey(Constants.API_SECRET_KEY).parseClaimsJws(token).getBody();
    }
}