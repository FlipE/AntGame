package exceptions;

public class InvalidWorldException extends Exception {

	private static final long serialVersionUID = -282179514741313346L;

	public InvalidWorldException(String message) {
		super(message);
	}	
}