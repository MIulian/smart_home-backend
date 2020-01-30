package com.smart_home.s_home.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.smart_home.s_home.data.BoardRepository;
import com.smart_home.s_home.model.Board;
import com.smart_home.s_home.model.BoardDto;
import com.smart_home.s_home.service.BoardService;

public class BoardServiceImpl implements BoardService{

	BoardRepository boardRepository = new BoardRepository();
	
	@Override
	public void deleteBoard(String serial) {
		boardRepository.deleteBoard(serial);
	}

	@Override
	public List<Board> findBoardByUserId(int id) {
		//gasit in functie de user toate panourile de comanda asignate
		List<Board> boardList = new ArrayList<>();
		boardRepository.findBoards(id);
		return boardList;
	}

    @Override
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
