package com.btrco.hiberJPA.dao;

import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidIdException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;

import java.util.List;

public interface CrudDao<T> {

	T create(T entity) throws EntityExistsException;

	boolean delete(T entity) throws EntityNotFoundException;

	boolean update(T entity) throws EntityNotFoundException;

	T findById(Object id) throws EntityNotFoundException, InvalidIdException;

	List<T> getAll(int firstRow, int rowAmount) throws EntityNotFoundException, RowsAmountException;
	;

}
