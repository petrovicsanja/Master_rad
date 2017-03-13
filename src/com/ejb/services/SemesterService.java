package com.ejb.services;

import java.util.Date;
import java.util.List;

import com.jpa.entities.Semester;

public interface SemesterService {

	Semester getActiveSemester();

	List<Semester> listSemesters();

	void addNewSemester(String schoolYear, Date startDate, Date endDate, Integer ordinalNumber);

	void activateSemester(Long semesterId);

	void deactivateSemester(Long semesterId);
}
