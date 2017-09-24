package com.ejb.services;

import java.util.List;

import com.jpa.entities.Lesson;

/**
 * Business interface - It is used for defining the set of methods that are
 * available to client
 * 
 * @author sanja
 *
 */

public interface LessonsService {

	/**
	 * Add new or update existing lesson
	 * 
	 * @param lesson
	 * @param update
	 * @return
	 */
	Lesson addLesson(Lesson lesson, boolean update);

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
