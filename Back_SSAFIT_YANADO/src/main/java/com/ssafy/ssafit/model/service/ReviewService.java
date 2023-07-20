package com.ssafy.ssafit.model.service;

import java.util.List;

import com.ssafy.ssafit.model.dto.Review;

public interface ReviewService {
	public List<Review> getReviewList(String videoId);
	
	public int writeReview(Review review);
	
	public int modifyReview(Review review);
	
	public int removeReview(int reviewId);
	
	public Review getReview(int reviewId);
	
	public void downCommentCnt(int delta, String videoId);
	
	public int didIReview(String userId, String videoId);
}
