package com.btrco.hiberJPA.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "name", length = 45)
	private String name;

	//mappedBy = field, described reference between, for configure join columns
	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY) //LAZY -только когда нужен лист студентов, EAGER - сразу вытягивает.
	//Надо юзать LAZY чтобы вытягивать инфу порциями
	private List<Student> students;

	@ManyToMany
	@JoinTable(name = "groups_subjects",
			joinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "subject_id", referencedColumnName = "id")})
	//лучше юзать отдельную энтити для groups_subjects таблицы
	private List<Subject> subjects;

	public Group() {}

	public Group(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Group{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Group group = (Group) o;

		if (id != group.id) return false;
		return name != null ? name.equals(group.name) : group.name == null;

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
