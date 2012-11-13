package com.saperion.sdb.client.exceptions;

public class ConnectException extends Exception {
	private static final long serialVersionUID = 6676756809195803742L;

	public ConnectException() {
		super();
	}

	public ConnectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectException(String message) {
		super(message);
	}

	public ConnectException(Throwable cause) {
		super(cause);
	}

}
