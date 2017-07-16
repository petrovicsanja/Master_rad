package com.ejb.services;

import java.util.List;
import java.util.Set;

import com.jpa.entities.Group;
import com.jpa.entities.Lesson;
import com.jpa.entities.Room;
import com.jpa.entities.Semester;
import com.jpa.entities.Subject;
import com.jpa.entities.User;

/**
 * Business interface - It is used for defining the set of methods that are
 * available to client
 * 
 * @author sanja
 *
 */

public interface LessonsService {

	/**
	 * Add new lesson
	 * 
	 * @param teachers
	 * @param groups
	 * @param subject
	 * @param terms
	 * @param rooms
	 * @param note
	 * @param activeSemester
	 * @param isAdmin
	 * @return Lesson
	 */
	Lesson addLesson(Set<User> teachers, Set<Group> groups, Subject subject, String terms, Set<Room> rooms, String note,
			Semester activeSemester, boolean isAdmin);

	/**
	 * List all lessons
	 * 
	 * @param subjectId
	 * @param semesterId
	 * @return List<Lesson>
	 */
	List<Lesson> listLessons(Long subjectId, Long semesterId);

	/**
	 * Delete existing lesson by id
	 * 
	 * @param lessonId
	 */
	void deleteLesson(Long lessonId);

	/**
	 * Approving lesson by administrator
	 * 
	 * @param lessonId
	 */
	void approveLesson(Long lessonId);
}
