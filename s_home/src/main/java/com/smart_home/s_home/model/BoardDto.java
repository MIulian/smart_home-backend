package com.smart_home.s_home.model;

public class BoardDto {

	private String username;
	private int boardId;
	private String boardName;
	private String boardSerial;
	private String boardStart;
	private String boardStartDate;
	private String boardRunTime;
	private boolean boardAutoStart;
	private int boardContor;
	private boolean boardOff;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getBoardSerial() {
		return boardSerial;
	}
	public void setBoardSerial(String boardSerial) {
		this.boardSerial = boardSerial;
	}
	public String getBoardStart() {
		return boardStart;
	}
	public void setBoardStartDate(String boardStartDate) {
		this.boardStartDate = boardStartDate;
	}
	public String getBoardStartDate() {
		return boardStartDate;
	}
	public void setBoardRunTime(String boardRunTime) {
		this.boardRunTime = boardRunTime;
	}
	public String getBoardRunTime() {
		return boardRunTime;
	}
	public void setBoardStart(String boardStart) {
		this.boardStart = boardStart;
	}
	public int getBoardAutoStartInt() {
		if( this.boardAutoStart == false) {
			return 0;
		}
		return 1;
	}
	public boolean getBoardAutoStart() {
		return boardAutoStart;
	}
	public void setBoardAutoStart(boolean boardAutoStart) {
		this.boardAutoStart = boardAutoStart;
	}
	public int getBoardContor() {
		return boardContor;
	}
	public void setBoardContor(int boardContor) {
		this.boardContor = boardContor;
	}
	public int getBoardOffInt() {
		if(this.boardOff == false) {
			return 0;
		}
		return 1;
	}
	public boolean getBoardOff() {
		return boardOff;
	}
	public void setBoardOff(boolean boardOff) {
		this.boardOff = boardOff;
	}
	
	
	
}
