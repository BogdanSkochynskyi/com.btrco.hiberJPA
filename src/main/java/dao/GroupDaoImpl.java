package dao;

import dao.service.IGroupDao;
import entity.Group;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utils.HibernateUtils;
import utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class GroupDaoImpl implements IGroupDao {

	private static final Logger LOG = Logger.getLogger(GroupDaoImpl.class);
	private static EntityManager entityManager;
	private static final String GET_ALL_GROUPS = "SELECT g FROM Group g";

	static {
		entityManager = HibernateUtils.getEntityManager();
	}
	public GroupDaoImpl() {
		LOG.setLevel(Level.INFO);
		LOG.info("Group DAO created");
	}

	public Group create(Group group) {
		LOG.info("User try to create group");
		EntityManager manager = HibernateUtils.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try{
			transaction.begin();
			LOG.info("Transaction began");
			manager.persist(group);
			LOG.info("Group persisted");
			transaction.commit();
			LOG.info("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.info("Transaction rolled back");
			return null;
		} finally {
			manager.close();
			LOG.info("Manager closed");
		}
		return group;
	}

	public boolean delete(Group group) {
		LOG.info("User try to delete group");
		EntityManager manager = HibernateUtils.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		try{
			transaction.begin();
			LOG.info("Transaction began");
			group = manager.find(Group.class, group.getId());
			LOG.info("Add group into managed context");
			manager.remove(group);
			LOG.info("Group removed");
			transaction.commit();
			LOG.info("Transaction commited");
		} catch (Exception e) {
			LOG.error("Operation failed");
			transaction.rollback();
			LOG.info("Transaction rolled back");
			return false;
		} finally {
			manager.close();
			LOG.info("Manager closed");
		}
		return true;
	}

	public boolean update(Group group) {
		EntityManager manager = HibernateUtils.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		Group groupFromDB = manager.find(Group.class, group.getId()); //can we call findById method?
		Utils.copyGroup(group, groupFromDB);

		try{
			transaction.begin();
			manager.persist(groupFromDB);  //can we call create method?
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			manager.close();
		}
		return true;
	}

	public Group findById(Object id) {
		EntityManager manager = HibernateUtils.getEntityManager();
		try {
			Group groupFromDB = manager.find(Group.class, id);
			return groupFromDB;
		} finally {
			manager.close();
		}
	}

	@Override
	public List<Group> getAll() {
		return entityManager.createQuery(GET_ALL_GROUPS).setMaxResults(100).getResultList();
	}

	public static void setEntityManager(EntityManager entityManager) {
		GroupDaoImpl.entityManager = entityManager;
	}
}
