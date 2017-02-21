package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RadniDaniProfesori")
public class TeacherNumDays {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idRadniDaniProfesori;

	@OneToOne
	@JoinColumn(name = "idProfesora")
	private User teacher;

	private Integer min;

	private Integer opt;

	private Integer max;

	public Long getIdRadniDaniProfesori() {
		return idRadniDaniProfesori;
	}

	public void setIdRadniDaniProfesori(Long idRadniDaniProfesori) {
		this.idRadniDaniProfesori = idRadniDaniProfesori;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getOpt() {
		return opt;
	}

	public void setOpt(Integer opt) {
		this.opt = opt;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}
}
