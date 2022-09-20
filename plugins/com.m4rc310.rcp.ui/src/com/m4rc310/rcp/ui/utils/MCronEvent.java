package com.m4rc310.rcp.ui.utils;

import java.io.Serializable;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class MCronEvent implements Serializable {

	private transient ThreadPoolTaskScheduler taskScheduler;	
	
	private static final long serialVersionUID = 1L;

	public MCronEvent(ThreadPoolTaskScheduler taskScheduler) {
		this.taskScheduler = taskScheduler;
	}
	
	public void stop() {
		this.taskScheduler.shutdown();
	}

}
