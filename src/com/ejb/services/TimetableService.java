package com.ejb.services;

import com.jpa.entities.Semester;

public interface TimetableService {

	String createTimetable(Semester activeSemester);
}
