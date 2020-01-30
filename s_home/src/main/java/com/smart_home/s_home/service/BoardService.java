package com.smart_home.s_home.service;

import java.util.List;

import com.smart_home.s_home.model.Board;
import com.smart_home.s_home.model.BoardDto;

public interface BoardService {

	Board saveBoard(BoardDto board);
	
	void deleteBoard(String serial);
	
	BoardDto updateBoard(BoardDto boardDto);
	
	List<Board> findBoardByUserId(int id);
}
