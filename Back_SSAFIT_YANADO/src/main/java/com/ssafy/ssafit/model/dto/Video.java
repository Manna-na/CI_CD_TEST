package com.ssafy.ssafit.model.dto;

public class Video {
	private String videoId;
	private String channelName;
	private String videoTitle;
	private int likeCnt;
	private int reviewCnt;
	private int viewCnt;
	private int commentCnt;
	

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public int getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}

	public int getReviewCnt() {
		return reviewCnt;
	}

	public void setReviewCnt(int reviewCnt) {
		this.reviewCnt = reviewCnt;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public int getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}

	@Override
	public String toString() {
		return "Video [videoId=" + videoId + ", channelName=" + channelName + ", videoTitle=" + videoTitle
				+ ", likeCnt=" + likeCnt + ", reviewCnt=" + reviewCnt + ", viewCnt=" + viewCnt + ", commentCnt="
				+ commentCnt + "]";
	}
	
	
}