package logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerTest {

	private static Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		
		logger.debug("This is a debug logger.");
		logger.error("This is an error logger.");
		logger.fatal("This is a fatal logger.");
		logger.info("This is an info logger.");
		logger.trace("This is a trace logger.");
		logger.warn("This is a warn logger.");
		
	}

}
