package com.ejb.services.impl;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.LessonsService;
import com.jpa.entities.Group;
import com.jpa.entities.Lesson;
import com.jpa.entities.Room;
import com.jpa.entities.Semester;
import com.jpa.entities.Subject;
import com.jpa.entities.User;

@Stateless
public class LessonsServiceImpl implements LessonsService {

	Logger log = Logger.getLogger(LessonsServiceImpl.class.getName());

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public void addLesson(Set<User> teachers, Set<Group> groups, Subject subject, String terms, Set<Room> rooms,
			String note, Semester activeSemester) {
		Lesson newLesson = new Lesson();
		newLesson.setTeachers(teachers);
		newLesson.setGroups(groups);
		newLesson.setSubject(subject);
		newLesson.setTerms(terms);
		newLesson.setRooms(rooms);
		newLesson.setNote(note);
		newLesson.setSemester(activeSemester.getOrdinalNumber());
		newLesson.setYear(activeSemester.getSchoolYear());
		em.persist(newLesson);

		log.info("New lesson is successfully added to the database, id: " + newLesson.getId());
	}

	@Override
	public List<Lesson> listLessons(Long subjectId, Long semesterId) {
		TypedQuery<Lesson> lessonsForSubject = em.createQuery(
				"SELECT l FROM Lesson l WHERE l.subject.id = :subjectId AND l.semester.id = :semesterId", Lesson.class);
		lessonsForSubject.setParameter("semesterId", semesterId);
		lessonsForSubject.setParameter("subjectId", subjectId);

		return lessonsForSubject.getResultList();
	}
}
