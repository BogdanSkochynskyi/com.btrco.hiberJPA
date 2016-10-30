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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;
//TODO: rewrite logging and duplicated logic around persist method using AOP(aspectJ)

@Component("groupDao")
public class GroupDaoImpl implements IGroupDao {

	private static final Logger LOG = Logger.getLogger(GroupDaoImpl.class.getName());
	private static final String GET_ALL_GROUPS = "SELECT g FROM Group g";

	@PersistenceContext
	private EntityManager entityManager;

	public GroupDaoImpl() {
		LOG.error("Group DAO created");

	}

	@Override
	@Transactional
	public Group create(Group group) throws EntityExistsException {
		if (entityManager.find(Group.class, group.getId()) != null) {
			throw new EntityExistsException(group.toString());
		}

		try{
			entityManager.persist(group);
		} catch (Exception e) {
			return null;
		}

		return group;
	}

	@Override
	@Transactional
	public boolean delete(Group group) throws EntityNotFoundException {
		if (entityManager.find(Group.class, group.getId()) == null) {
			throw new EntityNotFoundException(group.toString());
		}

		try{
			group = entityManager.find(Group.class, group.getId());
			entityManager.remove(group);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	@Transactional
	public boolean update(Group group) throws EntityNotFoundException {
		Group groupFromDB = entityManager.find(Group.class, group.getId());

		if (groupFromDB == null) {
			throw new EntityNotFoundException(group.toString());
		}

		Utils.copyGroup(group, groupFromDB);

		try{
			entityManager.persist(groupFromDB);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	@Transactional
	public Group findById(Object id) throws InvalidIdException, EntityNotFoundException {
		if (!(id instanceof Integer) || ((Integer)id < 0)) {
			throw new InvalidIdException(id.toString());
		}

		Group groupFromDB = entityManager.find(Group.class, id);

		if (groupFromDB == null) {
			throw new EntityNotFoundException((Integer)id);
		}

		return groupFromDB;

	}

	@Override
	@Transactional
	public List<Group> getAll(int firstRow, int rowsAmount) {
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
