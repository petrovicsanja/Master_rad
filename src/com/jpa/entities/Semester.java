package com.jpa.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Semester")
public class Semester {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("d.MM.yyyy");

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date startDate;

	private Date endDate;

	@NotNull
	private Integer ordinalNumber;

	private boolean active;

	@NotNull
	private String schoolYear;

	@NotNull
	private Boolean deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getOrdinalNumber() {
		return ordinalNumber;
	}

	public void setOrdinalNumber(Integer ordinalNumber) {
		this.ordinalNumber = ordinalNumber;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return schoolYear + " - " + ordinalNumber + ". semestar - period: " + dateFormat.format(startDate) + " - "
				+ dateFormat.format(endDate);
	}
}
