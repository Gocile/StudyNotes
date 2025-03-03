package com.gocile.shikesystem.util;

import com.gocile.shikesystem.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final long EXPIRE_TIME=4*60*60*1000;//过期时间4小时
    private static final SecretKey KEY;

    static {
        try {
            KEY = JwtUtil.getSecretKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getToken(String id,String permission){
        Map<String,Object> header = new HashMap();
        header.put("typ","JWT");
        header.put("alg","HS256");

        JwtBuilder builder = Jwts.builder().setHeader(header)
                .setId(id)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_TIME))
                .setSubject(permission)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,KEY);
        return builder.compact();
    }


    public static User verify(String token, User user){
        Claims claims = null;
        try {
            claims = Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token/*"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyMDI0MDEwMDAxIiwiZXhwIjoxNzQwNDkzNzE4LCJzdWIiOiLlrabnlJ8iLCJpYXQiOjE3NDA0NzkzMTh9.HqkOzU7JBdRZ4j9AjMzaBtaYN44dgfAdkuz45EQ5lvU"*/).getBody();
        }catch (ExpiredJwtException e){
            return user;
        }
        //从token中获取用户id，查询该Id的用户是否存在，存在则token验证通过
        user.setId(claims.getId());
        user.setPermission(claims.getSubject());
        return user;
    }


    public static SecretKey getSecretKey() throws NoSuchAlgorithmException {
        byte[] seed = "gocile".getBytes();
        SecureRandom secureRandom = new SecureRandom(seed);
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        keyGenerator.init(256,secureRandom);
        return keyGenerator.generateKey();
    }
}
