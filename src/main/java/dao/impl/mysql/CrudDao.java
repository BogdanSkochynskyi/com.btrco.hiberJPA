package dao.impl.mysql;

import java.util.List;

public interface CrudDao<T> {

	T create(T entity);

	boolean delete(T entity);

	boolean update(T entity);

	T findById(Object id);

	List<T> getAll(int firstRow, int rowAmount);

}