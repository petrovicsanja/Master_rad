package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "GroupAvailability")
public class GroupAvailability {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "groupId")
	@NotNull
	private Group group;

	@NotNull
	private String type;

	@NotNull
	private String dayMark;

	@NotNull
	private Integer termNumber;

	@NotNull
	private String year;

	@NotNull
	private Integer semester;

	public Long getId() {
		return id;
	}

	public void setId(Long groupAvailabilityId) {
		this.id = groupAvailabilityId;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDayMark() {
		return dayMark;
	}

	public void setDayMark(String dayMark) {
		this.dayMark = dayMark;
	}

	public Integer getTermNumber() {
		return termNumber;
	}

	public void setTermNumber(Integer termNumber) {
		this.termNumber = termNumber;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}
}
