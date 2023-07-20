package com.ssafy.ssafit.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.DiaryDao;
import com.ssafy.ssafit.model.dto.Diary;

@Service
public class DiaryServiceImpl implements DiaryService {

	@Autowired
	private DiaryDao diaryDao;
	
	@Override
	public int insertDiary(Diary diary) {
		return diaryDao.insertDiary(diary);
	}

	@Override
	public int deleteDiary(int diaryId) {
		return diaryDao.deleteDiary(diaryId);
	}

	@Override
	public List<Diary> selectDiaryAll(String userId) {
		return diaryDao.selectDiaryAll(userId);
	}

	@Override
	public int updateDiary(Diary diary) {
		return diaryDao.updateDiary(diary);
	}
	
	@Override
	public Diary selectDiary(int diaryId) {
		return diaryDao.selectDiary(diaryId);
	}

}
