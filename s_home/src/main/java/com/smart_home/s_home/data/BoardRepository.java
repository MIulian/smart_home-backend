package com.smart_home.s_home.data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.smart_home.s_home.model.Board;
import com.smart_home.s_home.model.BoardDto;

public class BoardRepository {
	
	private final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/s_home_db";
	private final String DATABASE_USER = "root";
	private final String DATABASE_PASSWORD = "Iulian0107.";
	
	public List<BoardDto> getAllBoards () {
		List<BoardDto> allBoards = new ArrayList<BoardDto>();
		String queryAllBoards = "SELECT * FROM commandboard";
		
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)){
			Statement stmt = conn.createStatement();
			System.out.println("BoardRepository=>getAllBoards=> "+queryAllBoards);
			ResultSet rs = stmt.executeQuery(queryAllBoards);
			
			while(rs.next()) {
				BoardDto board = new BoardDto();
				board.setUsername(" ");
				board.setBoardId(rs.getInt("board_id"));
				board.setBoardName(rs.getString("board_name"));
				board.setBoardSerial(rs.getString("board_serial"));
				board.setBoardStart(rs.getString("board_start"));
				board.setBoardAutoStart(rs.getInt("board_auto_start"));
				board.setBoardContor(rs.getInt("board_contor"));
				board.setBoardOff(rs.getInt("board_off"));
				allBoards.add(board);
			}
			
			stmt.close();
			rs.close();
		}catch (Exception e) {
			System.err.println("Error found in BoardRepository=>getAllBoards");
			e.printStackTrace();
		}
		
		return allBoards;
		
	}
	
	public List<BoardDto> getUsersBoards(){
		
		String queryBoards = "SELECT username, board_id, board_name, board_serial, board_start, board_auto_start, board_contor, board_off FROM commandboard, user, connections where connections.FK_BOARD_ID = board_id and connections.FK_USER_ID= id";
		List<BoardDto> usersBoards = new ArrayList<BoardDto>();
		
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)){
			Statement stmt = conn.createStatement();
			System.out.println("BoardRepository=>getUsersBoards=> "+queryBoards);
			ResultSet rs = stmt.executeQuery(queryBoards);
			
			while(rs.next()) {
				BoardDto board = new BoardDto();
				board.setUsername(rs.getString("username"));
				board.setBoardId(rs.getInt("board_id"));
				board.setBoardName(rs.getString("board_name"));
				board.setBoardSerial(rs.getString("board_serial"));
				board.setBoardStart(rs.getString("board_start"));
				board.setBoardAutoStart(rs.getInt("board_auto_start"));
				board.setBoardContor(rs.getInt("board_contor"));
				board.setBoardOff(rs.getInt("board_off"));
				usersBoards.add(board);
			}
			
			stmt.close();
			rs.close();
		}catch (Exception e) {
			System.err.println("Error found in BoardRepository=>getUsersBoards");
			e.printStackTrace();
		}
		
		return usersBoards;
	}
	
	public Board oneBoard (String serial) {
		Board board = new Board();
		String queryOneBoards = "Select * from commandboard where board_serial = '"+serial+"' ";
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)){
			Statement stmt = conn.createStatement();
			System.out.println("BoardRepository=>oneBoard=> "+queryOneBoards);
			ResultSet rs = stmt.executeQuery(queryOneBoards);
			
			while(rs.next()) {
				board.setBoardId(rs.getInt("board_id"));
				board.setBoardName(rs.getString("board_name"));
				board.setBoardSerial(rs.getString("board_serial"));
				board.setBoardStart(LocalTime.parse(rs.getString("board_start")));
				board.setBoardAutoStart(rs.getInt("board_auto_start"));
				board.setBoardContor(rs.getInt("board_contor"));
				board.setBoardOff(rs.getInt("board_off"));
			}
			
			stmt.close();
			rs.close();
		}catch (Exception e) {
			System.err.println("Error found in BoardRepository=>oneBoard");
			e.printStackTrace();
		}
		
		return board;
		
	}
	
	public void deleteBoard(String serial){
		
		String queryGetBoardId ="SELECT board_id FROM commandboard WHERE board_serial = '"+serial+"'";
		String queryDeleteConnectoins ="DELETE FROM connections WHERE fk_board_id = ?";
		String queryDeleteComandboard ="DELETE FROM commandboard WHERE board_serial = ?";
		int id = 0;
		
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)) {
			
			
			Statement stmt = conn.createStatement();
			System.out.println("BoardRepository=>deleteBoard=> get boardId from comandboard table "+queryGetBoardId);
			
			ResultSet rs = stmt.executeQuery(queryGetBoardId);
			
			while(rs.next()) {
				id = rs.getInt("board_id");
			}
			stmt.close();
			PreparedStatement pstmt = conn.prepareStatement(queryDeleteConnectoins);
			pstmt.setInt(1, id);
			System.out.println("BoardRepository=>deleteBoard=> delete from connections table "+queryDeleteConnectoins);
			System.out.println(id);
			pstmt.execute();
			
			pstmt = conn.prepareStatement(queryDeleteComandboard);
			pstmt.setString(1, serial);
			System.out.println("BoardRepository=>deleteBoard=> delete from comandboard table "+queryDeleteComandboard);
			System.out.println(serial);
			pstmt.execute();
			
			pstmt.close();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>deleteBoard");
			e.printStackTrace();
		}
		
	}

	public BoardDto saveBoard(BoardDto board) {
		
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)) {
			String queryGetUserId = "SELECT id FROM user WHERE username='"+board.getUsername()+"'";
			String querySaveNewConnection = "INSERT INTO connections(FK_USER_ID, FK_BOARD_ID) VALUES(?, ?)";
			
			int userId = 0;
			int boardId = 0;
			
			Statement stmt = conn.createStatement();
			System.out.println("BoardRepository=>saveBoard=> get id from user table:  "+queryGetUserId);
			ResultSet rs = stmt.executeQuery(queryGetUserId);
			
			while(rs.next()) {
				userId = rs.getInt("id");
			}
			
			if(insertBoard(board)) {				
				
				boardId = getBoardId(board.getBoardSerial());
				
				PreparedStatement pstmt = conn.prepareStatement(querySaveNewConnection);
				pstmt.setInt(1, userId);
				pstmt.setInt(2, boardId);
				System.out.println("BoardRepository=>saveBoard=> insert new Connection: "+querySaveNewConnection);
				pstmt.executeUpdate();
				
				pstmt.close();
			}else {
				return null;
			}
			rs.close();
			stmt.close();
			
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
			System.out.println("BoardRepository=>getContorValue=> "+queryContor);
			ResultSet rs = stmt.executeQuery(String.valueOf(queryContor));
			
			contorValue = rs.getInt("board_contor");
			
			conn.commit();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>getContorValue");
			e.printStackTrace();
		}
		
		return contorValue;
	}
	
	public List<BoardDto> findBoards(int userId) {
		List<BoardDto> list = new ArrayList<>();
		String queryBoards = "SELECT username, board_id, board_name, board_serial, board_start, board_auto_start, board_contor, board_off  FROM commandboard, user, connections where connections.FK_BOARD_ID = board_id and connections.FK_USER_ID= id and id = "+ userId;
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)){
			Statement stmt = conn.createStatement();
			System.out.println("BoardRepository=>findBoards=> "+queryBoards);
			ResultSet rs = stmt.executeQuery(queryBoards);
			
			while(rs.next()) {
				BoardDto board = new BoardDto();
				board.setUsername(rs.getString("username"));
				board.setBoardId(rs.getInt("board_id"));
				board.setBoardName(rs.getString("board_name"));
				board.setBoardSerial(rs.getString("board_serial"));
				board.setBoardStart(rs.getString("board_start"));
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
	
	public BoardDto updateNewValues(BoardDto board) {
		
		BoardDto newBoard=new BoardDto();
		PreparedStatement pstmt = null;
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)) {
			String queryUpdate = "UPDATE commandboard SET board_name=? , board_start=? , board_auto_start=? , board_contor=? , board_off=? WHERE board_serial = ?";
			
			
			pstmt = conn.prepareStatement(queryUpdate);
			pstmt.setString(1, board.getBoardName());
			pstmt.setTime(2, Time.valueOf(board.getBoardStart()));
			pstmt.setInt(3, board.getBoardAutoStart());
			pstmt.setInt(4, board.getBoardContor());
			pstmt.setInt(5, board.getBoardOff());
			pstmt.setString(6, board.getBoardSerial());
			System.out.println("BoardRepository=>updateNewValues=> "+queryUpdate);
			System.out.println("valori noi: "+board.getBoardName()+", "+ board.getBoardStart()+" ,"+board.getBoardAutoStart()+" ,"+board.getBoardContor()+" ,"+board.getBoardOff());
			pstmt.executeUpdate();
			
			pstmt.close();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>updateNewValues");
			e.printStackTrace();
		}
		return newBoard;
	}
	
	public boolean oldBoard(int boardId) {
		boolean exist = false;
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)) {
			
			String boardQuery = "SELECT * FROM commandboard WHERE board_id = "+boardId;
			Statement stmt = conn.createStatement();
			System.out.println("BoardRepository=>oldBoard=> "+boardQuery);
			ResultSet rs = stmt.executeQuery(boardQuery);
			
			while(rs.next()) {
				exist = true;
			}
			
			stmt.close();
			rs.close();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>oldBoard");
			e.printStackTrace();
		}
		return exist;
	}
	
	public boolean insertBoard(BoardDto newBoard) {
		
		boolean complete=false;
		String querySaveNewBoard = "INSERT INTO commandboard(board_name, board_serial, board_start, board_auto_start, board_contor, board_off) VALUES( ? , ? , ? , ? , ? , ? )";
		
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)) {
		
			PreparedStatement pstmt = conn.prepareStatement(querySaveNewBoard);
			pstmt.setString(1, newBoard.getBoardName());
			pstmt.setString(2, newBoard.getBoardSerial());
			pstmt.setTime(3, Time.valueOf(newBoard.getBoardStart()));
			pstmt.setInt(4, newBoard.getBoardAutoStart());
			pstmt.setInt(5, newBoard.getBoardContor());
			pstmt.setInt(6, newBoard.getBoardOff());
			System.out.println("BoardRepository=>insertBoard=> insert new Board: "+querySaveNewBoard);
			pstmt.executeUpdate();
			complete = true;
			pstmt.close();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>insertBoard");
			e.printStackTrace();
		}
		return complete;
		
	}
	
	public int getBoardId(String serial) {
		
		int boardId = 0;
		String queryGetBoardId ="SELECT board_id FROM commandboard WHERE board_serial = '"+serial+"'";
		
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)) {
			
			Statement stmt = conn.createStatement();
			System.out.println("BoardRepository=>getBoardId=> get board_id from commandboard table:  "+queryGetBoardId);
			ResultSet rs = stmt.executeQuery(queryGetBoardId);
			
			while(rs.next()) {
				boardId = rs.getInt("board_id");
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println("Error found in BoardRepository=>insertBoard");
			e.printStackTrace();
		}
		return boardId;
	}
}
