package com.ihrm.demo;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/07
 */
public class CreateJwtTest {

    /**
     * 创建token字符串
     */
    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder().setId("2222")
                .setSubject("topbaby")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "123456")
                .claim("companyName", "Top有限公司");
        String token = jwtBuilder.compact();
        System.out.println(token);
    }
}
