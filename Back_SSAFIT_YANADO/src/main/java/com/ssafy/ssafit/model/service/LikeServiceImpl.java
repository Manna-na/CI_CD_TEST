package com.ssafy.ssafit.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.LikeDao;

@Service
public class LikeServiceImpl implements LikeService {

	
	@Autowired
	private LikeDao likeDao;
	
	@Override
	public Integer updateLike(String userId, String videoId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("videoId", videoId);
		if(likeDao.didILike(map) != null ) {
			// 가져온 likeId가 있다 = like가 있으면
			likeDao.deleteLike(likeDao.didILike(map));
			return -1;
		}else{
			likeDao.insertLike(map);
			return 1;
		}
	}

	@Override
	public String didILike(String userId, String videoId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("videoId", videoId);
		if(likeDao.didILike(map) != null) {
			return "YesIDid";
		}else {
			return "NoIDidnt";
		}
	}

	@Override
	public int updataLikeCnt(String videoId, Integer delta) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("videoId", videoId);
		map.put("delta", delta);
		return likeDao.updateLikeCnt(map);
	}

	@Override
	public Integer getLikeCnt(String videoId) {
		return likeDao.getLikeCnt(videoId);
	}

	@Override
	public List<String> selectLikeByUserId(String userId) {
		return likeDao.selectLikeByUserId(userId);
	}

}
