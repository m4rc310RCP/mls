package com.m4rc310.rcp.constants.security.exceptions;

public class MServerException extends Throwable {

	private static final long serialVersionUID = 1L;

	public MServerException(String message) {
		super(message);
	}

	public MServerException(String message, Throwable cause) {
		super(message, cause);
	}

}
