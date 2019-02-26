package ru.otus.L041;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Logger {
	private static FileWriter fileWriter;
	private static File logFile;

	Logger() {
		File logPath = new File("./logs/");
		if (!(logPath.exists())) logPath.mkdir();
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd_HH-mm-ss");
		String logFileTime = dateFormat.format(date);
		logFile = new File("./logs/GCLog_" + logFileTime + ".log");
		try {
			fileWriter = new FileWriter(logFile, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fileWriter.write("Logger initialized at: " + logFileTime);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void printToLog(String message) {
		try {
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
			fileWriter = new FileWriter(logFile, true);
			Date date = new Date();
			fileWriter.write(timeFormat.format(date.getTime()) + "\n");
			fileWriter.write(message + "\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}