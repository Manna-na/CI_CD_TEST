package com.ssafy.ssafit.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ssafit.model.dto.Diary;
import com.ssafy.ssafit.model.service.DiaryService;
import com.ssafy.ssafit.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api-diary")
@Api(tags="Diary 컨트롤러")
@CrossOrigin("*")
public class DiaryRestController {

	@Autowired
	private DiaryService ds;
	
	@Autowired
	private UserService us;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@PostMapping("/list/{userId}")
	@ApiOperation(value = "특정 유저의 등록된 모든 일기를 반환한다.")
	public ResponseEntity<?> selectAll(@PathVariable String userId, @RequestBody String loginUser){
		String loginChk = us.loginChk(userId, loginUser);
		if(loginChk.equals("OK")) {
			List<Diary> list = ds.selectDiaryAll(userId);
			return new ResponseEntity<List<Diary>>(list, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(loginChk, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/{diaryId}")
	@ApiOperation(value="특정 일기를 반환한다.")
	public ResponseEntity<?> selectDiary(@PathVariable int diaryId, @RequestBody String loginUser){
		Diary diary = ds.selectDiary(diaryId);
		if(diary == null) {
			return new ResponseEntity<String>("NoSuchDiary", HttpStatus.BAD_REQUEST);
		}
		String userId = diary.getUserId();
		String loginChk = us.loginChk(userId, loginUser);
		if (loginChk.equals("OK")) {
		return new ResponseEntity<Diary>(diary, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(loginChk, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Transactional
	@PutMapping("/update")
	@ApiOperation(value = "일기 정보를 수정한다")
	public ResponseEntity<String> update(@RequestBody HashMap<String, Object> map){
		Diary diary =  mapper.convertValue(map.get("diary"), Diary.class);
		String loginChk = us.loginChk(diary.getUserId(), (String)map.get("loginUser"));
		if(loginChk.equals("OK")) {
			ds.updateDiary(diary);
			return new ResponseEntity<String>(loginChk, HttpStatus.OK);				
		}else {
			return new ResponseEntity<String>(loginChk, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Transactional
	@PostMapping("/insert")
	@ApiOperation(value = "일기 정보를 등록한다.")
	public ResponseEntity<String> insert(@RequestBody HashMap<String, Object> map){
		Diary diary =  mapper.convertValue(map.get("diary"), Diary.class);
		String loginChk = us.loginChk(diary.getUserId(), (String)map.get("loginUser"));
		if(loginChk.equals("OK")) {
			ds.insertDiary(diary);
			return new ResponseEntity<String>(loginChk, HttpStatus.OK);				
		}else {
			return new ResponseEntity<String>(loginChk, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@Transactional
	@DeleteMapping("/{diaryId}")
	@ApiOperation(value = "일기를 삭제합니다.")
	public ResponseEntity<String> delete(@PathVariable int diaryId, @RequestBody String loginUser){
		Diary diary = ds.selectDiary(diaryId);
		if(diary == null) {
			return new ResponseEntity<String>("NoSuchDiary", HttpStatus.BAD_REQUEST);
		}
		String loginChk = us.loginChk(diary.getUserId(), loginUser);
		if(loginChk.equals("OK")) {
			ds.deleteDiary(diaryId);
			return new ResponseEntity<String>(loginChk, HttpStatus.OK);				
		}else {
			return new ResponseEntity<String>(loginChk, HttpStatus.BAD_REQUEST);
		}
	}
}
