package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Periods")
public class Period {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long periodId;

	private String day;

	private String dayMark;

	private Integer termsNumber;

	private Integer termLength;

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDayMark() {
		return dayMark;
	}

	public void setDayMark(String dayMark) {
		this.dayMark = dayMark;
	}

	public Integer getTermsNumber() {
		return termsNumber;
	}

	public void setTermsNumber(Integer termsNumber) {
		this.termsNumber = termsNumber;
	}

	public Integer getTermLength() {
		return termLength;
	}

	public void setTermLength(Integer termLength) {
		this.termLength = termLength;
	}
}
