package com.ejb.services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.SubjectsService;
import com.jpa.entities.Subject;

/**
 * Implementation of services to work with subject data
 * 
 * @author sanja
 *
 */

@Stateless
public class SubjectsServiceImpl implements SubjectsService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public List<Subject> listSubjects() {
		TypedQuery<Subject> subjectList = em.createQuery("SELECT s FROM Subject s ORDER BY s.name", Subject.class);
		return subjectList.getResultList();
	}

	@Override
	public void deleteSubject(Long subjectId) {
		Subject subjectToDelete = em.find(Subject.class, subjectId);
		em.remove(subjectToDelete);
		System.out.println("Subject is successfully deleted from the database, id: " + subjectId);
	}

	@Override
	public void updateSubject(Subject subject) {
		Subject subjectToUpdate = em.find(Subject.class, subject.getId());
		subjectToUpdate.setName(subject.getName());
		subjectToUpdate.setMark(subject.getMark());
		subjectToUpdate.setObligatory(subject.getObligatory());
		System.out.println("Subject is successfully updated in the database, id: " + subject.getId());
	}

	@Override
	public void addSubject(Subject subject) {
		em.persist(subject);
		System.out.println("New subject is successfully added to the database, id: " + subject.getId());
	}

	@Override
	public Subject findSubjectById(Long id) {
		return em.find(Subject.class, id);
	}
}
