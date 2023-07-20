package com.ssafy.ssafit.model.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.UserDao;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.util.JwtUtil;


@Service
public class UserServiceImpl implements UserService {
	
	String idpattern = "^[a-z]+[a-z0-9]{5,19}$";
	// 5-20자 영어 소문자로 시작, 영어소문자나 숫자
	String passwordpattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";
	// 8-16자, 최소 1개의 알파벳, 1개의 숫자, 1개의 특수문자
	String emailpattern = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	JavaMailSender emailsender;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Override
	public int insertUser(User user) {
		return userDao.insertUser(user);
	}

	@Override
	public User selectUserById(String userId) {
		return userDao.selectUserById(userId);
	}
	
	@Override
	public User selectUserByEmail(String email) {
		return userDao.selectUserByEmail(email);
	}

	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}
	
	@Override
	public int deleteUser(String userId) {
		return userDao.deleteUser(userId);
	}

	@Override
	public boolean passwordChk(String password) {
		if(Pattern.matches(passwordpattern, password)) return true;
		else return false;
	}

	@Override
	public boolean emailChk(String email) {
		if(Pattern.matches(emailpattern, email)) return true;
		else return false;
	}

	@Override
	public List<User> selectAll() {
		return userDao.selectAll();
	}

	@Override
	public String loginChk(String userId, String token) {
		try {
			if(selectUserById(userId) == null) {
				return "NoSuchUser";
			}
			if(jwtUtil.valid(token).isEmpty()) {
				return "NeedLogin";
			}
			if(userId.equals(jwtUtil.valid(token))){
				return "OK";
			}
			else {
				return "NoAccess";
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			return "Error";
		}
	}

}
