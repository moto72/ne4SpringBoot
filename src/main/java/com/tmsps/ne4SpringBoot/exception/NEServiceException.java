package com.tmsps.ne4SpringBoot.exception;

/**
 *======================================================
 * @author zhangwei 396033084@qq.com 
 *------------------------------------------------------
 * NEServiceException
 *======================================================
 */
public class NEServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NEServiceException() {
	}

	public NEServiceException(String message) {
		super(message);
	}

	public NEServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NEServiceException(Throwable cause) {
		super(cause);
	}
}