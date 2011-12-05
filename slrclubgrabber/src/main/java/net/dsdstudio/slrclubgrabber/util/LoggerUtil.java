package net.dsdstudio.slrclubgrabber.util;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class LoggerUtil {
	public static final void setConsoleLogging() {
		String strpattern = "%5p %d{yyyyMMddHHmmss} (%t) %F[%M]:%L - %m%n";
		Properties p = new Properties();
		p.put("log4j.rootLogger", "fatal, stdout");
		p.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		p.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		p.put("log4j.appender.stdout.layout.ConversionPattern", strpattern);

		p.put("log4j.logger.org.apache.commons.httpclient.HttpClient", "FATAL, stdout");
		p.put("log4j.logger.org.springframework", "FATAL, stdout");
		PropertyConfigurator.configure(p);
	}
}
