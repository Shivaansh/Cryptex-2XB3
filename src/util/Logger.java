package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
	private static DateTimeFormatter formatter;
	
	static {
		formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	}
	
	public static void info(String s) {
		LocalDateTime time = LocalDateTime.now();  
		System.out.println("[" + formatter.format(time) + "] " + s);
	}
	public static void error(String s) {
		LocalDateTime time = LocalDateTime.now();  
		System.out.println("[" + formatter.format(time) + "] " + "ERROR: " + s);
	}
	public static void warning(String s) {
		LocalDateTime time = LocalDateTime.now();  
		System.out.println("[" + formatter.format(time) + "] " + "Warning: " + s);
	}

}
