package com.ssafy.ssafit.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ssafit.model.dto.Review;
import com.ssafy.ssafit.model.service.ReviewService;
import com.ssafy.ssafit.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api-comment")
@Api(tags = "Comment 컨트롤러")
@CrossOrigin("*")
public class ReviewRestController {

	@Autowired
	private ReviewService rs;

	@Autowired
	private UserService us;

	ObjectMapper mapper = new ObjectMapper();
	
	@ApiOperation(value = "댓글 목록", notes = "videoId에 해당하는 댓글 목록 가져옴")
	@GetMapping("/list/{videoId}")
	public ResponseEntity<?> list(@PathVariable String videoId) {
		List<Review> list = rs.getReviewList(videoId);
		if (list == null || list.size() == 0) {
			return new ResponseEntity<String>("NoReview", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Review>>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글", notes = "reviewId에 해당하는 댓글 가져옴")
	@GetMapping("/detail/{reviewId}")
	public ResponseEntity<?> list(@PathVariable int reviewId) {
		Review list = rs.getReview(reviewId);
		if (list == null) {
			return new ResponseEntity<String>("NoReview", HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Review>(list, HttpStatus.OK);
	}

	@Transactional
	@ApiOperation(value = "댓글 등록")
	@PostMapping("/write")
	public ResponseEntity<String> write(@RequestBody HashMap<String, Object> map){
		Review review = mapper.convertValue(map.get("review"), Review.class);
		String loginChk = us.loginChk(review.getUserId(), (String)map.get("loginUser"));
		if (loginChk.equals("OK")) {
			int chk = rs.writeReview(review);
			if (chk == 0) {
				return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<String>("OK", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(loginChk, HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	@ApiOperation(value = "댓글 수정")
	@PutMapping("/update")
	public ResponseEntity<String> update(Review review, @RequestBody String loginUser) {
		String userId = review.getUserId();
		String loginChk = us.loginChk(userId, loginUser);
		if (loginChk.equals("OK")) {
			int chk = rs.modifyReview(review);
			if (chk == 0) {
				return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<String>("OK", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(loginChk, HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	@ApiOperation(value = "댓글 삭제")
	@DeleteMapping("/delete/{reviewId}")
	public ResponseEntity<?> delete(@PathVariable int reviewId, @RequestBody String loginUser) {
		String userId = rs.getReview(reviewId).getUserId();
		String loginChk = us.loginChk(userId, loginUser);
		if (loginChk.equals("OK")) {
			int chk = rs.removeReview(reviewId);
			if (chk == 0) {
				return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<String>("OK", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(loginChk, HttpStatus.BAD_REQUEST);
		}
	}

}
