package com.smart_home.s_home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart_home.s_home.model.ApiResponse;
import com.smart_home.s_home.model.Board;
import com.smart_home.s_home.model.BoardDto;
import com.smart_home.s_home.service.BoardService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/boards")
public class BoardController {

	private BoardService boardService;
	
	@PostMapping
    public ApiResponse<Board> saveBoard(@RequestBody BoardDto board){
        return new ApiResponse<>(HttpStatus.OK.value(), "Command Board saved successfully.",boardService.saveBoard(board));
    }
	@GetMapping
    public ApiResponse<List<Board>> listUserBoard(@PathVariable int id){
        return new ApiResponse<>(HttpStatus.OK.value(), "Command Board  list fetched successfully.",boardService.findBoardByUserId(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<BoardDto> updateBoard(@RequestBody BoardDto boardDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Command Board  updated successfully.",boardService.updateBoard(boardDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBoard(@PathVariable String serial) {
    	boardService.deleteBoard(serial);
        return new ApiResponse<>(HttpStatus.OK.value(), "Command Board  fetched successfully.", null);
    }
}
