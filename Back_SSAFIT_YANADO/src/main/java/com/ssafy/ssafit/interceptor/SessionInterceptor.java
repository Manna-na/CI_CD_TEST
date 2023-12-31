package com.ssafy.ssafit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.ssafit.util.JwtUtil;

@Component
public class SessionInterceptor implements HandlerInterceptor {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	// 컨트롤러 호출 전에 실행되어야 한다.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// request 에서 session 정보 얻기
		HttpSession session = request.getSession();
		// 세션에 로그인 정보가 있다면 그대로 진행한다.
		if(jwtUtil.valid((String)session.getAttribute("loginUser")).equals(session.getAttribute("userId"))) {
			return true;
		} 
		// 세션에 로그인 정보가 없다면 redirect 시킨다.
		else {	
			return false;
		}
	
	}
	
}
