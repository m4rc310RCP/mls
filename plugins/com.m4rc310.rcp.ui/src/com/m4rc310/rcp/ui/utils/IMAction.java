package com.m4rc310.rcp.ui.utils;

import org.eclipse.e4.core.contexts.IEclipseContext;
import com.m4rc310.rcp.ui.utils.streaming.MListener;
import com.m4rc310.rcp.ui.utils.streaming.MStreamLocal;

public interface IMAction {

	MStreamLocal getStream();
	
	IEclipseContext getContext();

	void removeListeners();
	
	void removeListeners(Object target);

	void addListener(String property, MListener listener);

	void addListener(Object target, String property, MListener listener);

	void putListener(String property, MListener listener);

	void fireInCache(String ref, Object... args);

	void fire(String ref, Object... args);
	
	void remove(IMAction action);
	
	void destroy();
	
	void setRaised(boolean raised);
	
	boolean wasRaised();
	
	<T extends IMAction> T getInstance(String ref, Class<T> type);

}