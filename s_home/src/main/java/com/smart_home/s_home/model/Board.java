package com.smart_home.s_home.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "commandboard")
public class Board {

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="board_id", nullable = false)
	private int boardId;
	@Column(name="board_name")
	private String boardName;
	@Column(name="board_serial")
	private String boardSerial;
	@Column(name="board_start")
	private LocalTime boardStart;
	@Column(name="board_auto_start")
	private int boardAutoStart;
	@Column(name="board_contor")
	private int boardContor;
	@Column(name="board_off")
	private int boardOff;
	
	public Board() {
		
	}

	public Board (BoardDto newBoard) {
		this.boardId = newBoard.getBoardId();
		this.boardName = newBoard.getBoardName();
		this.boardSerial = newBoard.getBoardSerial();
		this.boardStart = LocalTime.parse(newBoard.getBoardStart());
		this.boardAutoStart = newBoard.getBoardAutoStart();
		this.boardContor = newBoard.getBoardContor();
		this.boardOff = newBoard.getBoardOff();
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
	public LocalTime getBoardStart() {
		return boardStart;
	}
	public void setBoardStart(LocalTime boardStart) {
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
