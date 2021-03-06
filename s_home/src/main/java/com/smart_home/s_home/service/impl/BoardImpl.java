package com.smart_home.s_home.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.smart_home.s_home.data.BoardRepository;
import com.smart_home.s_home.model.BoardDto;


public class BoardImpl {
	
	public BoardImpl () {
		
	}

	BoardRepository boardRepository = new BoardRepository();
	ModuleImpl modul = new ModuleImpl();
	
	public List<BoardDto> allBoards() {
		boolean exist = true;
		
		List<BoardDto> allBoardList = new ArrayList<BoardDto>();
		List<BoardDto> usersBoardList = new ArrayList<BoardDto>();
		
		allBoardList.addAll(boardRepository.getAllBoards());
		usersBoardList.addAll(boardRepository.getUsersBoards());
		
		List<BoardDto> boardList = new ArrayList<BoardDto>();
		
		for (BoardDto board : allBoardList) {
			exist = true;
			for (BoardDto newBoard : usersBoardList) {
				if(board.getBoardSerial().equalsIgnoreCase(newBoard.getBoardSerial())) {
					exist = false;
					break;
				}
			}
			if(exist) {
				boardList.add(board);
			}
		}
		boardList.addAll(usersBoardList);
		return boardList;
	}
	
	public BoardDto oneBoard (String serial) {
		return boardRepository.oneBoard(serial);
	}
	
	public void deleteBoard(String serial) {
		
		boardRepository.deleteBoard(serial);
		modul.updateListe();
	}

	public List<BoardDto> findBoardByUserName(String username) {
		List<BoardDto> boardList = new ArrayList<>();
		boardList.addAll(boardRepository.findUserBoards(username));
		return boardList;
	}
	
	public List<BoardDto> findBoardByUserId(int id) {
		List<BoardDto> boardList = new ArrayList<>();
		boardList.addAll(boardRepository.findBoards(id));
		return boardList;
	}

    public BoardDto updateBoard(BoardDto boardDto) {
    	BoardDto board;
    	if(boardDto.getBoardContor() > 2) {
    		boardDto.setBoardAutoStart(true);
    		boardDto.setBoardContor(0);
    	}
    	board = boardRepository.updateNewValues(boardDto);
    	modul.updateListe();
        return board;
    }

	public BoardDto saveBoard(BoardDto boardDto) {
		BoardDto board;
		if(boardDto.getBoardRunTime() != null && !(boardDto.getBoardRunTime().equals(" "))) {
			int time = Integer.valueOf(boardDto.getBoardRunTime());
			int hour = 00 ;
			int min = 00 ;
			DecimalFormat form = new DecimalFormat("00");
			hour = (time / 60);
			min = (time - (hour * 60));
			boardDto.setBoardRunTime(form.format(hour)+":"+form.format(min)+":00");
			
		}
		
		if(boardDto.getBoardContor() > 2) {
    		boardDto.setBoardAutoStart(true);
    		boardDto.setBoardContor(0);
    	}
		board = boardRepository.saveBoard(boardDto);
		modul.updateListe();
        return board;
    }
}
