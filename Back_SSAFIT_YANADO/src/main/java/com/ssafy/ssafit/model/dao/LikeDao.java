package com.ssafy.ssafit.model.dao;

import java.util.HashMap;
import java.util.List;

public interface LikeDao {
	int insertLike(HashMap<String, String> map);
	int deleteLike(int likeId);
	List<String> selectLikeByUserId(String userId);
	Integer didILike(HashMap<String, String> map);
	Integer getLikeCnt(String videoId);
	int updateLikeCnt(HashMap<String, Object> map);
}
