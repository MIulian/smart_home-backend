package com.smart_home.s_home.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.smart_home.s_home.data.BoardRepository;
import com.smart_home.s_home.model.Board;
import com.smart_home.s_home.model.BoardDto;


public class BoardImpl {
	
	public BoardImpl () {
		
	}

	BoardRepository boardRepository = new BoardRepository();
	
	public List<Board> allBoards() {
		List<Board> allBoardList = new ArrayList<>();
		allBoardList.addAll(boardRepository.allBoards());
		return allBoardList;
	}
	
	public void deleteBoard(String serial) {
		boardRepository.deleteBoard(serial);
	}

	public List<Board> findBoardByUserId(int id) {
		List<Board> boardList = new ArrayList<>();
		boardList.addAll(boardRepository.findBoards(id));
		return boardList;
	}

    public BoardDto updateBoard(BoardDto boardDto) {
    	Board board = boardRepository.oldBoard(boardDto.getBoardId());
    	if(board != null) {
    		boardRepository.updateNewValues(board);
    	}
        return boardDto;
    }

    public Board saveBoard(BoardDto board) {
    	Board newBoard = new Board();
    	newBoard.setBoardName(board.getBoardName());
    	newBoard.setBoardSerial(board.getBoardSerial());
    	newBoard.setBoardStart(board.getBoardStart());
    	newBoard.setBoardStart(board.getBoardStart());
    	newBoard.setBoardAutoStart(board.getBoardAutoStart());
    	newBoard.setBoardContor(board.getBoardContor());
    	newBoard.setBoardOff(board.getBoardOff());
        return boardRepository.saveBoard(newBoard);
    }
}
