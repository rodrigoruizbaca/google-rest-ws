package com.google.rws.exception;

public class ExpiredOrInvalidTokenException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExpiredOrInvalidTokenException(String msg) {
		super(msg);
	}
}
