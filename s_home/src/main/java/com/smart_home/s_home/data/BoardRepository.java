package com.smart_home.s_home.data;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.smart_home.s_home.model.Board;
import com.smart_home.s_home.model.BoardDto;

public class BoardRepository {
	
	private final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/s_home_db";
	private final String DATABASE_USER = "root";
	private final String DATABASE_PASSWORD = "Iulian0107.";
	
	public List<Board> allBoards () {
		List<Board> allBoards = new ArrayList<Board>();
		String queryAllBoards = "SELECT * FROM commandboard";
		//"SELECT U.username ,CB.board_id, CB.board_name, CB.board_serial, CB.board_start, CB.board_auto_start, CB.board_contor, CB.board_off FROM commandboard CB ,user U, connections C where C.FK_USER_ID = U.id and CB.board_id = C.FK_BOARD_ID order by U.username;"
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(queryAllBoards);
			
			while(rs.next()) {
				Board board = new Board();
				board.setBoardId(rs.getInt("board_id"));
				board.setBoardName(rs.getString("board_name"));
				board.setBoardSerial(rs.getString("board_serial"));
				board.setBoardStart(rs.getDate("board_start"));
				board.setBoardAutoStart(rs.getInt("board_auto_start"));
				board.setBoardContor(rs.getInt("board_contor"));
				board.setBoardOff(rs.getInt("board_off"));
				allBoards.add(board);
			}
			
			stmt.close();
			rs.close();
		}catch (Exception e) {
			System.err.println("Error found in BoardRepository=>findBoards");
			e.printStackTrace();
		}
		
		return allBoards;
		
	}
	
	public void deleteBoard(String serial){
		
		String queryDelete ="DELETE FROM commandboard where board_serial = ?";
		
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)) {
			PreparedStatement pstmt = conn.prepareStatement(queryDelete);
			pstmt.setString(1, serial);
			pstmt.execute();
			
			conn.commit();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>deleteBoard");
			e.printStackTrace();
		}
		
	}

	public Board saveBoard(Board board) {
		PreparedStatement pstmt = null;
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)) {
			String querySave = "INSERT INTO commandboard(board_name, board_serial, board_start, board_auto_start, board_contor, board_off) VALUES( ? , ? , ? , ? , ? , ? )";
			
			
			pstmt = conn.prepareStatement(querySave);
			pstmt.setString(1, board.getBoardName());
			pstmt.setString(2, board.getBoardSerial());
			pstmt.setDate(3, (Date) board.getBoardStart());
			pstmt.setInt(4, board.getBoardAutoStart());
			pstmt.setInt(5, board.getBoardContor());
			pstmt.setInt(6, board.getBoardOff());
			pstmt.executeUpdate();
			
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>saveBoard");
			e.printStackTrace();
		}
		return board;
	}
	
	private int getContorValue(String serial) {
		int contorValue = 0;
		StringBuilder queryContor = new StringBuilder("SELECT board_contor FROM commandboard WHERE board_serial = '")
				.append(serial)
				.append("' ");
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);Statement stmt = conn.createStatement();) {
			 
			ResultSet rs = stmt.executeQuery(String.valueOf(queryContor));
			
			contorValue = rs.getInt("board_contor");
			
			conn.commit();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>getContorValue");
			e.printStackTrace();
		}
		
		return contorValue;
	}
	
	//gaseste toate panourile de comanda ale unui user
	public List<Board> findBoards(int userId) {
		List<Board> list = new ArrayList<>();
		String queryBoards = "SELECT board_id, board_name, board_serial, board_start, board_auto_start, board_contor, board_off  FROM commandboard JOIN connections on connections.FK_BOARD_ID = commandboard.board_id and connections.FK_USER_ID= "+ userId;
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(queryBoards);
			
			while(rs.next()) {
				Board board = new Board();
				board.setBoardId(rs.getInt("board_id"));
				board.setBoardName(rs.getString("board_name"));
				board.setBoardSerial(rs.getString("board_serial"));
				board.setBoardStart(rs.getDate("board_start"));
				board.setBoardAutoStart(rs.getInt("board_auto_start"));
				board.setBoardContor(rs.getInt("board_contor"));
				board.setBoardOff(rs.getInt("board_off"));
				list.add(board);
			}
			
			stmt.close();
			rs.close();
		}catch (Exception e) {
			System.err.println("Error found in BoardRepository=>findBoards");
			e.printStackTrace();
		}
			
		return list;
	}
	
	public BoardDto updateNewValues(Board board) {
		
		BoardDto newBoard=new BoardDto();
		PreparedStatement pstmt = null;
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)) {
			String querySave = "UPDATE commandboard SET board_name=? , board_serial=? , board_start=? , board_auto_start=? , board_contor=? , board_off=? ";
			
			
			pstmt = conn.prepareStatement(querySave);
			pstmt.setString(1, board.getBoardName());
			pstmt.setString(2, board.getBoardSerial());
			pstmt.setDate(3, (Date) board.getBoardStart());
			pstmt.setInt(4, board.getBoardAutoStart());
			pstmt.setInt(5, board.getBoardContor());
			pstmt.setInt(6, board.getBoardOff());
			pstmt.executeUpdate();
			
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>updateNewValues");
			e.printStackTrace();
		}
		return newBoard;
	}
	
	public Board oldBoard(int boardId) {
		Board board=new Board();
		
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)) {
			String boardQuery = "SELECT * FROM commandboard WHERE board_id = "+boardId;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(boardQuery);
			
			while(rs.next()) {
				board.setBoardId(rs.getInt("board_id"));
				board.setBoardName(rs.getString("board_name"));
				board.setBoardSerial(rs.getString("board_serial"));
				board.setBoardStart(rs.getDate("board_start"));
				board.setBoardAutoStart(rs.getInt("board_auto_start"));
				board.setBoardContor(rs.getInt("board_contor"));
				board.setBoardOff(rs.getInt("board_off"));
			}
			
			stmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>oldBoard");
			e.printStackTrace();
		}
		return board;
	}
}
