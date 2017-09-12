package com.ejb.services;

import java.util.List;

import com.jpa.entities.Group;
import com.jpa.entities.Room;
import com.jpa.entities.Semester;
import com.jpa.entities.Timetable;
import com.jpa.entities.User;

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

	/**
	 * Parsing data from timetable.ttbl file and saving into database
	 * 
	 * @param activeSemester
	 */
	void parseTimetableData(Semester activeSemester);

	/**
	 * Getting timetable data for specified semester and teacher
	 * 
	 * @param semester
	 * @param teacher
	 * @return List<Timetable>
	 */
	List<Timetable> getTimetableDataForTeacher(Semester semester, User teacher);

	/**
	 * Getting timetable data for specified semester and group
	 * 
	 * @param semester
	 * @param group
	 * @return List<Timetable>
	 */
	List<Timetable> getTimetableDataForGroup(Semester semester, Group group);

	/**
	 * Getting timetable data for specified semester and room
	 * 
	 * @param semester
	 * @param room
	 * @return List<Timetable>
	 */
	List<Timetable> getTimetableDataForRoom(Semester semester, Room room);
}
