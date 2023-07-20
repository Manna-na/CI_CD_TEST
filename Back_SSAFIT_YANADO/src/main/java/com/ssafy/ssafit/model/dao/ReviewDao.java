package com.ssafy.ssafit.model.dao;

import java.util.HashMap;
import java.util.List;

import com.ssafy.ssafit.model.dto.Review;

public interface ReviewDao {
	public int insertReview(Review review);
	public int deleteReview(int reviewId);
	public List<Review> selectReviewAll(String videoId);
	public Review selectReview(int reviewId);
	public int updateReview(Review review);
	public int upCommentCnt(String videoId);
	public int downCommentCnt(String videoId);
	public int didIReview(HashMap<String, Object> map);
}
