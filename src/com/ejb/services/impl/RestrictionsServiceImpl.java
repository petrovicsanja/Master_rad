package com.ejb.services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.RestrictionsService;
import com.jpa.entities.GroupIdles;
import com.jpa.entities.GroupLoad;
import com.jpa.entities.GroupNumDays;
import com.jpa.entities.Semester;
import com.jpa.entities.TeacherIdles;
import com.jpa.entities.TeacherLoad;
import com.jpa.entities.TeacherNumDays;

@Stateless
public class RestrictionsServiceImpl implements RestrictionsService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public List<GroupNumDays> listNumDaysGroupRestrictions(Semester activeSemester) {
		TypedQuery<GroupNumDays> numDaysGroupList = em.createQuery(
				"SELECT r FROM GroupNumDays r WHERE r.semester.id = :semesterId ORDER BY r.group.name",
				GroupNumDays.class);
		numDaysGroupList.setParameter("semesterId", activeSemester == null ? null : activeSemester.getId());
		return numDaysGroupList.getResultList();
	}

	@Override
	public List<TeacherNumDays> listNumDaysTeacherRestrictions(Long teacherId, Semester activeSemester) {
		TypedQuery<TeacherNumDays> numDaysTeacherList;

		if (teacherId != null) {
			numDaysTeacherList = em.createQuery(
					"SELECT r FROM TeacherNumDays r WHERE r.semester.id = :semesterId AND r.teacher.id = :teacherId ORDER BY r.teacher.firstName",
					TeacherNumDays.class);
			numDaysTeacherList.setParameter("teacherId", teacherId);
		} else {
			numDaysTeacherList = em.createQuery(
					"SELECT r FROM TeacherNumDays r WHERE r.semester.id = :semesterId ORDER BY r.teacher.firstName",
					TeacherNumDays.class);
		}
		numDaysTeacherList.setParameter("semesterId", activeSemester == null ? null : activeSemester.getId());
		return numDaysTeacherList.getResultList();
	}

	@Override
	public List<GroupIdles> listIdlesGroupRestrictions(Semester activeSemester) {
		TypedQuery<GroupIdles> idlesGroupList = em.createQuery(
				"SELECT r FROM GroupIdles r WHERE r.semester.id = :semesterId ORDER BY r.group.name", GroupIdles.class);
		idlesGroupList.setParameter("semesterId", activeSemester == null ? null : activeSemester.getId());
		return idlesGroupList.getResultList();
	}

	@Override
	public List<TeacherIdles> listIdlesTeacherRestrictions(Long teacherId, Semester activeSemester) {
		TypedQuery<TeacherIdles> idlesTeacherList;

		if (teacherId != null) {
			idlesTeacherList = em.createQuery(
					"SELECT r FROM TeacherIdles r WHERE r.semester.id = :semesterId AND r.teacher.id = :teacherId ORDER BY r.teacher.firstName",
					TeacherIdles.class);
			idlesTeacherList.setParameter("teacherId", teacherId);
		} else {
			idlesTeacherList = em.createQuery(
					"SELECT r FROM TeacherIdles r WHERE r.semester.id = :semesterId ORDER BY r.teacher.firstName",
					TeacherIdles.class);
		}
		idlesTeacherList.setParameter("semesterId", activeSemester == null ? null : activeSemester.getId());
		return idlesTeacherList.getResultList();
	}

	@Override
	public List<GroupLoad> listLoadGroupRestrictions(Semester activeSemester) {
		TypedQuery<GroupLoad> loadGroupList = em.createQuery(
				"SELECT r FROM GroupLoad r WHERE r.semester.id = :semesterId ORDER BY r.group.name", GroupLoad.class);
		loadGroupList.setParameter("semesterId", activeSemester == null ? null : activeSemester.getId());
		return loadGroupList.getResultList();
	}

	@Override
	public List<TeacherLoad> listLoadTeacherRestrictions(Long teacherId, Semester activeSemester) {
		TypedQuery<TeacherLoad> loadTeacherList;

		if (teacherId != null) {
			loadTeacherList = em.createQuery(
					"SELECT r FROM TeacherLoad r WHERE r.semester.id = :semesterId AND r.teacher.id = :teacherId ORDER BY r.teacher.firstName",
					TeacherLoad.class);
			loadTeacherList.setParameter("teacherId", teacherId);
		} else {
			loadTeacherList = em.createQuery(
					"SELECT r FROM TeacherLoad r WHERE r.semester.id = :semesterId ORDER BY r.teacher.firstName",
					TeacherLoad.class);
		}
		loadTeacherList.setParameter("semesterId", activeSemester == null ? null : activeSemester.getId());
		return loadTeacherList.getResultList();
	}

	@Override
	public void addNewNumDaysGroupRestriction(GroupNumDays groupNumDays, Semester activeSemester) {
		groupNumDays.setSemester(activeSemester);
		em.persist(groupNumDays);
		System.out.println("New num days group restriction is successfuly added to the database.");

	}

	@Override
	public void addNewNumDaysTeacherRestriction(TeacherNumDays teacherNumDays, Semester activeSemester) {
		teacherNumDays.setSemester(activeSemester);
		em.persist(teacherNumDays);
		System.out.println("New num days teacher restriction is successfuly added to the database.");
	}

	@Override
	public void addIdlesGroupRestriction(GroupIdles groupIdles, Semester activeSemester) {
		groupIdles.setSemester(activeSemester);
		em.persist(groupIdles);
		System.out.println("New idles group restriction is successfuly added to the database.");
	}

	@Override
	public void addIdlesTeacherRestriction(TeacherIdles teacherIdles, Semester activeSemester) {
		teacherIdles.setSemester(activeSemester);
		em.persist(teacherIdles);
		System.out.println("New idles teacher restriction is successfuly added to the database.");
	}

	@Override
	public void addLoadGroupRestriction(GroupLoad groupLoad, Semester activeSemester) {
		groupLoad.setSemester(activeSemester);
		em.persist(groupLoad);
		System.out.println("New load group restriction is successfuly added to the database");
	}

	@Override
	public void addLoadTeacherRestriction(TeacherLoad teacherLoad, Semester activeSemester) {
		teacherLoad.setSemester(activeSemester);
		em.persist(teacherLoad);
		System.out.println("New load teacher restriction is successfuly added to the database");
	}
}
