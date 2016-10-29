package com.btrco.hiberJPA.service;

import com.btrco.hiberJPA.entity.Group;
import com.btrco.hiberJPA.entity.Student;
import com.btrco.hiberJPA.entity.Subject;
import com.btrco.hiberJPA.exceptions.EntityExistsException;
import com.btrco.hiberJPA.exceptions.EntityNotFoundException;
import com.btrco.hiberJPA.exceptions.RowsAmountException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class StudentsServiceTest {

	@Mock
	private static IStudentService service;
	private Student testStudent;
	private Group testGroup;
	private Subject testSubject;

	@Before
	public void mockInitialization() throws RowsAmountException, EntityNotFoundException, EntityExistsException {
		this.testGroup = new Group("Test group");
		this.testStudent = new Student("Test Student", this.testGroup);
		this.testSubject = new Subject("Test subject", "Subject description");
		MockitoAnnotations.initMocks(this);
		when(service.getListOfStudents()).thenReturn(new ArrayList<>());
		when(service.getListOfStudents(0, 20)).thenReturn(getListOfStudents(20));
		when(service.addStudent(this.testStudent)).thenReturn(this.testStudent);
		when(service.updateStudent(this.testStudent)).thenReturn(true);
		when(service.getStudentsByGroup(this.testGroup)).thenReturn(new ArrayList<>());
		when(service.getAverageMarkBySubjectInGroup(this.testSubject, this.testGroup)).thenReturn(3.5f);
		when(service.getAverageMarkBySubject(this.testSubject)).thenReturn(4.5f);
	}

	@Test
	public void testGetListOfStudentsWithStandartRowLimit() throws RowsAmountException, EntityNotFoundException {
		Assert.assertNotNull(service.getListOfStudents());
	}

	@Test
	public void testGetListOfStudents() throws RowsAmountException, EntityNotFoundException {
		List<Student> students = service.getListOfStudents(0, 20);
		assertTrue(students.size() == 20);
	}

	@Test
	public void testAddStudent() throws EntityExistsException {
		assertEquals(service.addStudent(this.testStudent), this.testStudent);
	}

	@Test
	public void testUpdateStudent() throws EntityNotFoundException {
		this.testStudent.setName("Updated name");
		assertTrue(service.updateStudent(this.testStudent));
	}

	@Test
	public void testGetStudentsByGroup() throws EntityNotFoundException {
		assertNotNull(service.getStudentsByGroup(this.testGroup));
	}

	@Test
	public void testGetAverageMarkBySubjectInGroup() {
		assertTrue(service.getAverageMarkBySubjectInGroup(this.testSubject, this.testGroup) > 0);
	}

	@Test
	public void testGetAverageMarkBySubject() {
		assertTrue(service.getAverageMarkBySubject(this.testSubject) > 0);
	}

	//TODO: rewrite by using lambda
	private List<Student> getListOfStudents(int capacity) {
		List<Student> students = new ArrayList<>();
		for (int i = 0; i < capacity; i++) {
			students.add(new Student("Student name " + i, this.testGroup));
		}
		return students;
	}
}
