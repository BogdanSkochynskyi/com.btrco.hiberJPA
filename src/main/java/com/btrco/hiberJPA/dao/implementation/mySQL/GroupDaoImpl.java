package com.btrco.hiberJPA.dao.implementation.mySQL;

import com.btrco.hiberJPA.dao.IGroupDao;
import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.InvalidIdException;
import org.apache.log4j.Logger;
import com.btrco.hiberJPA.utils.HibernateUtils;
import com.btrco.hiberJPA.utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
//TODO: rewrite logging and duplicated logic around persist method using AOP(aspectJ)
public class GroupDaoImpl implements IGroupDao {

	private static final Logger LOG = Logger.getLogger(GroupDaoImpl.class.getName());
	private static final String GET_ALL_GROUPS = "SELECT g FROM Group g";
	private EntityManager entityManager;

	public GroupDaoImpl() {
		this.entityManager = HibernateUtils.getEntityManager();
	}

	public GroupDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Group create(Group group) throws EntityExistsException {
		LOG.trace("User try to create group");
		EntityTransaction transaction = entityManager.getTransaction();

		if (entityManager.find(Group.class, group.getId()) != null) {
			throw new EntityExistsException(group.toString());
		}

		try{
			transaction.begin();
			LOG.trace("Transaction began");
			entityManager.persist(group);
			LOG.trace("Group persisted");
			transaction.commit();
			LOG.trace("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.trace("Transaction rolled back");
			return null;
		}
		return group;
	}

	public boolean delete(Group group) throws EntityNotFoundException {
		LOG.trace("User try to delete group");
		EntityTransaction transaction = entityManager.getTransaction();

		if (entityManager.find(Group.class, group.getId()) == null) {
			throw new EntityNotFoundException(group.toString());
		}

		try{
			transaction.begin();
			LOG.trace("Transaction began");
			group = entityManager.find(Group.class, group.getId());
			LOG.trace("Add group into managed context");
			entityManager.remove(group);
			LOG.trace("Group removed");
			transaction.commit();
			LOG.trace("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.trace("Transaction rolled back");
			return false;
		}
		return true;
	}

	public boolean update(Group group) throws EntityNotFoundException {
		LOG.trace("User try to update group");
		EntityTransaction transaction = entityManager.getTransaction();

		Group groupFromDB = entityManager.find(Group.class, group.getId());

		if (groupFromDB == null) {
			throw new EntityNotFoundException(group.toString());
		}

		LOG.trace("Group found correct");
		Utils.copyGroup(group, groupFromDB);
		LOG.trace("Group data updated correct");
		try{
			transaction.begin();
			LOG.trace("Transaction began");
			entityManager.persist(groupFromDB);
			LOG.trace("Group added to managed context");
			transaction.commit();
			LOG.trace("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.trace("Transaction rolled back");
			return false;
		}
		return true;
	}

	public Group findById(Object id) throws InvalidIdException, EntityNotFoundException {
		LOG.trace("User try to find group by id");

		if (!(id instanceof Integer) || ((Integer)id < 0)) {
			throw new InvalidIdException(id.toString());
		}

		Group groupFromDB = entityManager.find(Group.class, id);

		if (groupFromDB == null) {
			throw new EntityNotFoundException((Integer)id);
		}

		return groupFromDB;

	}

	//TODO: add exception(how to know amount of rows without of select all of them)
	@Override
	public List<Group> getAll(int firstRow, int rowsAmount) {
		LOG.trace("User try to find groups from " + firstRow + " row and with row amount " + rowsAmount);
		return entityManager.createQuery(GET_ALL_GROUPS)
				.setFirstResult(firstRow)
				.setMaxResults(rowsAmount)
				.getResultList();
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Group> getGroupsThatStudySubject(Subject subject) {
		return null;
	}
}
