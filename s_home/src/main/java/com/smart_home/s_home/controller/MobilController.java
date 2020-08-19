package com.smart_home.s_home.controller;

import java.util.List;

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
import com.smart_home.s_home.service.impl.BoardImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/mobil")

public class MobilController {
	
	private BoardImpl boardImpl = new BoardImpl();
	
	@GetMapping("/{serial}")
	public ApiResponse<Board> getOne(@PathVariable String serial){
        return new ApiResponse<Board>(HttpStatus.OK.value(), "Command Board saved successfully.", boardImpl.oneBoard(serial));
	}
	
	@PostMapping
    public ApiResponse<BoardDto> saveBoard(@RequestBody BoardDto board){
        return new ApiResponse<>(HttpStatus.OK.value(), "Command Board saved successfully.",boardImpl.saveBoard(board));
    }
	@GetMapping("/user-boards/{username}")
    public ApiResponse<List<BoardDto>> listUserBoard(@PathVariable String username){
        return new ApiResponse<>(HttpStatus.OK.value(), "Command Board saved successfully.",boardImpl.findBoardByUserName(username));
    }

    @PutMapping
    public ApiResponse<BoardDto> editBoard(@RequestBody BoardDto boardDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Command Board updated successfully.",boardImpl.updateBoard(boardDto));
    }

    @DeleteMapping("/{serial}")
    public ApiResponse<Void> deleteBoard(@PathVariable String serial) {
    	boardImpl.deleteBoard(serial);
        return new ApiResponse<>(HttpStatus.OK.value(), "Command Board deleted successfully.", null);
    }
	
}
