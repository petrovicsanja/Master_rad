package com.ejb.services;

import java.util.List;

import com.jpa.entities.Subject;

/**
 * Business interface - It is used for defining the set of methods that are
 * available to client
 * 
 * @author sanja
 *
 */
public interface SubjectsService {

	/**
	 * List all subjects ordered by name
	 * 
	 * @return List<Subject>
	 */
	List<Subject> listSubjects();

	/**
	 * Delete subject
	 * 
	 * @param Long
	 *            subjectId
	 */
	void deleteSubject(Long subjectId);

	/**
	 * Update subject
	 * 
	 * @param Subject
	 *            subject
	 */
	void updateSubject(Subject subject);

	/**
	 * Add new subject
	 * 
	 * @param Subject
	 *            subject
	 */
	void addSubject(Subject subject);

	/**
	 * Find subject by id
	 * 
	 * @param Long
	 *            subjectId
	 * @return Subject
	 */
	Subject findSubjectById(Long subjectId);
}
