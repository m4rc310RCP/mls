package com.m4rc310.rcp.constants.dialogs;

public interface MDialogMessage {
	void error(String message, Object... args) throws UnsupportedOperationException;
	void error(Throwable e);
}
