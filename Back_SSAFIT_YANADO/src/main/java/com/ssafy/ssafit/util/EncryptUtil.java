package com.ssafy.ssafit.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class EncryptUtil {
	public String getEncrypt(String str) {
		String result = "";
		try {
			StringBuffer sb = new StringBuffer();
			// SHA256 알고리즘 객체 생성
			// MessageDigest : 자바에 내장된 암호화를 도와주는 클래스입니다
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// 문자열에 SHA256 적용
			md.update(str.getBytes());
			byte[] pwd = md.digest();
			// byte to String(10진수의 문자열로 변경)
			for (byte b : pwd) {
				sb.append(String.format("%02x", b));
			}
			result = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 반환
		return result;
	}
}
