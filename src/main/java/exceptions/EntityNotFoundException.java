package exceptions;

public class EntityNotFoundException extends Exception {

	public EntityNotFoundException(String entity) {

		super(entity + " not found");

	}

	public EntityNotFoundException(Integer id) {

		super("Entity with " + id + " id not found");

	}
}
