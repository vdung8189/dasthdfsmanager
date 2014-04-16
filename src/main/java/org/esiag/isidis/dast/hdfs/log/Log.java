package org.esiag.isidis.dast.hdfs.log;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/**
 * This class is in charge of writing events in "log/log.txt"
 * 
 * @author Viet Dung NGUYEN
 * 
 */
public class Log {

	final public static Logger logger = Logger.getLogger(Log.class);
	
	public static void init(String confFile, String logFile) {
		PropertyConfigurator.configure(confFile);
		FileAppender app = (FileAppender)Logger.getRootLogger().getAppender("file");
		app.setFile(logFile);
		app.activateOptions();
	}
}