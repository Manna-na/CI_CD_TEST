package com.ssafy.ssafit.model.service;

import java.util.List;

import com.ssafy.ssafit.model.dto.User;

public interface UserService {
	int insertUser(User user);
	User selectUserById(String userId);
	User selectUserByEmail(String email);
	int updateUser(User user);
	int deleteUser(String userId);
	boolean passwordChk(String password);
	boolean emailChk(String email);
	String loginChk(String userId, String loginId);
	List<User> selectAll();
}
