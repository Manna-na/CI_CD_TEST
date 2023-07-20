package com.ssafy.ssafit.model.dto;

public class Review {
	private int reviewId;
	private String videoId;
	private String userId;
	private String content;
	private String date;
	private boolean isupdated;
	

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isIsupdated() {
		return isupdated;
	}

	public void setIsupdated(boolean isupdated) {
		this.isupdated = isupdated;
	}
	
}