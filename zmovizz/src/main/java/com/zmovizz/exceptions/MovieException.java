//$Id$
package com.zmovizz.exceptions;

public class MovieException extends Exception{
	private String message;
	

	private static final long serialVersionUID = 1L;
	public MovieException(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return this.message;
	}

}
