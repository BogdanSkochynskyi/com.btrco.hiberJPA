package entity;

import javax.persistence.*;

@Entity
@Table(name = "groups")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "name", length = 45)
	private String name;

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
