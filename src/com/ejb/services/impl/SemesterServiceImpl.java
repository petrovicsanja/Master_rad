package com.ejb.services.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.SemesterService;
import com.jpa.entities.Semester;

/**
 * Implementation of services to work with semester data
 * 
 * @author sanja
 *
 */

@Stateless
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

	@Override
	public List<Semester> listSemesters() {
		TypedQuery<Semester> semesterList = em
				.createQuery("SELECT s FROM Semester s WHERE s.deleted = 0 ORDER BY s.schoolYear", Semester.class);
		return semesterList.getResultList();
	}

	@Override
	public Semester addNewSemester(String schoolYear, Date startDate, Date endDate, Integer ordinalNumber) {
		Semester semester = new Semester();
		semester.setSchoolYear(schoolYear);
		semester.setStartDate(startDate);
		semester.setEndDate(endDate);
		semester.setOrdinalNumber(ordinalNumber);
		semester.setActive(false);
		semester.setDeleted(false);
		em.persist(semester);

		System.out.println("Semester is successfully added to the database, id: " + semester.getId());
		return semester;
	}

	@Override
	public Semester activateSemester(Long semesterId) {
		TypedQuery<Semester> activeSemesterQuery = em.createQuery("SELECT s FROM Semester s WHERE s.active = :active",
				Semester.class);
		activeSemesterQuery.setParameter("active", true);

		Semester activeSemester;
		try {
			activeSemester = activeSemesterQuery.getSingleResult();
		} catch (NoResultException e) {
			activeSemester = null;
		}

		if (activeSemester == null) {
			Semester semester = em.find(Semester.class, semesterId);
			semester.setActive(true);

			System.out.println("Semester is successfully activated, id: " + semesterId);
			return semester;
		}

		return null;
	}

	@Override
	public void deactivateSemester(Long semesterId) {
		Semester semester = em.find(Semester.class, semesterId);
		semester.setActive(false);

		System.out.println("Semester is successfully deactivated, id: " + semesterId);
	}

	@Override
	public void updateSemester(Semester semester) {
		Semester semesterToUpdate = em.find(Semester.class, semester.getId());

		semesterToUpdate.setSchoolYear(semester.getSchoolYear());
		semesterToUpdate.setStartDate(semester.getStartDate());
		semesterToUpdate.setEndDate(semester.getEndDate());
		semesterToUpdate.setOrdinalNumber(semester.getOrdinalNumber());

		System.out.println("Semester is successfully updated in the database, id: " + semester.getId());
	}

	@Override
	public void deleteSemester(Long semesterId) {
		Semester semesterToDelete = em.find(Semester.class, semesterId);
		semesterToDelete.setDeleted(true);

		System.out.println("Semester is successfully deleted from the database, id: " + semesterId);
	}
}
