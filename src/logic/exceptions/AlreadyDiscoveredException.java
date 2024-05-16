package logic.exceptions;

public class AlreadyDiscoveredException extends Exception {
	// Constructores:
	public AlreadyDiscoveredException() { 
		super(); 
	}
	
	public AlreadyDiscoveredException(String message) { // Este será el usado generalmente
		super(message);
	}
	
	public AlreadyDiscoveredException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AlreadyDiscoveredException(Throwable cause) { 
		super(cause); 
	}
	
	public AlreadyDiscoveredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
