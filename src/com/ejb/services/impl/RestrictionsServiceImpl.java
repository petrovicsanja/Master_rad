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

/**
 * Implementation of services to work with num days, idles and loads restriction
 * data for teachers and groups
 * 
 * @author sanja
 *
 */

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
	public GroupNumDays addNewNumDaysGroupRestriction(GroupNumDays groupNumDays, Semester activeSemester) {
		TypedQuery<GroupNumDays> existingRestriction = em.createQuery(
				"SELECT r FROM GroupNumDays r WHERE r.semester.id = :semesterId AND r.group.id = :groupId",
				GroupNumDays.class);
		existingRestriction.setParameter("semesterId", activeSemester.getId());
		existingRestriction.setParameter("groupId", groupNumDays.getGroup().getId());

		if (existingRestriction.getResultList().size() > 0) {
			return null;
		}

		groupNumDays.setSemester(activeSemester);
		em.persist(groupNumDays);
		System.out.println("New num days group restriction is successfuly added to the database.");
		return groupNumDays;
	}

	@Override
	public TeacherNumDays addNewNumDaysTeacherRestriction(TeacherNumDays teacherNumDays, Semester activeSemester) {
		TypedQuery<TeacherNumDays> existingRestriction = em.createQuery(
				"SELECT r FROM TeacherNumDays r WHERE r.semester.id = :semesterId AND r.teacher.id = :teacherId",
				TeacherNumDays.class);
		existingRestriction.setParameter("semesterId", activeSemester.getId());
		existingRestriction.setParameter("teacherId", teacherNumDays.getTeacher().getId());

		if (existingRestriction.getResultList().size() > 0) {
			return null;
		}

		teacherNumDays.setSemester(activeSemester);
		em.persist(teacherNumDays);
		System.out.println("New num days teacher restriction is successfuly added to the database.");
		return teacherNumDays;
	}

	@Override
	public GroupIdles addIdlesGroupRestriction(GroupIdles groupIdles, Semester activeSemester) {
		TypedQuery<GroupIdles> existingRestriction = em.createQuery(
				"SELECT r FROM GroupIdles r WHERE r.semester.id = :semesterId AND r.group.id = :groupId",
				GroupIdles.class);
		existingRestriction.setParameter("semesterId", activeSemester.getId());
		existingRestriction.setParameter("groupId", groupIdles.getGroup().getId());

		if (existingRestriction.getResultList().size() > 0) {
			return null;
		}

		groupIdles.setSemester(activeSemester);
		em.persist(groupIdles);
		System.out.println("New idles group restriction is successfuly added to the database.");
		return groupIdles;
	}

	@Override
	public TeacherIdles addIdlesTeacherRestriction(TeacherIdles teacherIdles, Semester activeSemester) {
		TypedQuery<TeacherIdles> existingRestriction = em.createQuery(
				"SELECT r FROM TeacherIdles r WHERE r.semester.id = :semesterId AND r.teacher.id = :teacherId",
				TeacherIdles.class);
		existingRestriction.setParameter("semesterId", activeSemester.getId());
		existingRestriction.setParameter("teacherId", teacherIdles.getTeacher().getId());

		if (existingRestriction.getResultList().size() > 0) {
			return null;
		}

		teacherIdles.setSemester(activeSemester);
		em.persist(teacherIdles);
		System.out.println("New idles teacher restriction is successfuly added to the database.");
		return teacherIdles;
	}

	@Override
	public GroupLoad addLoadGroupRestriction(GroupLoad groupLoad, Semester activeSemester) {
		TypedQuery<GroupLoad> existingRestriction = em.createQuery(
				"SELECT r FROM GroupLoad r WHERE r.semester.id = :semesterId AND r.group.id = :groupId",
				GroupLoad.class);
		existingRestriction.setParameter("semesterId", activeSemester.getId());
		existingRestriction.setParameter("groupId", groupLoad.getGroup().getId());

		if (existingRestriction.getResultList().size() > 0) {
			return null;
		}

		groupLoad.setSemester(activeSemester);
		em.persist(groupLoad);
		System.out.println("New load group restriction is successfuly added to the database");
		return groupLoad;
	}

	@Override
	public TeacherLoad addLoadTeacherRestriction(TeacherLoad teacherLoad, Semester activeSemester) {
		TypedQuery<TeacherLoad> existingRestriction = em.createQuery(
				"SELECT r FROM TeacherLoad r WHERE r.semester.id = :semesterId AND r.teacher.id = :teacherId",
				TeacherLoad.class);
		existingRestriction.setParameter("semesterId", activeSemester.getId());
		existingRestriction.setParameter("teacherId", teacherLoad.getTeacher().getId());

		if (existingRestriction.getResultList().size() > 0) {
			return null;
		}

		teacherLoad.setSemester(activeSemester);
		em.persist(teacherLoad);
		System.out.println("New load teacher restriction is successfuly added to the database");
		return teacherLoad;
	}

	@Override
	public void deleteNumDaysGroupRestriction(Long groupNumDaysId) {
		GroupNumDays groupNumDaysToDelete = em.find(GroupNumDays.class, groupNumDaysId);
		em.remove(groupNumDaysToDelete);
		System.out
				.println("Group num days restriction is successfully deleted from the database, id:" + groupNumDaysId);
	}

	@Override
	public void deleteNumDaysTeacherRestriction(Long teacherNumDaysId) {
		TeacherNumDays teacherNumDaysToDelete = em.find(TeacherNumDays.class, teacherNumDaysId);
		em.remove(teacherNumDaysToDelete);
		System.out.println(
				"Teacher num days restriction is successfully deleted from the database, id:" + teacherNumDaysId);
	}

	@Override
	public void deleteIdlesGroupRestriction(Long groupIdlesId) {
		GroupIdles groupIdlesToDelete = em.find(GroupIdles.class, groupIdlesId);
		em.remove(groupIdlesToDelete);
		System.out.println("Group idles restriction is successfully deleted from the database, id:" + groupIdlesId);
	}

	@Override
	public void deleteIdlesTeacherRestriction(Long teacherIdlesId) {
		TeacherIdles teacherIdlesToDelete = em.find(TeacherIdles.class, teacherIdlesId);
		em.remove(teacherIdlesToDelete);
		System.out.println("Teacher idles restriction is successfully deleted from the database, id:" + teacherIdlesId);
	}

	@Override
	public void deleteLoadGroupRestriction(Long groupLoadId) {
		GroupLoad groupLoadToDelete = em.find(GroupLoad.class, groupLoadId);
		em.remove(groupLoadToDelete);
		System.out.println("Group load restriction is successfully deleted from the database, id:" + groupLoadId);
	}

	@Override
	public void deleteLoadTeacherRestriction(Long teacherLoadId) {
		TeacherLoad teacherLoadToDelete = em.find(TeacherLoad.class, teacherLoadId);
		em.remove(teacherLoadToDelete);
		System.out.println("Teacher load restriction is successfully deleted from the database, id:" + teacherLoadId);
	}
}
