package dao;

public interface CrudDao<T> {

	T create(T entity);
	boolean delete(T entity);
	boolean update(T entity);
	T findById(Object id);

}
