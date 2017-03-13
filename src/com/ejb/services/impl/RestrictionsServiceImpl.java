package com.ejb.services.impl;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejb.services.RestrictionsService;
import com.jpa.entities.GroupIdles;
import com.jpa.entities.GroupLoad;
import com.jpa.entities.GroupNumDays;
import com.jpa.entities.Semester;
import com.jpa.entities.TeacherIdles;
import com.jpa.entities.TeacherLoad;
import com.jpa.entities.TeacherNumDays;

@Stateful
public class RestrictionsServiceImpl implements RestrictionsService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public void addNewNumDaysGroupRestriction(GroupNumDays groupNumDays, Semester activeSemester) {
		groupNumDays.setSemester(activeSemester.getOrdinalNumber());
		groupNumDays.setYear(activeSemester.getSchoolYear());
		em.persist(groupNumDays);
		System.out.println("New num days group restriction is successfuly added to the database.");

	}

	@Override
	public void addNewNumDaysTeacherRestriction(TeacherNumDays teacherNumDays, Semester activeSemester) {
		teacherNumDays.setSemester(activeSemester.getOrdinalNumber());
		teacherNumDays.setYear(activeSemester.getSchoolYear());
		em.persist(teacherNumDays);
		System.out.println("New num days teacher restriction is successfuly added to the database.");
	}

	@Override
	public void addIdlesGroupRestriction(GroupIdles groupIdles, Semester activeSemester) {
		groupIdles.setSemester(activeSemester.getOrdinalNumber());
		groupIdles.setYear(activeSemester.getSchoolYear());
		em.persist(groupIdles);
		System.out.println("New idles group restriction is successfuly added to the database.");
	}

	@Override
	public void addIdlesTeacherRestriction(TeacherIdles teacherIdles, Semester activeSemester) {
		teacherIdles.setSemester(activeSemester.getOrdinalNumber());
		teacherIdles.setYear(activeSemester.getSchoolYear());
		em.persist(teacherIdles);
		System.out.println("New idles teacher restriction is successfuly added to the database.");
	}

	@Override
	public void addLoadGroupRestriction(GroupLoad groupLoad, Semester activeSemester) {
		groupLoad.setSemester(activeSemester.getOrdinalNumber());
		groupLoad.setYear(activeSemester.getSchoolYear());
		em.persist(groupLoad);
		System.out.println("New load group restriction is successfuly added to the database");
	}

	@Override
	public void addLoadTeacherRestriction(TeacherLoad teacherLoad, Semester activeSemester) {
		teacherLoad.setSemester(activeSemester.getOrdinalNumber());
		teacherLoad.setYear(activeSemester.getSchoolYear());
		em.persist(teacherLoad);
		System.out.println("New load teacher restriction is successfuly added to the database");
	}
}
