package com.ssafy.ssafit.model.service;

import java.util.List;

public interface LikeService {
	Integer updateLike(String userId, String videoId);
	List<String> selectLikeByUserId(String userId);
	Integer getLikeCnt(String videoId);
	String didILike(String userId, String videoId);
	int updataLikeCnt(String videoId, Integer delta);
}
