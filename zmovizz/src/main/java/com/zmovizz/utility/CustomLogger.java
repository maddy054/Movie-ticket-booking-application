//$Id$
package com.zmovizz.utility;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class CustomLogger {

	private static String  path = "/Users/madhavan-21454/ZIDE/zmovizz/logs/";
	
	public static void log(Level level, String record) {	
		try {
			Logger logger = Logger.getLogger(CustomLogger.class.getName());
			
			LocalDate date = LocalDate.now();
			String fileName = date+"zmovizz.log";


			FileHandler	handler = new FileHandler(path+fileName,true);
			handler.setFormatter(new SimpleFormatter());
			
			logger.addHandler(handler);
		
			logger.log(level,record);
			handler.close();
			
		} catch (SecurityException | IOException e) {
	
			e.printStackTrace();
		}
		
		
	}
	public static void log(Level level,String message,Exception e) {
		try {
			Logger logger = Logger.getLogger(CustomLogger.class.getName());
			
			LocalDate date = LocalDate.now();
			String fileName = date+"zmovizz.log";


			FileHandler	handler = new FileHandler(path+fileName,true);
			handler.setFormatter(new SimpleFormatter());
			
			logger.addHandler(handler);
		
			logger.log(level,message,e);
			handler.close();
			
		} catch (SecurityException | IOException ex) {
	
			ex.printStackTrace();
		}
		
	}
	
}
