package com.ssafy.ssafit.model.dao;

import java.util.HashMap;

import com.ssafy.ssafit.model.dto.Pause;

public interface PauseDao {
	int insertPause(Pause pause);
	int deletePause(int pauseId);
	int updatePause(Pause pause);
	Pause selectPause(HashMap<String, String> map);
}
