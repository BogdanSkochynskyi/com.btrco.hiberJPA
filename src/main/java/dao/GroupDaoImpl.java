package dao;

import dao.impl.mysql.IGroupDao;
import entity.Group;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utils.HibernateUtils;
import utils.Utils;

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

	public Group create(Group group) {
		LOG.info("User try to create group");
		EntityTransaction transaction = entityManager.getTransaction();
		try{
			transaction.begin();
			LOG.info("Transaction began");
			entityManager.persist(group);
			LOG.info("Group persisted");
			transaction.commit();
			LOG.info("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.info("Transaction rolled back");
			return null;
		}
		return group;
	}

	public boolean delete(Group group) {
		LOG.info("User try to delete group");
		EntityTransaction transaction = entityManager.getTransaction();
		try{
			transaction.begin();
			LOG.info("Transaction began");
			group = entityManager.find(Group.class, group.getId());
			LOG.info("Add group into managed context");
			entityManager.remove(group);
			LOG.info("Group removed");
			transaction.commit();
			LOG.info("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.info("Transaction rolled back");
			return false;
		}
		return true;
	}

	public boolean update(Group group) {
		LOG.info("User try to update group");
		EntityTransaction transaction = entityManager.getTransaction();

		Group groupFromDB = entityManager.find(Group.class, group.getId());
		LOG.info("Group found correct");
		Utils.copyGroup(group, groupFromDB);
		LOG.info("Group data updated correct");
		try{
			transaction.begin();
			LOG.info("Transaction began");
			entityManager.persist(groupFromDB);
			LOG.info("Group added to managed context");
			transaction.commit();
			LOG.info("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.info("Transaction rolled back");
			return false;
		}
		return true;
	}

	public Group findById(Object id) {
		LOG.info("User try to find group by id");
		try {
			Group groupFromDB = entityManager.find(Group.class, id);
			if (groupFromDB != null)
			{
				LOG.info("Group " + groupFromDB + " found correct");
			} else {
				LOG.info("Group with id " + id + " not found");
			}
			return groupFromDB;
		} catch (Exception e) {
			LOG.error("Operation failed");
			return null;
		}
	}

	@Override
	public List<Group> getAll(int firstRow, int rowsAmount) {
		LOG.info("User try to find groups from " + firstRow + " row and with row amount " + rowsAmount);
		return entityManager.createQuery(GET_ALL_GROUPS)
				.setFirstResult(firstRow)
				.setMaxResults(rowsAmount)
				.getResultList();
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
