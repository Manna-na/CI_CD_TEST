package com.ssafy.ssafit.model.service;


import com.ssafy.ssafit.model.dto.Pause;

public interface PauseService {
	int insertPause(Pause pause);
	int deletePause(int pauseId);
	int updatePause(Pause pause);
	Pause selectPause(String userId, String videoId);
}
