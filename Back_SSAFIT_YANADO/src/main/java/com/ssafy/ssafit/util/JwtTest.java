package com.ssafy.ssafit.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTest {
	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
		String token = Jwts.builder()
				.setHeaderParam("alg", "HS256")
				.setHeaderParam("typ", "JWT")// 헤더 완료
				.claim("loginUser", "wngh")
				.setExpiration(new Date(System.currentTimeMillis()+(1000*60*60*24))) // 페이로드 완료
				.signWith(SignatureAlgorithm.HS256,"SSAFIT".getBytes("UTF-8")) // 서명 완료
				.compact();
		System.out.println(token);
		
		System.out.println(new String(Base64.getDecoder().decode(token.split("\\.")[1])));
		// payload를 decode
		
		// 위에서 설정한 유효기간이 3초 이므로  3초 뒤에 다시한번 검사를 해보면 오류가 뜨는걸 확인해보자
//		Thread.sleep(4000);
		
		//유효성 검사
		Claims claim = Jwts.parser().setSigningKey("SSAFIT".getBytes("UTF-8")).parseClaimsJws(token).getBody();
		System.out.println(claim.get("loginUser"));
	}
}