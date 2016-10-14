package entity;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "name", length = 45)
	private String name;

	@Column(name = "group_id")
	private int groupId;

	public Student(String name, int groupId) {
		this.name = name;
		this.groupId = groupId;
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

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", name='" + name + '\'' +
				", groupId=" + groupId +
				'}';
	}
}
