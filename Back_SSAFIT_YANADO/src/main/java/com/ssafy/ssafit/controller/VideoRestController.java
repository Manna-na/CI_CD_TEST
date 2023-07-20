package com.ssafy.ssafit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ssafit.model.dto.Video;
import com.ssafy.ssafit.model.service.LikeService;
import com.ssafy.ssafit.model.service.PauseService;
import com.ssafy.ssafit.model.service.VideoService;
import com.ssafy.ssafit.util.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api-video")
@Api(tags = "Video 컨트롤러")
@CrossOrigin("*")
public class VideoRestController {

	@Autowired
	private VideoService vs;

	@Autowired
	private LikeService ls;
	
	@Autowired
	private PauseService ps;

	@Autowired
	JwtUtil jwtUtil;

	// 비디오 생성
	@Transactional
	@ApiOperation(value = "비디오 생성")
	@PostMapping("/create")
	public ResponseEntity<String> create(@RequestBody Video video) {
		System.out.println("비디오 생성 "+ video.getChannelName() + " " + video.getVideoId() + " "+ video.getVideoTitle());
		int result = vs.createVideo(video);
		// 비디오 생성 실패했으면 예외
		if (result == 0) {
			return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
		}
		System.out.println("영상 넣는중");
		return new ResponseEntity<String>("OK", HttpStatus.CREATED);
	}

	// 비디오 상세보기
	@ApiOperation(value = "비디오 상세보기 + 조회수")
	@GetMapping("/{videoId}")
	public ResponseEntity<Video> readVideo(@PathVariable String videoId) {
		Video video = vs.viewVideo(videoId);
		return new ResponseEntity<Video>(video, HttpStatus.OK);
	}

	@ApiOperation(value = "DB에 비디오 있는지 확인")
	@PostMapping("/{videoId}")
	public ResponseEntity<Boolean> chkVideo(@PathVariable String videoId) {
		System.out.println("확인중");
		Video video = vs.selectVideo(videoId);
		if (video != null ) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
	}

	// 비디오 전체 가져오기? 근데 이게 필요한가?
	@ApiOperation(value = "비디오 전체 가져오기")
	@GetMapping("/video")
	public ResponseEntity<List<Video>> video() {
		List<Video> videos = vs.selectAllVideo();
		return new ResponseEntity<List<Video>>(videos, HttpStatus.OK);
	}

	// 비디오 좋아요 순으로 10개 가져오기
	@ApiOperation(value = "비디오 좋아요 순으로 10개 가져오기")
	@GetMapping("/like")
	public ResponseEntity<?> videoByLikeCnt() {
		List<Video> videos = vs.selectVideoByLikeCnt();
		if (videos == null || videos.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Video>>(videos, HttpStatus.OK);
	}

	// 비디오 조회수 순으로 10개 가져오기
	@ApiOperation(value = "비디오 조회수 순으로 10개 가져오기")
	@GetMapping("/view")
	public ResponseEntity<?> videosByViewCnt() {
		List<Video> videos = vs.selectVideoByViewCnt();
		if (videos == null || videos.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Video>>(videos, HttpStatus.OK);
	}

	// 비디오에 좋아요 누르기
	@ApiOperation(value = "비디오에 좋아요를 달아줍니다.")
	@PostMapping("/{videoId}/{userId}/like")
	public ResponseEntity<?> whatsAfterLike(@PathVariable String userId, @PathVariable String videoId) {
		if (vs.selectVideo(videoId) == null) {
			return new ResponseEntity<String>("NoSuchVideo", HttpStatus.NOT_FOUND);
		}
		if (userId == null) {
			return new ResponseEntity<String>("NeedLogin", HttpStatus.BAD_REQUEST);
		}
		int delta = ls.updateLike(userId, videoId);
		ls.updataLikeCnt(videoId, delta);
		return new ResponseEntity<Integer>(delta, HttpStatus.OK);
	}

	@ApiOperation(value = "영상에 좋아요가 몇개 있는지 확인합니다.")
	@GetMapping("/{videoId}/like")
	public ResponseEntity<?> LikeCnt(@PathVariable String videoId) {
		System.out.println("좋아요 확인에 들어온 아이디 " + videoId);
		System.out.println("좋아요 확인에 들어온 아이디로 검색한 영상 "+vs.selectVideo(videoId).toString());
		if (vs.selectVideo(videoId) == null) {
			return new ResponseEntity<String>("NoSuchVideo", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Integer>(ls.getLikeCnt(videoId), HttpStatus.OK);
	}

	@ApiOperation(value = "특정 유저가 영상에 좋아요를 눌렀는지 알아봅니다")
	@GetMapping("/{videoId}/{userId}/like")
	public ResponseEntity<String> didILike(@PathVariable String videoId, @PathVariable String userId) {
		if (ls.didILike(userId, videoId).equals("YesIDid")) {
			return new ResponseEntity<String>("Yes", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No", HttpStatus.OK);
		}
	}
	
//	@ApiOperation(value = "영상에 북마크를 달아줍니다.")
//	@PostMapping("/{videoId}/bookmark")
//	public ResponseEntity<String> insertPause(@PathVariable String videoId, @RequestBody String loginUser){
//		ps.insertPause(pause);
//	}
	
}
