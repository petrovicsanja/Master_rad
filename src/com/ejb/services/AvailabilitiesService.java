package com.ejb.services;

import java.util.List;

import com.jpa.entities.Group;
import com.jpa.entities.GroupAvailability;
import com.jpa.entities.Room;
import com.jpa.entities.RoomAvailability;
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
			String type);

	public List<GroupAvailability> addGroupAvailability(Group group, String dayMark, Integer[] termNumbers,
			String type);

	public List<RoomAvailability> addRoomAvailability(Room room, String dayMark, Integer[] termNumbers, String type);

	public List<TeacherAvailability> listAllTeacherAvailabilities();

	public List<GroupAvailability> listAllGroupAvailabilities();

	public List<RoomAvailability> listAllRoomAvailabilities();

	public List<TeacherAvailability> listAllAvailabilitiesForTeacher(Long teacherId);
}
