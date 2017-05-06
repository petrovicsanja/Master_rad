package com.ejb.services;

import java.util.List;

import com.jpa.entities.Group;
import com.jpa.entities.GroupAvailability;
import com.jpa.entities.Room;
import com.jpa.entities.RoomAvailability;
import com.jpa.entities.Semester;
import com.jpa.entities.TeacherAvailability;
import com.jpa.entities.User;

/**
 * @author Sanja
 *
 *         Interfejs koji sluzi za definisanje skupa metoda koje su dostupne
 *         klijentima tzv. business interface
 */
public interface AvailabilitiesService {

	public List<TeacherAvailability> addTeacherAvailability(User teacher, String dayMark, Integer[] termNumbers,
			String type, Semester activeSemester);

	public List<GroupAvailability> addGroupAvailability(Group group, String dayMark, Integer[] termNumbers, String type,
			Semester activeSemester);

	public List<RoomAvailability> addRoomAvailability(Room room, String dayMark, Integer[] termNumbers, String type,
			Semester activeSemester);

	public List<TeacherAvailability> listAllTeacherAvailabilities(Long semesterId);

	public List<GroupAvailability> listAllGroupAvailabilities(Long semesterId);

	public List<RoomAvailability> listAllRoomAvailabilities(Long semesterId);

	public List<TeacherAvailability> listAllAvailabilitiesForTeacher(Long teacherId, Long semesterId);

	public void deleteTeacherAvailability(Long teacherAvailabilityId);

	public void deleteGroupAvailability(Long groupAvailabilityId);

	public void deleteRoomAvailability(Long roomAvailabilityId);
}
