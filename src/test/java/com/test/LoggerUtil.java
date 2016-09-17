package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintStream;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class LoggerUtil
	{
		private static ObjectMapper	objectMapper		= new ObjectMapper();
		private static ObjectWriter	objectWriter		= objectMapper.writerWithDefaultPrettyPrinter();
		private static File			errorFile			= new File("output" + File.separator + "error.log");
		private static boolean		isPreviousRequired	= false;
		
		public static void log(String testCaseName, Object object)
			{
				try
					{
						if (object == null || testCaseName == null || testCaseName.trim().length() == 0)
							{
								return;
							}
						String fileName = "output" + File.separator + testCaseName + ".json";
						if (isPreviousRequired)
							{
								File temp = new File(fileName);
								if (temp.exists())
									{
										FileUtils.moveFile(temp, new File("output" + File.separator + "old" + File.separator + testCaseName + System.nanoTime() + ".json"));
									}
							}
						File file = new File(fileName);
						FileWriter fileWriter = new FileWriter(file);
						fileWriter.write(objectWriter.writeValueAsString(object));
						fileWriter.flush();
						fileWriter.close();
					}
				catch (Exception exception)
					{
						try
							{
								exception.printStackTrace(new PrintStream(errorFile));
							}
						catch (FileNotFoundException fileNotFoundException)
							{
								fileNotFoundException.printStackTrace();
							}
					}
			}
	}
