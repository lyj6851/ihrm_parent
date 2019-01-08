package com.ihrm.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.Map;

/**
 * <p>jwt utils</p>
 *
 * @author xiaodongsun
 * @date 2019/01/07
 */
@Getter
@Setter
@ConfigurationProperties("jwt.config")
public class JwtUtils {
    /**
     * 签名私钥
     */
    private String key;
    /**
     * 签名的失效时间
     */
    private Long ttl;

    /**
     * 设置认证token
     */
    public String createJwt(String id, String name, Map<String, Object> map){
        //设置失效时间
        long now = System.currentTimeMillis();
        long exp = now + ttl;
        //创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                .setSubject(name)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);
        //根据map设置claims
        for(Map.Entry<String, Object> entry:map.entrySet()){
            jwtBuilder.claim(entry.getKey(), entry.getValue());
        }
        jwtBuilder.setExpiration(new Date(exp));
        //创建token
        String token = jwtBuilder.compact();
        return token;
    }

    /**
     * 解析token字符串获取clamis
     */
    public Claims parseJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }
}
