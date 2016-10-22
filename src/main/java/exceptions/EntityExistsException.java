package exceptions;

public class EntityExistsException extends Exception {

	public EntityExistsException(String entity) {

		super(entity + " already exists in DB");

	}
}
