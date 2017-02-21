package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GroupAvailability")
public class GroupAvailability {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long groupAvailabilityId;

	@ManyToOne
	@JoinColumn(name = "groupId")
	private Group group;

	private String type;

	private String dayMark;

	private Integer termNumber;

	private Integer year;

	private Integer semester;

	public Long getGroupAvailabilityId() {
		return groupAvailabilityId;
	}

	public void setGroupAvailabilityId(Long groupAvailabilityId) {
		this.groupAvailabilityId = groupAvailabilityId;
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}
}
