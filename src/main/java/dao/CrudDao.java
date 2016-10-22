package dao;

import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidIdException;
import exceptions.RowsAmountException;

import java.util.List;

public interface CrudDao<T> {

	T create(T entity) throws EntityExistsException;

	boolean delete(T entity) throws EntityNotFoundException;

	boolean update(T entity) throws EntityNotFoundException;

	T findById(Object id) throws EntityNotFoundException, InvalidIdException;

	List<T> getAll(int firstRow, int rowAmount) throws EntityNotFoundException, RowsAmountException;
	;

}
