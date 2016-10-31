package com.btrco.hiberJPA.service;

import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


//TODO: rewrite with correct mocks
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

	@After
	public void resetMocks() {
		reset(service);
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
