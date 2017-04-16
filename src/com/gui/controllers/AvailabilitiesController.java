package com.gui.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ejb.services.AvailabilitiesService;
import com.jpa.entities.Group;
import com.jpa.entities.GroupAvailability;
import com.jpa.entities.Period;
import com.jpa.entities.Room;
import com.jpa.entities.RoomAvailability;
import com.jpa.entities.TeacherAvailability;
import com.jpa.entities.User;

@ManagedBean
@ViewScoped
public class AvailabilitiesController {

	@EJB
	private AvailabilitiesService availabilitiesService;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	@ManagedProperty(value = "#{groupsController}")
	private GroupsController groupsController;

	@ManagedProperty(value = "#{usersController}")
	private UsersController usersController;

	@ManagedProperty(value = "#{roomsController}")
	private RoomsController roomsController;

	@ManagedProperty(value = "#{periodsController}")
	private PeriodsController periodsController;

	@ManagedProperty(value = "#{semesterController}")
	private SemesterController semesterController;

	// Teacher availability
	private User teacher = null;
	private String teacherAvailabilityDayMark;
	private Integer[] teacherAvailabilityTermNumbers;
	private String teacherAvailabilityType;
	private List<Integer> teacherAvailabilityTermsForSelectedDay = null;
	private List<TeacherAvailability> allTeacherAvailabilities = null;

	// Group availability
	private Group group = null;
	private String groupAvailabilityDayMark;
	private Integer[] groupAvailabilityTermNumbers;
	private String groupAvailabilityType;
	private List<Integer> groupAvailabilityTermsForSelectedDay = null;
	private List<GroupAvailability> allGroupAvailabilities = null;

	// Room availability
	private Room room = null;
	private String roomAvailabilityDayMark;
	private Integer[] roomAvailabilityTermNumbers;
	private String roomAvailabilityType;
	private List<Integer> roomAvailabilityTermsForSelectedDay = null;
	private List<RoomAvailability> allRoomAvailabilities = null;

	public List<Group> listAllGroups() {
		return groupsController.listGroups();
	}

	public List<User> listAllTeachers() {
		return usersController.listUsers();
	}

	public List<Room> listAllRooms() {
		return roomsController.listClassrooms();
	}

	public List<Period> listAllAvailablePeriods() {
		return periodsController.listAllPeriods();
	}

	public void clearData(String objectType) {
		if (objectType.equals("teacher")) {
			teacher = null;
			teacherAvailabilityType = null;
			teacherAvailabilityDayMark = null;
			teacherAvailabilityTermNumbers = null;
			teacherAvailabilityTermsForSelectedDay = null;
		} else if (objectType.equals("group")) {
			group = null;
			groupAvailabilityType = null;
			groupAvailabilityDayMark = null;
			groupAvailabilityTermNumbers = null;
			groupAvailabilityTermsForSelectedDay = null;
		} else {
			room = null;
			roomAvailabilityType = null;
			roomAvailabilityDayMark = null;
			roomAvailabilityTermNumbers = null;
			roomAvailabilityTermsForSelectedDay = null;
		}
	}

	public void listAllTeacherAvailableTermsForSelectedDay() {
		if (!teacherAvailabilityDayMark.isEmpty()) {
			if (teacherAvailabilityTermsForSelectedDay != null) {
				teacherAvailabilityTermsForSelectedDay.clear();
			} else {
				teacherAvailabilityTermsForSelectedDay = new ArrayList<>();
			}

			for (Period period : listAllAvailablePeriods()) {
				if (period.getDayMark().equals(teacherAvailabilityDayMark)) {
					for (int i = 0; i < period.getTermsNumber(); i++) {
						teacherAvailabilityTermsForSelectedDay.add(i + 1);
					}
					break;
				}
			}
		}
	}

	public void listAllGroupAvailableTermsForSelectedDay() {
		if (!groupAvailabilityDayMark.isEmpty()) {
			if (groupAvailabilityTermsForSelectedDay != null) {
				groupAvailabilityTermsForSelectedDay.clear();
			} else {
				groupAvailabilityTermsForSelectedDay = new ArrayList<>();
			}

			for (Period period : listAllAvailablePeriods()) {
				if (period.getDayMark().equals(groupAvailabilityDayMark)) {
					for (int i = 0; i < period.getTermsNumber(); i++) {
						groupAvailabilityTermsForSelectedDay.add(i + 1);
					}
					break;
				}
			}
		}
	}

	public void listAllRoomAvailableTermsForSelectedDay() {
		if (!roomAvailabilityDayMark.isEmpty()) {
			if (roomAvailabilityTermsForSelectedDay != null) {
				roomAvailabilityTermsForSelectedDay.clear();
			} else {
				roomAvailabilityTermsForSelectedDay = new ArrayList<>();
			}

			for (Period period : listAllAvailablePeriods()) {
				if (period.getDayMark().equals(roomAvailabilityDayMark)) {
					for (int i = 0; i < period.getTermsNumber(); i++) {
						roomAvailabilityTermsForSelectedDay.add(i + 1);
					}
					break;
				}
			}
		}
	}

	public void addTeacherAvailability() {
		if (loginController.userType() == 1) {
			teacher = loginController.getUser();
		}

		List<TeacherAvailability> teacherAvailabilityList = availabilitiesService.addTeacherAvailability(teacher,
				teacherAvailabilityDayMark, teacherAvailabilityTermNumbers, teacherAvailabilityType,
				semesterController.getActiveSemester());

		allTeacherAvailabilities.addAll(teacherAvailabilityList);

		clearData("teacher");
	}

	public void addGroupAvailability() {
		List<GroupAvailability> groupAvailabilityList = availabilitiesService.addGroupAvailability(group,
				groupAvailabilityDayMark, groupAvailabilityTermNumbers, groupAvailabilityType,
				semesterController.getActiveSemester());

		allGroupAvailabilities.addAll(groupAvailabilityList);

		clearData("group");
	}

	public void addRoomAvailability() {
		List<RoomAvailability> roomAvailabilityList = availabilitiesService.addRoomAvailability(room,
				roomAvailabilityDayMark, roomAvailabilityTermNumbers, roomAvailabilityType,
				semesterController.getActiveSemester());

		allRoomAvailabilities.addAll(roomAvailabilityList);

		clearData("room");
	}

	public List<TeacherAvailability> listAllTeacherAvailabilities() {
		if (allTeacherAvailabilities == null) {
			if (loginController.isAdmin()) {
				allTeacherAvailabilities = availabilitiesService
						.listAllTeacherAvailabilities(semesterController.getActiveSemester().getId());
			} else {
				allTeacherAvailabilities = availabilitiesService.listAllAvailabilitiesForTeacher(
						loginController.getUser().getId(), semesterController.getActiveSemester().getId());
			}
		}
		return allTeacherAvailabilities;
	}

	public List<GroupAvailability> listAllGroupAvailabilities() {
		if (allGroupAvailabilities == null) {
			allGroupAvailabilities = availabilitiesService
					.listAllGroupAvailabilities(semesterController.getActiveSemester().getId());
		}
		return allGroupAvailabilities;
	}

	public List<RoomAvailability> listAllRoomAvailabilities() {
		if (allRoomAvailabilities == null) {
			allRoomAvailabilities = availabilitiesService
					.listAllRoomAvailabilities(semesterController.getActiveSemester().getId());
		}
		return allRoomAvailabilities;
	}

	/* Getters and setters */

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public String getTeacherAvailabilityDayMark() {
		return teacherAvailabilityDayMark;
	}

	public void setTeacherAvailabilityDayMark(String teacherAvailabilityDayMark) {
		this.teacherAvailabilityDayMark = teacherAvailabilityDayMark;
	}

	public List<Integer> getTeacherAvailabilityTermsForSelectedDay() {
		return teacherAvailabilityTermsForSelectedDay;
	}

	public void setTeacherAvailabilityTermsForSelectedDay(List<Integer> teacherAvailabilityTermsForSelectedDay) {
		this.teacherAvailabilityTermsForSelectedDay = teacherAvailabilityTermsForSelectedDay;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public void setGroupsController(GroupsController groupsController) {
		this.groupsController = groupsController;
	}

	public void setUsersController(UsersController usersController) {
		this.usersController = usersController;
	}

	public void setRoomsController(RoomsController roomsController) {
		this.roomsController = roomsController;
	}

	public void setPeriodsController(PeriodsController periodsController) {
		this.periodsController = periodsController;
	}

	public void setSemesterController(SemesterController semesterController) {
		this.semesterController = semesterController;
	}

	public Integer[] getTeacherAvailabilityTermNumbers() {
		return teacherAvailabilityTermNumbers;
	}

	public void setTeacherAvailabilityTermNumbers(Integer[] teacherAvailabilityTermNumbers) {
		this.teacherAvailabilityTermNumbers = teacherAvailabilityTermNumbers;
	}

	public String getTeacherAvailabilityType() {
		return teacherAvailabilityType;
	}

	public void setTeacherAvailabilityType(String teacherAvailabilityType) {
		this.teacherAvailabilityType = teacherAvailabilityType;
	}

	public String getGroupAvailabilityDayMark() {
		return groupAvailabilityDayMark;
	}

	public void setGroupAvailabilityDayMark(String groupAvailabilityDayMark) {
		this.groupAvailabilityDayMark = groupAvailabilityDayMark;
	}

	public Integer[] getGroupAvailabilityTermNumbers() {
		return groupAvailabilityTermNumbers;
	}

	public void setGroupAvailabilityTermNumbers(Integer[] groupAvailabilityTermNumbers) {
		this.groupAvailabilityTermNumbers = groupAvailabilityTermNumbers;
	}

	public String getGroupAvailabilityType() {
		return groupAvailabilityType;
	}

	public void setGroupAvailabilityType(String groupAvailabilityType) {
		this.groupAvailabilityType = groupAvailabilityType;
	}

	public List<Integer> getGroupAvailabilityTermsForSelectedDay() {
		return groupAvailabilityTermsForSelectedDay;
	}

	public void setGroupAvailabilityTermsForSelectedDay(List<Integer> groupAvailabilityTermsForSelectedDay) {
		this.groupAvailabilityTermsForSelectedDay = groupAvailabilityTermsForSelectedDay;
	}

	public String getRoomAvailabilityDayMark() {
		return roomAvailabilityDayMark;
	}

	public void setRoomAvailabilityDayMark(String roomAvailabilityDayMark) {
		this.roomAvailabilityDayMark = roomAvailabilityDayMark;
	}

	public Integer[] getRoomAvailabilityTermNumbers() {
		return roomAvailabilityTermNumbers;
	}

	public void setRoomAvailabilityTermNumbers(Integer[] roomAvailabilityTermNumbers) {
		this.roomAvailabilityTermNumbers = roomAvailabilityTermNumbers;
	}

	public String getRoomAvailabilityType() {
		return roomAvailabilityType;
	}

	public void setRoomAvailabilityType(String roomAvailabilityType) {
		this.roomAvailabilityType = roomAvailabilityType;
	}

	public List<Integer> getRoomAvailabilityTermsForSelectedDay() {
		return roomAvailabilityTermsForSelectedDay;
	}

	public void setRoomAvailabilityTermsForSelectedDay(List<Integer> roomAvailabilityTermsForSelectedDay) {
		this.roomAvailabilityTermsForSelectedDay = roomAvailabilityTermsForSelectedDay;
	}
}
