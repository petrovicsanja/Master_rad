package com.ejb.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.AvailabilitiesService;
import com.jpa.entities.Group;
import com.jpa.entities.GroupAvailability;
import com.jpa.entities.Room;
import com.jpa.entities.RoomAvailability;
import com.jpa.entities.TeacherAvailability;
import com.jpa.entities.User;

@Stateful
public class AvailabilitiesServiceImpl implements AvailabilitiesService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public List<TeacherAvailability> addTeacherAvailability(User teacher, String dayMark, Integer[] termNumbers,
			String type) {
		List<TeacherAvailability> teacherAvailabilityList = new ArrayList<>();

		for (int i = 0; i < termNumbers.length; i++) {
			TeacherAvailability teacherAvailability = new TeacherAvailability();
			teacherAvailability.setTeacher(teacher);
			teacherAvailability.setDayMark(dayMark);
			teacherAvailability.setTermNumber(termNumbers[i]);
			teacherAvailability.setType(type);
			teacherAvailabilityList.add(teacherAvailability);

			em.persist(teacherAvailability);
		}
		System.out.println("New teacher availabilities are added for user: " + teacher.getIdKorisnika());

		return teacherAvailabilityList;
	}

	@Override
	public List<GroupAvailability> addGroupAvailability(Group group, String dayMark, Integer[] termNumbers,
			String type) {
		List<GroupAvailability> groupAvailabilityList = new ArrayList<>();

		for (int i = 0; i < termNumbers.length; i++) {
			GroupAvailability groupAvailability = new GroupAvailability();
			groupAvailability.setGroup(group);
			groupAvailability.setDayMark(dayMark);
			groupAvailability.setTermNumber(termNumbers[i]);
			groupAvailability.setType(type);
			groupAvailabilityList.add(groupAvailability);

			em.persist(groupAvailability);
		}
		System.out.println("New group availabilities are added for group: " + group.getIdGrupe());

		return groupAvailabilityList;
	}

	@Override
	public List<RoomAvailability> addRoomAvailability(Room room, String dayMark, Integer[] termNumbers, String type) {
		List<RoomAvailability> roomAvailabilityList = new ArrayList<>();

		for (int i = 0; i < termNumbers.length; i++) {
			RoomAvailability roomAvailability = new RoomAvailability();
			roomAvailability.setRoom(room);
			roomAvailability.setDayMark(dayMark);
			roomAvailability.setTermNumber(termNumbers[i]);
			roomAvailability.setType(type);
			roomAvailabilityList.add(roomAvailability);

			em.persist(roomAvailability);
		}
		System.out.println("New room availabilities are added for room: " + room.getIdUcionice());

		return roomAvailabilityList;
	}

	@Override
	public List<TeacherAvailability> listAllTeacherAvailabilities() {
		TypedQuery<TeacherAvailability> allTeacherAvailabilities = em
				.createQuery("SELECT a FROM TeacherAvailability a ORDER BY a.teacher.ime", TeacherAvailability.class);
		return allTeacherAvailabilities.getResultList();
	}

	@Override
	public List<GroupAvailability> listAllGroupAvailabilities() {
		TypedQuery<GroupAvailability> allGroupAvailabilities = em
				.createQuery("SELECT a FROM GroupAvailability a ORDER BY a.group.naziv", GroupAvailability.class);
		return allGroupAvailabilities.getResultList();
	}

	@Override
	public List<RoomAvailability> listAllRoomAvailabilities() {
		TypedQuery<RoomAvailability> allRoomAvailabilities = em
				.createQuery("SELECT a FROM RoomAvailability a ORDER BY a.room.naziv", RoomAvailability.class);
		return allRoomAvailabilities.getResultList();
	}

	@Override
	public List<TeacherAvailability> listAllAvailabilitiesForTeacher(Long teacherId) {
		TypedQuery<TeacherAvailability> availabilityList = em.createQuery(
				"SELECT a FROM TeacherAvailability a WHERE a.teacher.idKorisnika = :teacherId",
				TeacherAvailability.class);
		availabilityList.setParameter("teacherId", teacherId);
		return availabilityList.getResultList();
	}
}
