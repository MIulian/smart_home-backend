package com.smart_home.s_home.service.impl;


import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.smart_home.s_home.data.BoardRepository;
import com.smart_home.s_home.model.ArduinoModul;

public class ModuleImpl {

	BoardRepository boardRepository = new BoardRepository();
	List<ArduinoModul> listModule = boardRepository.boardsToExecute();
	
	public ModuleImpl() {
		
	}
	
	public String execute() {
		String output = "";
		if(!(listModule.isEmpty())) {
			for (ArduinoModul arduinoModul : listModule) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().toString());
				String curentDateTime = now.format(format);
				String boardDateTime = arduinoModul.getData()+" "+arduinoModul.getOra();
				String boardDuration = addDurata(arduinoModul.getOra(),arduinoModul.getDurata(),arduinoModul.getData());
				if(!(arduinoModul.isExecutat()) && curentDateTime.equals(boardDateTime) ) {
					if(arduinoModul.getSerial().startsWith("r")) {
						output = "r1";
						System.out.println("Panoul cu serialul : "+arduinoModul.getSerial()+" a pornit");
					}else if (arduinoModul.getSerial().startsWith("y")) {
						output = "y1";
						System.out.println("Panoul cu serialul : "+arduinoModul.getSerial()+" a pornit");
					}else if (arduinoModul.getSerial().startsWith("b")) {
						output = "b1";
						System.out.println("Panoul cu serialul : "+arduinoModul.getSerial()+" a pornit");
					}else {
						System.out.println("Panoul necunoscut");
					}
					arduinoModul.setExecuted(true);
				}else if (arduinoModul.isExecutat() && curentDateTime.equals(boardDuration)) {
					if(arduinoModul.getSerial().startsWith("r")) {
						output = "r0";
						System.out.println("Panoul cu serialul : "+arduinoModul.getSerial()+" a fost oprit");
					}else if (arduinoModul.getSerial().startsWith("y")) {
						output = "y0";
						System.out.println("Panoul cu serialul : "+arduinoModul.getSerial()+" a fost oprit");
					}else if (arduinoModul.getSerial().startsWith("b")) {
						output = "b0";
						System.out.println("Panoul cu serialul : "+arduinoModul.getSerial()+" a fost oprit");
					}else {
						System.out.println("Panoul necunoscut");
					}
				}
			}
			
			
		}
		return output;
	}

	public void updateListe() {
		System.out.println("ModuleImpl=>updateListe: Actualizare lista panouri");
		this.listModule = boardRepository.boardsToExecute();
		
	}
	
	public String addDurata(String ora , String durata, String data) {
		String boardDuration = "";
			if(!(ora.equals("")) && ora != null && !(durata.equals("")) && durata != null && !(durata.equals("")) && durata != null) {
				LocalDate nextDay = LocalDate.parse(data);
				
				String oraL []= ora.split(":");
				String durataL []= durata.split(":");
				DecimalFormat form = new DecimalFormat("00");
				int concatenateH = Integer.valueOf(oraL[0])+Integer.valueOf(durataL[0]);
				int concatenateMin = Integer.valueOf(oraL[1])+Integer.valueOf(durataL[1]);
				int concatenateSec = Integer.valueOf(oraL[2])+Integer.valueOf(durataL[2]);
				if(concatenateMin > 59) {
					concatenateH++;
					concatenateMin = concatenateMin-60;
				}
				if(concatenateSec > 59) {
					concatenateMin++;
					concatenateSec = concatenateSec - 60;
				}
				if(concatenateH > 23) {
					LocalDate curentDate = LocalDate.parse(data);
					nextDay = curentDate.plusDays(1);
					concatenateH = concatenateH-24;
				}
				boardDuration = nextDay+" "+form.format(concatenateH)+":"+form.format(concatenateMin)+":"+form.format(concatenateSec);
			}

		return boardDuration;
		
	}
	
}
