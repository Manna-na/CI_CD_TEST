package com.ssafy.ssafit.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ssafit.model.dao.ReviewDao;
import com.ssafy.ssafit.model.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewDao reviewDao;
	
	@Override
	public List<Review> getReviewList(String videoId) {
		return reviewDao.selectReviewAll(videoId);
	}

	@Override
	public int writeReview(Review review) {
		reviewDao.upCommentCnt(review.getVideoId());
		return reviewDao.insertReview(review);
	}

	@Override
	public int modifyReview(Review review) {
		return reviewDao.updateReview(review);
	}

	@Override
	public int removeReview(int reviewId) {
		reviewDao.downCommentCnt(reviewDao.selectReview(reviewId).getVideoId());
		return reviewDao.deleteReview(reviewId);
	}
	
	@Override
    public Review getReview(int reviewId) {
        return reviewDao.selectReview(reviewId);
    }
	
	@Override
	public void downCommentCnt(int delta, String videoId) {
		for(int i=0;i<delta;i++) {
			reviewDao.downCommentCnt(videoId);
		}
	}
	
	@Override
	public int didIReview(String videoId, String userId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("videoId", videoId);
		map.put("userId", userId);
		return reviewDao.didIReview(map);
	}
}
