package com.m4rc310.rcp.ui.utils.loggers;

import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.events.IEventBroker;

import com.m4rc310.rcp.ui.constants.MConsts;

@Creatable @Singleton
public class Log implements MConsts{
	@Inject 
	IEventBroker eventBroker;
	
	public void print(String pattern, Object... args) {
		String text = MessageFormat.format(pattern, args);
		System.out.println(text);
		eventBroker.send(LOG$print_ln, text);
	}
}
