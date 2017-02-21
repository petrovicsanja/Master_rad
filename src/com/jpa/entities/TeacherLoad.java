package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TeacherLoad")
public class TeacherLoad {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long teacherLoadId;

	@OneToOne
	@JoinColumn(name = "teacherId")
	private User teacher;

	private Integer max;

	private Integer min;

	public Long getTeacherLoadId() {
		return teacherLoadId;
	}

	public void setTeacherLoadId(Long teacherLoadId) {
		this.teacherLoadId = teacherLoadId;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}
}
