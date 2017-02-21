package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TeacherIdles")
public class TeacherIdles {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long teacherIdlesId;

	@OneToOne
	@JoinColumn(name = "teacherId")
	private User teacher;

	private Integer max;

	private Boolean multiple;

	private Integer days;

	public Long getTeacherIdlesId() {
		return teacherIdlesId;
	}

	public void setTeacherIdlesId(Long teacherIdlesId) {
		this.teacherIdlesId = teacherIdlesId;
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

	public Boolean getMultiple() {
		return multiple;
	}

	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
}
