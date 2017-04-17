package com.ejb.services;

import java.util.Date;
import java.util.List;

import com.jpa.entities.Semester;

public interface SemesterService {

	Semester getActiveSemester();

	List<Semester> listSemesters();

	Semester addNewSemester(String schoolYear, Date startDate, Date endDate, Integer ordinalNumber);

	Semester activateSemester(Long semesterId);

	void deactivateSemester(Long semesterId);
}
