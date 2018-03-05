package util;

public class APINotRespondingException extends Exception {
	
	public APINotRespondingException() {
		super();
	}

	public APINotRespondingException(Throwable t) {
		super(t);
	}
	
	public APINotRespondingException(String error) {
		super(error);
	}

}
