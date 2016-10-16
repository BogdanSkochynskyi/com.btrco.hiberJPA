package entity;

import javax.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "name", length = 45)
	private String name;

	@Column(name = "experience")
	private int experience;

	public Teacher(String name, int experience) {
		this.name = name;
		this.experience = experience;
	}

	public Teacher() {
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

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	@Override
	public String toString() {
		return "Teacher{" +
				"id=" + id +
				", name='" + name + '\'' +
				", experience=" + experience +
				'}';
	}
}
