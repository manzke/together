package com.saperion.sdb.client.exceptions;

public class AuthenticationException extends Exception{
	private static final long serialVersionUID = 5732982047622811557L;

	public AuthenticationException(String message) {
		super(message);
	}
}