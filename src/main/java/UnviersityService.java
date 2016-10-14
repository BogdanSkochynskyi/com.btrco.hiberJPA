import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class UnviersityService {

	private final EntityManager entityManager = Persistence.createEntityManagerFactory("university").createEntityManager();

}
