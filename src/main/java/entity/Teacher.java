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

	@ManyToOne(cascade =  CascadeType.ALL)
	@JoinColumn(name = "subject_id", referencedColumnName = "id")
	private Subject subject;

	public Teacher(String name, int experience, Subject subject) {
		this.name = name;
		this.experience = experience;
		this.subject = subject;
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

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
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
