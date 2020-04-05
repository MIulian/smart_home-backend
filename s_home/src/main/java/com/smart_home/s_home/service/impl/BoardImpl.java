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
	
	public Board oneBoard (String serial) {
		return boardRepository.oneBoard(serial);
	}
	
	public void deleteBoard(String serial) {
		boardRepository.deleteBoard(serial);
	}

	public List<BoardDto> findBoardByUserId(int id) {
		List<BoardDto> boardList = new ArrayList<>();
		boardList.addAll(boardRepository.findBoards(id));
		return boardList;
	}

    public BoardDto updateBoard(BoardDto boardDto) {
        return boardRepository.updateNewValues(boardDto);
    }

	public BoardDto saveBoard(BoardDto board) {
        return boardRepository.saveBoard(board);
    }
}
