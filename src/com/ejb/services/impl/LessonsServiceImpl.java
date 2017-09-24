package com.ejb.services.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.LessonsService;
import com.jpa.entities.Lesson;

/**
 * Implementation of services to work with lesson data
 * 
 * @author sanja
 *
 */

@Stateless
public class LessonsServiceImpl implements LessonsService {

	Logger log = Logger.getLogger(LessonsServiceImpl.class.getName());

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public Lesson addLesson(Lesson lesson, boolean update) {
		if (update) {
			Lesson lessonToUpdate = em.find(Lesson.class, lesson.getId());
			lessonToUpdate.setTeachers(lesson.getTeachers());
			lessonToUpdate.setGroups(lesson.getGroups());
			lessonToUpdate.setSubject(lesson.getSubject());
			lessonToUpdate.setRooms(lesson.getRooms());
			lessonToUpdate.setTerms(lesson.getTerms());
			lessonToUpdate.setNote(lesson.getNote());
			lessonToUpdate.setApproved(lesson.getApproved());
			lessonToUpdate.setSemester(lesson.getSemester());
			log.info("Lesson is successfully updated in the database, id: " + lessonToUpdate.getId());

			return lessonToUpdate;
		}

		em.persist(lesson);
		log.info("New lesson is successfully added to the database, id: " + lesson.getId());

		return lesson;
	}

	@Override
	public List<Lesson> listLessons(Long subjectId, Long semesterId) {
		TypedQuery<Lesson> lessonsForSubject = em.createQuery(
				"SELECT l FROM Lesson l WHERE l.subject.id = :subjectId AND l.semester.id = :semesterId", Lesson.class);
		lessonsForSubject.setParameter("semesterId", semesterId);
		lessonsForSubject.setParameter("subjectId", subjectId);

		return lessonsForSubject.getResultList();
	}

	@Override
	public void deleteLesson(Long lessonId) {
		Lesson lessonToDelete = em.find(Lesson.class, lessonId);
		em.remove(lessonToDelete);
		System.out.println("Lesson is seccussfully deleted from the database, id: " + lessonId);
	}

	@Override
	public void approveLesson(Long lessonId) {
		Lesson lessonToApprove = em.find(Lesson.class, lessonId);
		lessonToApprove.setApproved(true);
		System.out.println("Lesson is seccussfully approved, id: " + lessonId);
	}
}
