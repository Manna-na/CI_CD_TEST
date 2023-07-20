package com.ssafy.ssafit.model.dao;

import java.util.List;

import com.ssafy.ssafit.model.dto.User;

public interface UserDao {
	int insertUser(User user);
	User selectUserById(String userId);
	User selectUserByEmail(String email);
	int updateUser(User user);
	int deleteUser(String userId);
	
	List<User> selectAll();
}
