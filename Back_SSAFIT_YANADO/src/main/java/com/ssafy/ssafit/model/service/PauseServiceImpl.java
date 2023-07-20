package com.ssafy.ssafit.model.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.PauseDao;
import com.ssafy.ssafit.model.dto.Pause;

@Service
public class PauseServiceImpl implements PauseService {

	@Autowired
	private PauseDao pauseDao;
	
	@Override
	public int insertPause(Pause pause) {
		return pauseDao.insertPause(pause);
	}

	@Override
	public int deletePause(int pauseId) {
		return pauseDao.deletePause(pauseId);
	}
	
	@Override
	public int updatePause(Pause pause) {
		return pauseDao.updatePause(pause);
	}

	@Override
	public Pause selectPause(String userId, String videoId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("videoId",videoId);
		return pauseDao.selectPause(map);
	}

}
