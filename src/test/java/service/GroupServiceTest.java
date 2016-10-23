package service;

import entity.Group;
import entity.Subject;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import exceptions.RowsAmountException;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.implementation.GroupServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GroupServiceTest {

	@Mock
	private static IGroupService service;
	private Group testGroup;
	private Subject testSubject;

	@Before
	public void mockInitialization() throws RowsAmountException, EntityNotFoundException, EntityExistsException {
		this.testGroup = new Group("New group name");
		this.testSubject = new Subject("New Subject", "Description of new testSubject");
		MockitoAnnotations.initMocks(this);
		when(service.getListOfGroup()).thenReturn(new ArrayList<>());
		when(service.getListOfGroup(0, 20)).thenReturn(getListOfGroups(20));
		when(service.addGroup(this.testGroup)).thenReturn(this.testGroup);
		when(service.updateGroup(this.testGroup)).thenReturn(true);
		when(service.getGroupsThatStudySubject(this.testSubject)).thenReturn(new ArrayList<>());
	}

	@Test
	public void testGetListOfGroupsWithStandartRowLimit() throws RowsAmountException, EntityNotFoundException {
		assertNotNull(service.getListOfGroup());
	}

	@Test
	public void testGetListOfGroups() throws RowsAmountException, EntityNotFoundException {
		List<Group> groups = service.getListOfGroup(0, 20);
		assertTrue(groups.size() == 20);
	}

	@Test
	public void testAddGroup() throws EntityExistsException {
		assertEquals(service.addGroup(this.testGroup), this.testGroup);
	}

	@Test
	public void testUpdateGroup() throws EntityNotFoundException {
		this.testGroup.setName("Updated name");
		assertTrue(service.updateGroup(this.testGroup));
	}

	@Test
	public void testGetGroupsThatStudySubject() throws EntityNotFoundException {
		assertNotNull(service.getGroupsThatStudySubject(this.testSubject));
	}

	//TODO: rewrite by using lambda
	private List<Group> getListOfGroups(int capacity) {
		List<Group> groups = new ArrayList<>();
		for (int i = 0; i < capacity; i++) {
			groups.add(new Group("Group name " + i));
		}
		return groups;
	}
}
