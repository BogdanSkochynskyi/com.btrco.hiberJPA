package entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "name", length = 32)
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToMany
	@JoinTable
	private List<Group> groups;

	public Subject(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Subject() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Subject{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
