package com.ejb.services;

import java.util.Date;
import java.util.List;

import com.jpa.entities.Semester;

/**
 * Business interface - It is used for defining the set of methods that are
 * available to client
 * 
 * @author sanja
 *
 */
public interface SemesterService {

	/**
	 * Return currently active semester
	 * 
	 * @return Semester
	 */
	Semester getActiveSemester();

	/**
	 * Listing all semesters
	 * 
	 * @return List<Semester>
	 */
	List<Semester> listSemesters();

	/**
	 * Adding new semester
	 * 
	 * @param schoolYear
	 * @param startDate
	 * @param endDate
	 * @param ordinalNumber
	 * @return Semester
	 */
	Semester addNewSemester(String schoolYear, Date startDate, Date endDate, Integer ordinalNumber);

	/**
	 * Activating existing semester. Only one semester can be active.
	 * 
	 * @param semesterId
	 * @return Semester
	 */
	Semester activateSemester(Long semesterId);

	/**
	 * Deactivating existing semester
	 * 
	 * @param semesterId
	 */
	void deactivateSemester(Long semesterId);
}
