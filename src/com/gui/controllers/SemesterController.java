package com.gui.controllers;

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

	public String getActiveSemester() {
		Semester activeSemester = semesterService.getActiveSemester();
		if (activeSemester != null) {
			return activeSemester.toString();
		} else {
			return "Trenutno nijedan semestar nije aktivan";
		}
	}
}
