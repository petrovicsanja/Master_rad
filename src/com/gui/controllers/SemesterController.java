package com.gui.controllers;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ejb.services.SemesterService;
import com.jpa.entities.Semester;

@ManagedBean
@ViewScoped
public class SemesterController {

	@EJB
	private SemesterService semesterService;

	private List<Semester> semesterList = null;
	private Semester activeSemester;

	private String schoolYear;
	private Date startDate;
	private Date endDate;
	private Integer ordinalNumber;

	private Semester selectedSemester;

	public List<Semester> listSemesters() {
		semesterList = semesterService.listSemesters();
		return semesterList;
	}

	public void addNewSemester() {
		semesterService.addNewSemester(schoolYear, startDate, endDate, ordinalNumber);
	}

	public void activateSemester(Long semesterId) {
		activeSemester = semesterService.activateSemester(semesterId);
	}

	public void deactivateSemester(Long semesterId) {
		semesterService.deactivateSemester(semesterId);
		activeSemester = null;
	}

	public void updateSemester() {
		semesterService.updateSemester(selectedSemester);
	}

	/*
	 * Getters and setters
	 */

	public Semester getActiveSemester() {
		if (activeSemester == null) {
			activeSemester = semesterService.getActiveSemester();
		}
		return activeSemester;
	}

	public void setActiveSemester(Semester activeSemester) {
		this.activeSemester = activeSemester;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
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

	public Semester getSelectedSemester() {
		return selectedSemester;
	}

	public void setSelectedSemester(Semester selectedSemester) {
		this.selectedSemester = selectedSemester;
	}
}
