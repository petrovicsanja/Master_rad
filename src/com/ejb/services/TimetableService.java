package com.ejb.services;

import com.jpa.entities.Semester;

/**
 * Business interface - It is used for defining the set of methods that are
 * available to client
 * 
 * @author sanja
 *
 */
public interface TimetableService {

	/**
	 * Creating timetable using entered data for currently active semester
	 * 
	 * @param activeSemester
	 * @return String
	 */
	String createTimetable(Semester activeSemester);
}
