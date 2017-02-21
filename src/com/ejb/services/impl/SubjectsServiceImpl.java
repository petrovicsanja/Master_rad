package com.ejb.services.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.SubjectsService;
import com.jpa.entities.Subject;

@Stateful
public class SubjectsServiceImpl implements SubjectsService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public List<Subject> listSubjects() {
		TypedQuery<Subject> subjectList = em.createQuery("SELECT s FROM Subject s ORDER BY s.nazivPredmeta",
				Subject.class);
		return subjectList.getResultList();
	}

	@Override
	public void deleteSubject(Long subjectId) {
		Subject subjectToDelete = em.find(Subject.class, subjectId);
		em.remove(subjectToDelete);
		System.out.println("Predmet je uspesno obrisan.");
	}

	@Override
	public void updateSubject(Subject subject) {
		Subject subjectToUpdate = em.find(Subject.class, subject.getIdPredmeta());
		subjectToUpdate.setNazivPredmeta(subject.getNazivPredmeta());
		subjectToUpdate.setOznakaPredmeta(subject.getOznakaPredmeta());
		System.out.println("Predmet je uspesno izmenjen.");
	}

	@Override
	public void addSubject(Subject subject) {
		em.persist(subject);
		System.out.println("Novi predmet je uspesno dodat.");
	}

	@Override
	public Subject findSubjectById(Long id) {
		return em.find(Subject.class, id);
	}
}
