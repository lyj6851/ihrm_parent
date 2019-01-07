package com.ihrm.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * <p></p>
 *
 * @author xiaodongsun
 * @date 2019/01/07
 */
public class ParseJwtTest {

    /**
     * 解析jwt token字符串
     * @param args
     */
    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyMjIyIiwic3ViIjoidG9wYmFieSIsImlhdCI6MTU0Njg1MzM2MCwiY29tcGFueU5hbWUiOiJ0b3BiYWJ55a6e5Lia5pyJ6ZmQ5YWs5Y-4In0.O4-Uz4oSk5UbmavvYAQICD-f0ChvZSXf4mwJNST-IFE";
        Claims claims = Jwts.parser().setSigningKey("123456").parseClaimsJws(token).getBody();
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.get("companyName"));
    }
}
