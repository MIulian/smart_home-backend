package com.smart_home.s_home.model;

import java.util.Date;

public class BoardDto {

	private int boardId;
	private String boardName;
	private String boardSerial;
	private Date boardStart;
	private int boardAutoStart;
	private int boardContor;
	private int boardOff;
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
	public Date getBoardStart() {
		return boardStart;
	}
	public void setBoardStart(Date boardStart) {
		this.boardStart = boardStart;
	}
	public int getBoardAutoStart() {
		return boardAutoStart;
	}
	public void setBoardAutoStart(int boardAutoStart) {
		this.boardAutoStart = boardAutoStart;
	}
	public int getBoardContor() {
		return boardContor;
	}
	public void setBoardContor(int boardContor) {
		this.boardContor = boardContor;
	}
	public int getBoardOff() {
		return boardOff;
	}
	public void setBoardOff(int boardOff) {
		this.boardOff = boardOff;
	}
	
	
	
}
