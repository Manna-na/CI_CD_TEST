package com.ssafy.ssafit.model.service;

import java.util.List;

import com.ssafy.ssafit.model.dto.Video;


public interface VideoService {
	public int createVideo(Video video);
	public Video viewVideo(String videoId);
	public Video selectVideo(String videoId);
	public List<Video> selectAllVideo();
	public List<Video> selectVideoByLikeCnt();
	public List<Video> selectVideoByViewCnt();
	
	
}