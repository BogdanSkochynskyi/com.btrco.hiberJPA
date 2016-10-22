import ioc.DependencyInjector;
import ioc.ServiceLocator;
import service.IGroupService;

public class GroupServiceTest {

	public static void main(String[] args) {

		IGroupService groupService = (IGroupService) ServiceLocator.get("groupService");

		DependencyInjector.doInjection(groupService);
	}

}
