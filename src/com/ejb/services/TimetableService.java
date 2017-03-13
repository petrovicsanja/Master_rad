package com.ejb.services;

import com.jpa.entities.Semester;

public interface TimetableService {

	void createTimetable(Semester activeSemester);
}
