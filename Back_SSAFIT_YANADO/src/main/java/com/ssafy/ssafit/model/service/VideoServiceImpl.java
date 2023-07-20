package com.ssafy.ssafit.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.VideoDao;
import com.ssafy.ssafit.model.dto.Video;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoDao videoDao;
	
	@Override
	public int createVideo(Video video) {
		return videoDao.insertVideo(video);
	}
	
	@Override
	public Video viewVideo(String videoId) {
		videoDao.updateViewCnt(videoId);
		return videoDao.selectVideo(videoId);
	}
	
	@Override
	public Video selectVideo(String videoId) {
		return videoDao.selectVideo(videoId);
	}
	
	@Override
	public List<Video> selectAllVideo() {
		return videoDao.selectAllVideo();
	}

	@Override
	public List<Video> selectVideoByLikeCnt() {
		return videoDao.selectVideoByLikeCnt();
	}

	@Override
	public List<Video> selectVideoByViewCnt() {
		return videoDao.selectVideoByViewCnt();
	}
	
}