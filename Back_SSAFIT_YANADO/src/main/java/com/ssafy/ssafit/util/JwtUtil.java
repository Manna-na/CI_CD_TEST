package com.ssafy.ssafit.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private static final String SALT = "SSAFIT";
	
	public String createToken(String claimId, String data) throws UnsupportedEncodingException {
		
		return Jwts.builder()
				   .setHeaderParam("alg", "HS256")
				   .setHeaderParam("typ", "JWT") // 헤더끝
				   .claim(claimId, data)
				   .setExpiration(new Date(System.currentTimeMillis()+(1000*60*60*24))) // 유효기간, 페이로드 완료
				   .signWith(SignatureAlgorithm.HS256, SALT.getBytes("UTF-8"))
				   .compact();
	}
	
	// 유효성 검사
	public String valid(String token) throws Exception {
		Claims claim =  Jwts.parser().setSigningKey("SSAFIT".getBytes("UTF-8")).parseClaimsJws(token).getBody();
		return claim.get("loginId", String.class);
	}
	
}
