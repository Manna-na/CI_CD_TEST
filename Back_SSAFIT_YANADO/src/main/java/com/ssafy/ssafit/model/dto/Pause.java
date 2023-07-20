package com.ssafy.ssafit.model.dto;

public class Pause {
	private String videoId;
	private String userId;
	private int pauseTime;
	
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getPauseTime() {
		return pauseTime;
	}
	public void setPauseTime(int pauseTime) {
		this.pauseTime = pauseTime;
	}
	
}
