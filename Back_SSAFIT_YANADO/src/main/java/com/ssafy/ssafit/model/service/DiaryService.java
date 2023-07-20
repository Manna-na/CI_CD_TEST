package com.ssafy.ssafit.model.service;

import java.util.List;

import com.ssafy.ssafit.model.dto.Diary;

public interface DiaryService {
	int insertDiary(Diary diary);
	int deleteDiary(int diaryId);
	List<Diary> selectDiaryAll(String userId);
	int updateDiary(Diary diary);
	Diary selectDiary(int diaryId);
}
