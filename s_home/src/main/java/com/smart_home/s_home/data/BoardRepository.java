package com.smart_home.s_home.data;

import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.smart_home.s_home.model.ArduinoModul;
import com.smart_home.s_home.model.BoardDto;

public class BoardRepository {
	
	private final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/s_home_db";
	private final String DATABASE_USER = "root";
	private final String DATABASE_PASSWORD = "Iulian0107.";
	private final SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
	private final SimpleDateFormat time = new SimpleDateFormat("HH:mm");
	private final SimpleDateFormat runTimeForm = new SimpleDateFormat("HH:mm");
	
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
				board.setBoardStart(time.format(rs.getTime("board_start")));
				if(rs.getDate("board_start_date") != null){
					board.setBoardStartDate(date.format(rs.getDate("board_start_date")));
				}
				if(rs.getDate("board_run_time") != null){
					board.setBoardRunTime(runTimeForm.format(rs.getTime("board_run_time")));
				}
				if(rs.getInt("board_auto_start") == 0) {
					board.setBoardAutoStart(false);
				}else {
					board.setBoardAutoStart(true);
				}
				board.setBoardContor(rs.getInt("board_contor"));
				if(rs.getInt("board_off") == 0) {
					board.setBoardOff(false);
				}else {
					board.setBoardOff(true);
				}
				
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
		
		String queryBoards = "SELECT username, board_id, board_name, board_serial, board_start, board_start_date, board_run_time, board_auto_start, board_contor, board_off FROM commandboard, user, connections where connections.FK_BOARD_ID = board_id and connections.FK_USER_ID= id";
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
				board.setBoardStart(time.format(rs.getTime("board_start")));
				if(rs.getDate("board_start_date") != null){
					board.setBoardStartDate(date.format(rs.getDate("board_start_date")));
				}
				if(rs.getDate("board_run_time") != null){
					board.setBoardRunTime(runTimeForm.format(rs.getTime("board_run_time")));
				}
				if(rs.getInt("board_auto_start") == 0) {
					board.setBoardAutoStart(false);
				}else {
					board.setBoardAutoStart(true);
				}
				board.setBoardContor(rs.getInt("board_contor"));
				if(rs.getInt("board_off") == 0) {
					board.setBoardOff(false);
				}else {
					board.setBoardOff(true);
				}
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
	
	public BoardDto oneBoard (String serial) {
		BoardDto boardDto = new BoardDto();
		String queryOneBoards = "Select * from commandboard where board_serial = '"+serial+"' ";
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)){
			Statement stmt = conn.createStatement();
			System.out.println("BoardRepository=>oneBoard=> "+queryOneBoards);
			ResultSet rs = stmt.executeQuery(queryOneBoards);
			
			while(rs.next()) {
				boardDto.setBoardId(rs.getInt("board_id"));
				boardDto.setBoardName(rs.getString("board_name"));
				boardDto.setBoardSerial(rs.getString("board_serial"));
				boardDto.setBoardStart(time.format(rs.getTime("board_start")));
				boardDto.setBoardStartDate(rs.getString("board_start_date"));
				boardDto.setBoardRunTime(runTimeForm.format(rs.getTime("board_run_time")));
				if(rs.getInt("board_auto_start") == 1) {
					boardDto.setBoardAutoStart(true);
				}else {
					boardDto.setBoardAutoStart(false);
				}
				boardDto.setBoardContor(rs.getInt("board_contor"));
				if(rs.getInt("board_off") == 1) {
					boardDto.setBoardOff(true);
				}else {
					boardDto.setBoardOff(false);
				}
			}
			
			stmt.close();
			rs.close();
		}catch (Exception e) {
			System.err.println("Error found in BoardRepository=>oneBoard");
			e.printStackTrace();
		}
		
		return boardDto;
		
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
	
	public List<BoardDto> findBoards(int userId) {
		List<BoardDto> list = new ArrayList<>();
		String queryBoards = "SELECT username, board_id, board_name, board_serial, board_start, board_start_date, board_run_time, board_auto_start, board_contor, board_off  FROM commandboard, user, connections where connections.FK_BOARD_ID = board_id and connections.FK_USER_ID= id and id = "+ userId;
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
				board.setBoardStart(time.format(rs.getTime("board_start")));
				if(rs.getDate("board_start_date") != null){
					board.setBoardStartDate(date.format(rs.getDate("board_start_date")));
				}
				if(rs.getDate("board_run_time") != null){
					board.setBoardRunTime(runTimeForm.format(rs.getTime("board_run_time")));
				}
				if(rs.getInt("board_auto_start") == 0) {
					board.setBoardAutoStart(false);
				}else {
					board.setBoardAutoStart(true);
				}
				board.setBoardContor(rs.getInt("board_contor"));
				if(rs.getInt("board_off") == 0) {
					board.setBoardOff(false);
				}else {
					board.setBoardOff(true);
				}
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
	
	public List<BoardDto> findUserBoards(String username) {
		List<BoardDto> list = new ArrayList<>();
		String queryBoards = "SELECT board_id, board_name, board_serial, board_start, board_start_date, board_run_time, board_auto_start, board_contor, board_off  FROM commandboard, user, connections where connections.FK_BOARD_ID = board_id and connections.FK_USER_ID= id and username = '"+ username+"'";
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)){
			Statement stmt = conn.createStatement();
			System.out.println("BoardRepository=>findUserBoards=> "+queryBoards);
			ResultSet rs = stmt.executeQuery(queryBoards);
			
			while(rs.next()) {
				BoardDto board = new BoardDto();
				board.setUsername(username);
				board.setBoardId(rs.getInt("board_id"));
				board.setBoardName(rs.getString("board_name"));
				board.setBoardSerial(rs.getString("board_serial"));
				board.setBoardStart(time.format(rs.getTime("board_start")));
				if(rs.getDate("board_start_date") != null){
					board.setBoardStartDate(date.format(rs.getDate("board_start_date")));
				}
				if(rs.getDate("board_run_time") != null){
					board.setBoardRunTime(runTimeForm.format(rs.getTime("board_run_time")));
				}
				if(rs.getInt("board_auto_start") == 0) {
					board.setBoardAutoStart(false);
				}else {
					board.setBoardAutoStart(true);
				}
				board.setBoardContor(rs.getInt("board_contor"));
				if(rs.getInt("board_off") == 0) {
					board.setBoardOff(false);
				}else {
					board.setBoardOff(true);
				}
				list.add(board);
			}
			
			stmt.close();
			rs.close();
		}catch (Exception e) {
			System.err.println("Error found in BoardRepository=>findUserBoards");
			e.printStackTrace();
		}
			
		return list;
	}
	
	public BoardDto updateNewValues(BoardDto board) {
		
		BoardDto newBoard=new BoardDto();
		PreparedStatement pstmt = null;
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)) {
			String queryUpdate = "UPDATE commandboard SET board_name=? , board_start=? , board_start_date=? , board_run_time=? , board_auto_start=? , board_contor=? , board_off=? WHERE board_serial = ?";
			
			
			pstmt = conn.prepareStatement(queryUpdate);
			pstmt.setString(1, board.getBoardName());
			pstmt.setTime(2, Time.valueOf(board.getBoardStart()+":00"));
			pstmt.setDate(3, Date.valueOf(board.getBoardStartDate()));
			pstmt.setTime(4, Time.valueOf(board.getBoardRunTime()+":00"));
			if(board.getBoardAutoStart() == false) {
				pstmt.setInt(5, 0);
			}else {
				pstmt.setInt(5, 1);
			}
			
			pstmt.setInt(6, board.getBoardContor());
			if(board.getBoardOff() == false) {
				pstmt.setInt(7, 0);
			}else {
				pstmt.setInt(7, 1);
			}
			pstmt.setString(8, board.getBoardSerial());
			
			System.out.println("BoardRepository=>updateNewValues=> "+queryUpdate);
			System.out.println("valori noi: "+board.getBoardName()+", "+ board.getBoardStart()+" ,"+board.getBoardStartDate()+" ,"+board.getBoardRunTime()+" ,"+board.getBoardAutoStart()+" ,"+board.getBoardContor()+" ,"+board.getBoardOff());
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
		String querySaveNewBoard = "INSERT INTO commandboard(board_name, board_serial, board_start, board_start_date, board_run_time, board_auto_start, board_contor, board_off) VALUES( ? , ? , ? , ? , ? , ? , ?   , ? )";
		
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)) {
		
			PreparedStatement pstmt = conn.prepareStatement(querySaveNewBoard);
			pstmt.setString(1, newBoard.getBoardName());
			pstmt.setString(2, newBoard.getBoardSerial());
			String time = newBoard.getBoardStart()+":00";
			pstmt.setTime(3, Time.valueOf(time));
			pstmt.setDate(4, Date.valueOf(newBoard.getBoardStartDate()));
			pstmt.setTime(5, Time.valueOf(newBoard.getBoardRunTime()));
			if(newBoard.getBoardAutoStart() == false) {
				pstmt.setInt(6, 0);
			}else {
				pstmt.setInt(6, 1);
			}
			
			pstmt.setInt(7, newBoard.getBoardContor());
			if(newBoard.getBoardOff() == false) {
				pstmt.setInt(8, 0);
			}else {
				pstmt.setInt(8, 1);
			}
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
	
	public List<ArduinoModul> boardsToExecute() {
		List<ArduinoModul> list = new ArrayList<>();
		String queryBoards = "select board_serial, board_start, board_start_date, board_run_time from commandboard where board_off = 1 order by board_start_date";
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)){
			Statement stmt = conn.createStatement();
			System.out.println("BoardRepository=>boardsToExecute=> "+queryBoards);
			ResultSet rs = stmt.executeQuery(queryBoards);
			
			while(rs.next()) {
				ArduinoModul modul = new ArduinoModul(
						rs.getString("board_serial"),
						rs.getString("board_start"),
						rs.getString("board_start_date"),
						rs.getString("board_run_time"),
						false);
						
				list.add(modul);
			}
			
			stmt.close();
			rs.close();
		}catch (Exception e) {
			System.err.println("Error found in BoardRepository=>findBoards");
			e.printStackTrace();
		}
			
		return list;
	}
	
	public List<ArduinoModul> boardsToExecuteDaily() {
		List<ArduinoModul> list = new ArrayList<>();
		String queryBoards = "select board_serial, board_start, board_start_date, board_run_time from commandboard where board_auto_start = 1 or board_contor > 2 order by board_start_date;";
		try(Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD)){
			Statement stmt = conn.createStatement();
			System.out.println("BoardRepository=>findBoards=> "+queryBoards);
			ResultSet rs = stmt.executeQuery(queryBoards);
			
			while(rs.next()) {
				ArduinoModul modul = new ArduinoModul(
						rs.getString("board_serial"),
						rs.getString("board_start"),
						rs.getString("board_start_date"),
						rs.getString("board_run_time"),
						false);
				list.add(modul);
			}
			
			stmt.close();
			rs.close();
		}catch (Exception e) {
			System.err.println("Error found in BoardRepository=>findBoards");
			e.printStackTrace();
		}
			
		return list;
	}
	
	public int oraInt(String ora) {
		int result = 0;
		
		if(ora != null && !(ora.isEmpty())) {
			String[] valori;
			valori = ora.split(":");
			for (String string : valori) {
				result += Integer.parseInt(string) * 60;
			}
		}
		
		return result;
	}
	
	public int durataInt(String durata) {
		int result = 0;
		if(durata != null && !(durata.isEmpty())) {
			String[] valori;
			valori = durata.split(":");
			for (String string : valori) {
				result += Integer.parseInt(string) * 60;
			}
		}
		return result;
	}
}
