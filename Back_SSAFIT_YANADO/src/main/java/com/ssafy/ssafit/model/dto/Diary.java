package com.ssafy.ssafit.model.dto;

public class Diary {
	private int diaryId;
	private String userId;
	private String date;
	private String start;
	private String finish;
	private String content;
	private int manjok;
	private String workout;
	
	public int getDiaryId() {
		return diaryId;
	}
	public void setDiaryId(int diaryId) {
		this.diaryId = diaryId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getManjok() {
		return manjok;
	}
	public void setManjok(int manjok) {
		this.manjok = manjok;
	}
	public String getWorkout() {
		return workout;
	}
	public void setWorkout(String workout) {
		this.workout = workout;
	}

}
