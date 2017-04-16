package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TeacherAvailability")
public class TeacherAvailability extends Availability {

	@ManyToOne
	@JoinColumn(name = "teacherId")
	@NotNull
	private User teacher;

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}
}
