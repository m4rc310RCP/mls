package com.m4rc310.rcp.constants.logger;

public interface MLog {
	void error(Throwable e, String message, Object... args);
	void error(String message, Object... args);
	
	void info(String message, Object... args);
	void warning(String message, Object... args);
	void debug(String message, Object... args);
}
