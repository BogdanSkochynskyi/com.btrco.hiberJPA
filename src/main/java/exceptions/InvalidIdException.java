package exceptions;

public class InvalidIdException extends Exception  {

	public InvalidIdException(String id) {
		super(id + " is invalid, please use positive numbers bigger than 0");
	}
}
