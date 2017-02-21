package com.ejb.services.impl;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.SemesterService;
import com.jpa.entities.Semester;

@Stateful
public class SemesterServiceImpl implements SemesterService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public Semester getActiveSemester() {
		TypedQuery<Semester> activeSemester = em.createQuery("SELECT s FROM Semester s WHERE s.active = 1",
				Semester.class);
		Semester result;
		try {
			result = activeSemester.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}

		return result;
	}
}
