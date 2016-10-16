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

	@ManyToOne(cascade = CascadeType.ALL)//если этот объект переводится в какое то состояние,
	// то и его поля если они объекты наши - тоже будут переведены в это состояние  //EMBEDED field(id -клас в котором может быть больше 1 поля)
	// uniqueConstraint={@UniqueConstraint(columnNames={"city", "street"})}
	@JoinColumn(name = "group_id", referencedColumnName = "id")
	private Group group;

	public Student(String name, Group group) {
		this.name = name;
		this.group = group;
	}

	public Student() {
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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group groupName) {
		this.group = groupName;
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", name='" + name + '\'' +
				", group=" + group +
				'}';
	}
}
