package com.m4rc310.rcp.constants.security.exceptions;

public class MAccessTokenException extends Throwable {
	private static final long serialVersionUID = 1L;

	public MAccessTokenException(String message) {
		super(message);
	}
	public MAccessTokenException(String message, Throwable cause) {
		super(message, cause);
	}
}
