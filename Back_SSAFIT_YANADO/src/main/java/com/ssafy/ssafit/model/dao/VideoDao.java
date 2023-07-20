package com.ssafy.ssafit.model.dao;

import java.util.List;
import com.ssafy.ssafit.model.dto.Video;

public interface VideoDao {
	public int insertVideo(Video video);
	public Video selectVideo(String videoId);
	public int updateViewCnt(String videoId);
	public List<Video> selectAllVideo();
	public List<Video> selectVideoByLikeCnt();
	public List<Video> selectVideoByViewCnt();
}