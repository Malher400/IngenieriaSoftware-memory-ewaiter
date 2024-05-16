package logic.exceptions;

public class InvalidCardException extends Exception{
	// Constructores:
		public InvalidCardException() { 
			super(); 
		}
				
		public InvalidCardException(String message) { // Este será el usado generalmente
			super(message);
		}
				
		public InvalidCardException(String message, Throwable cause) {
			super(message, cause);
		}
					
		public InvalidCardException(Throwable cause) { 
			super(cause); 
		}
					
		public InvalidCardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
		}
}
