package com.ejb.services.impl;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejb.services.RestrictionsService;
import com.jpa.entities.GroupIdles;
import com.jpa.entities.GroupLoad;
import com.jpa.entities.GroupNumDays;
import com.jpa.entities.TeacherIdles;
import com.jpa.entities.TeacherLoad;
import com.jpa.entities.TeacherNumDays;

@Stateful
public class RestrictionsServiceImpl implements RestrictionsService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public void addNewNumDaysGroupRestriction(GroupNumDays groupNumDays) {
		em.persist(groupNumDays);
		System.out
				.println("New num days group restriction is successfuly added to the database.");

	}

	@Override
	public void addNewNumDaysTeacherRestriction(TeacherNumDays teacherNumDays) {
		em.persist(teacherNumDays);
		System.out
				.println("New num days teacher restriction is successfuly added to the database.");
	}

	@Override
	public void addIdlesGroupRestriction(GroupIdles groupIdles) {
		em.persist(groupIdles);
		System.out
				.println("New idles group restriction is successfuly added to the database.");
	}

	@Override
	public void addIdlesTeacherRestriction(TeacherIdles teacherIdles) {
		em.persist(teacherIdles);
		System.out
				.println("New idles teacher restriction is successfuly added to the database.");
	}

	@Override
	public void addLoadGroupRestriction(GroupLoad groupLoad) {
		em.persist(groupLoad);
		System.out
				.println("New load group restriction is successfuly added to the database");
	}

	@Override
	public void addLoadTeacherRestriction(TeacherLoad teacherLoad) {
		em.persist(teacherLoad);
		System.out
				.println("New load teacher restriction is successfuly added to the database");
	}
}
