package com.ejb.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.AvailabilitiesService;
import com.jpa.entities.Group;
import com.jpa.entities.GroupAvailability;
import com.jpa.entities.Room;
import com.jpa.entities.RoomAvailability;
import com.jpa.entities.Semester;
import com.jpa.entities.TeacherAvailability;
import com.jpa.entities.User;

/**
 * Implementation of services to work with teachers', groups' and rooms'
 * availability data
 * 
 * @author sanja
 *
 */

@Stateless
public class AvailabilitiesServiceImpl implements AvailabilitiesService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public List<TeacherAvailability> addTeacherAvailability(User teacher, String dayMark, Integer[] termNumbers,
			String type, Semester activeSemester) {
		List<TeacherAvailability> teacherAvailabilityList = new ArrayList<>();

		for (int i = 0; i < termNumbers.length; i++) {
			TeacherAvailability teacherAvailability = new TeacherAvailability();
			teacherAvailability.setTeacher(teacher);
			teacherAvailability.setDayMark(dayMark);
			teacherAvailability.setTermNumber(termNumbers[i]);
			teacherAvailability.setType(type);
			teacherAvailability.setSemester(activeSemester);
			teacherAvailabilityList.add(teacherAvailability);

			em.persist(teacherAvailability);
		}
		System.out.println("New teacher availabilities are added for user: " + teacher.getId());

		return teacherAvailabilityList;
	}

	@Override
	public List<GroupAvailability> addGroupAvailability(Group group, String dayMark, Integer[] termNumbers, String type,
			Semester activeSemester) {
		List<GroupAvailability> groupAvailabilityList = new ArrayList<>();

		for (int i = 0; i < termNumbers.length; i++) {
			GroupAvailability groupAvailability = new GroupAvailability();
			groupAvailability.setGroup(group);
			groupAvailability.setDayMark(dayMark);
			groupAvailability.setTermNumber(termNumbers[i]);
			groupAvailability.setType(type);
			groupAvailability.setSemester(activeSemester);
			groupAvailabilityList.add(groupAvailability);

			em.persist(groupAvailability);
		}
		System.out.println("New group availabilities are added for group: " + group.getId());

		return groupAvailabilityList;
	}

	@Override
	public List<RoomAvailability> addRoomAvailability(Room room, String dayMark, Integer[] termNumbers, String type,
			Semester activeSemester) {
		List<RoomAvailability> roomAvailabilityList = new ArrayList<>();

		for (int i = 0; i < termNumbers.length; i++) {
			RoomAvailability roomAvailability = new RoomAvailability();
			roomAvailability.setRoom(room);
			roomAvailability.setDayMark(dayMark);
			roomAvailability.setTermNumber(termNumbers[i]);
			roomAvailability.setType(type);
			roomAvailability.setSemester(activeSemester);
			roomAvailabilityList.add(roomAvailability);

			em.persist(roomAvailability);
		}
		System.out.println("New room availabilities are added for room: " + room.getId());

		return roomAvailabilityList;
	}

	@Override
	public List<TeacherAvailability> listAllTeacherAvailabilities(Long semesterId) {
		TypedQuery<TeacherAvailability> allTeacherAvailabilities = em.createQuery(
				"SELECT a FROM TeacherAvailability a WHERE a.semester.id = :semesterId ORDER BY a.teacher.firstName",
				TeacherAvailability.class);
		allTeacherAvailabilities.setParameter("semesterId", semesterId);
		return allTeacherAvailabilities.getResultList();
	}

	@Override
	public List<GroupAvailability> listAllGroupAvailabilities(Long semesterId) {
		TypedQuery<GroupAvailability> allGroupAvailabilities = em.createQuery(
				"SELECT a FROM GroupAvailability a WHERE a.semester.id = :semesterId ORDER BY a.group.name",
				GroupAvailability.class);
		allGroupAvailabilities.setParameter("semesterId", semesterId);
		return allGroupAvailabilities.getResultList();
	}

	@Override
	public List<RoomAvailability> listAllRoomAvailabilities(Long semesterId) {
		TypedQuery<RoomAvailability> allRoomAvailabilities = em.createQuery(
				"SELECT a FROM RoomAvailability a WHERE a.semester.id = :semesterId ORDER BY a.room.name",
				RoomAvailability.class);
		allRoomAvailabilities.setParameter("semesterId", semesterId);
		return allRoomAvailabilities.getResultList();
	}

	@Override
	public List<TeacherAvailability> listAllAvailabilitiesForTeacher(Long teacherId, Long semesterId) {
		TypedQuery<TeacherAvailability> availabilityList = em.createQuery(
				"SELECT a FROM TeacherAvailability a WHERE a.teacher.id = :teacherId AND a.semester.id = :semesterId",
				TeacherAvailability.class);
		availabilityList.setParameter("teacherId", teacherId);
		availabilityList.setParameter("semesterId", semesterId);
		return availabilityList.getResultList();
	}

	@Override
	public void deleteTeacherAvailability(Long teacherAvailabilityId) {
		TeacherAvailability teacherAvailabilityToDelete = em.find(TeacherAvailability.class, teacherAvailabilityId);
		em.remove(teacherAvailabilityToDelete);
		System.out.println(
				"Teacher availability is successfully deleted from the database, id: " + teacherAvailabilityId);
	}

	@Override
	public void deleteGroupAvailability(Long groupAvailabilityId) {
		GroupAvailability groupAvailabilityToDelete = em.find(GroupAvailability.class, groupAvailabilityId);
		em.remove(groupAvailabilityToDelete);
		System.out.println("Group availability is successfully deleted from the database, id: " + groupAvailabilityId);
	}

	@Override
	public void deleteRoomAvailability(Long roomAvailabilityId) {
		RoomAvailability roomAvailabilityToDelete = em.find(RoomAvailability.class, roomAvailabilityId);
		em.remove(roomAvailabilityToDelete);
		System.out.println("Room availability is successfully deleted from the database, id: " + roomAvailabilityId);
	}

	/*
	 * Update methods
	 */

	@Override
	public void updateTeacherAvailability(TeacherAvailability teacherAvailability) {
		TeacherAvailability teacherAvailabilityToUpdate = em.find(TeacherAvailability.class,
				teacherAvailability.getId());
		teacherAvailabilityToUpdate.setDayMark(teacherAvailability.getDayMark());
		teacherAvailabilityToUpdate.setTermNumber(teacherAvailability.getTermNumber());
		teacherAvailabilityToUpdate.setType(teacherAvailability.getType());

		System.out.println(
				"Teacher availability is successfully updated in the database, id: " + teacherAvailability.getId());
	}

	@Override
	public void updateGroupAvailability(GroupAvailability groupAvailability) {
		GroupAvailability groupAvailabilityToUpdate = em.find(GroupAvailability.class, groupAvailability.getId());
		groupAvailabilityToUpdate.setDayMark(groupAvailability.getDayMark());
		groupAvailabilityToUpdate.setTermNumber(groupAvailability.getTermNumber());
		groupAvailabilityToUpdate.setType(groupAvailability.getType());

		System.out.println(
				"Group availability is successfully updated in the database, id: " + groupAvailability.getId());
	}

	@Override
	public void updateRoomAvailability(RoomAvailability roomAvailability) {
		RoomAvailability roomAvailabilityToUpdate = em.find(RoomAvailability.class, roomAvailability.getId());
		roomAvailabilityToUpdate.setDayMark(roomAvailability.getDayMark());
		roomAvailabilityToUpdate.setTermNumber(roomAvailability.getTermNumber());
		roomAvailabilityToUpdate.setType(roomAvailability.getType());

		System.out
				.println("Room availability is successfully updated in the database, id: " + roomAvailability.getId());
	}
}
