package com.gui.controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ejb.services.TimetableService;
import com.jpa.entities.Semester;

@ManagedBean
@ViewScoped
public class TimetableController {

	@EJB
	private TimetableService timetableService;

	@ManagedProperty(value = "#{semesterController}")
	private SemesterController semesterController;

	public void createTimetable() {
		Semester activeSemester = semesterController.getActiveSemester();
		if (activeSemester != null) {
			timetableService.createTimetable(activeSemester);
		}
	}

	public void setSemesterController(SemesterController semesterController) {
		this.semesterController = semesterController;
	}
}
