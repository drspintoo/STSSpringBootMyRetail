package com.devon.myretailtarget.exceptions;

public class UpdateValidationException extends Exception{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UpdateValidationException() {
    }

    public UpdateValidationException(String message) {
        super(message);
    }
}
