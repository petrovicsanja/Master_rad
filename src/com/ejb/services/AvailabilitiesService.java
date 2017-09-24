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
 *         Business interface - It is used for defining the set of methods that
 *         are available to client
 */
public interface AvailabilitiesService {

	List<TeacherAvailability> addTeacherAvailability(User teacher, String dayMark, Integer[] termNumbers, String type,
			Semester activeSemester);

	List<GroupAvailability> addGroupAvailability(Group group, String dayMark, Integer[] termNumbers, String type,
			Semester activeSemester);

	List<RoomAvailability> addRoomAvailability(Room room, String dayMark, Integer[] termNumbers, String type,
			Semester activeSemester);

	List<TeacherAvailability> listAllTeacherAvailabilities(Long semesterId);

	List<GroupAvailability> listAllGroupAvailabilities(Long semesterId);

	List<RoomAvailability> listAllRoomAvailabilities(Long semesterId);

	List<TeacherAvailability> listAllAvailabilitiesForTeacher(Long teacherId, Long semesterId);

	void deleteTeacherAvailability(Long teacherAvailabilityId);

	void deleteGroupAvailability(Long groupAvailabilityId);

	void deleteRoomAvailability(Long roomAvailabilityId);

	void updateTeacherAvailability(TeacherAvailability teacherAvailability);

	void updateGroupAvailability(GroupAvailability groupAvailability);

	void updateRoomAvailability(RoomAvailability roomAvailability);
}
