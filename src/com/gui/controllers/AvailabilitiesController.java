package com.gui.controllers;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.jpa.entities.Semester;
import com.jpa.entities.TeacherAvailability;
import com.jpa.entities.User;
import com.jpa.entities.enums.AvailabilityType;

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
	private String teacherAvailabilityDayMark = null;
	private Integer[] teacherAvailabilityTermNumbers;
	private String teacherAvailabilityType = null;

	private Map<Integer, String> teacherAvailabilityTermsForSelectedDay = null;
	private List<TeacherAvailability> allTeacherAvailabilities = null;

	private TeacherAvailability selectedTeacherAvailability;

	// Group availability
	private Group group = null;
	private String groupAvailabilityDayMark = null;
	private Integer[] groupAvailabilityTermNumbers;
	private String groupAvailabilityType = null;

	private Map<Integer, String> groupAvailabilityTermsForSelectedDay = null;
	private List<GroupAvailability> allGroupAvailabilities = null;

	private GroupAvailability selectedGroupAvailability;

	// Room availability
	private Room room = null;
	private String roomAvailabilityDayMark = null;
	private Integer[] roomAvailabilityTermNumbers;
	private String roomAvailabilityType = null;

	private Map<Integer, String> roomAvailabilityTermsForSelectedDay = null;
	private List<RoomAvailability> allRoomAvailabilities = null;

	private RoomAvailability selectedRoomAvailability;

	// Indexes for deleting
	private int selectedAvailabilityIndex;
	private String availabilityObject;

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
		return periodsController.getAllPeriods();
	}

	public void clearData(String objectType) {
		if (objectType.equals("teacher") || objectType.equals("all")) {
			teacher = null;
			teacherAvailabilityType = null;
			teacherAvailabilityDayMark = null;
			teacherAvailabilityTermNumbers = null;
			teacherAvailabilityTermsForSelectedDay = null;
		}
		if (objectType.equals("group") || objectType.equals("all")) {
			group = null;
			groupAvailabilityType = null;
			groupAvailabilityDayMark = null;
			groupAvailabilityTermNumbers = null;
			groupAvailabilityTermsForSelectedDay = null;
		}
		if (objectType.equals("room") || objectType.equals("all")) {
			room = null;
			roomAvailabilityType = null;
			roomAvailabilityDayMark = null;
			roomAvailabilityTermNumbers = null;
			roomAvailabilityTermsForSelectedDay = null;
		}
	}

	public void listAllTeacherAvailableTermsForSelectedDay() {
		if (selectedTeacherAvailability != null) {
			teacherAvailabilityDayMark = selectedTeacherAvailability.getDayMark();
		}

		if (teacherAvailabilityDayMark != null && !teacherAvailabilityDayMark.isEmpty()) {
			if (teacherAvailabilityTermsForSelectedDay != null) {
				teacherAvailabilityTermsForSelectedDay.clear();
			} else {
				teacherAvailabilityTermsForSelectedDay = new LinkedHashMap<Integer, String>();
			}

			for (Period period : listAllAvailablePeriods()) {
				if (period.getDayMark().equals(teacherAvailabilityDayMark)) {
					List<String> termsTimeList = Arrays.asList(period.getTermsTime().split(","));

					for (int i = 0; i < period.getTermsNumber(); i++) {
						teacherAvailabilityTermsForSelectedDay.put(i + 1, termsTimeList.get(i).trim());
					}
					break;
				}
			}
		}
	}

	public void listAllGroupAvailableTermsForSelectedDay() {
		if (selectedGroupAvailability != null) {
			groupAvailabilityDayMark = selectedGroupAvailability.getDayMark();
		}

		if (groupAvailabilityDayMark != null && !groupAvailabilityDayMark.isEmpty()) {
			if (groupAvailabilityTermsForSelectedDay != null) {
				groupAvailabilityTermsForSelectedDay.clear();
			} else {
				groupAvailabilityTermsForSelectedDay = new LinkedHashMap<Integer, String>();
			}

			for (Period period : listAllAvailablePeriods()) {
				if (period.getDayMark().equals(groupAvailabilityDayMark)) {
					List<String> termsTimeList = Arrays.asList(period.getTermsTime().split(","));

					for (int i = 0; i < period.getTermsNumber(); i++) {
						groupAvailabilityTermsForSelectedDay.put(i + 1, termsTimeList.get(i).trim());
					}
					break;
				}
			}
		}
	}

	public void listAllRoomAvailableTermsForSelectedDay() {
		if (selectedRoomAvailability != null) {
			roomAvailabilityDayMark = selectedRoomAvailability.getDayMark();
		}

		if (roomAvailabilityDayMark != null && !roomAvailabilityDayMark.isEmpty()) {
			if (roomAvailabilityTermsForSelectedDay != null) {
				roomAvailabilityTermsForSelectedDay.clear();
			} else {
				roomAvailabilityTermsForSelectedDay = new LinkedHashMap<Integer, String>();
			}

			for (Period period : listAllAvailablePeriods()) {
				if (period.getDayMark().equals(roomAvailabilityDayMark)) {
					List<String> termsTimeList = Arrays.asList(period.getTermsTime().split(","));

					for (int i = 0; i < period.getTermsNumber(); i++) {
						roomAvailabilityTermsForSelectedDay.put(i + 1, termsTimeList.get(i).trim());
					}
					break;
				}
			}
		}
	}

	public void addTeacherAvailability() {
		if (!loginController.isAdmin()) {
			teacher = loginController.getUser();
		}

		List<TeacherAvailability> teacherAvailabilityList = availabilitiesService.addTeacherAvailability(teacher,
				teacherAvailabilityDayMark, teacherAvailabilityTermNumbers, teacherAvailabilityType,
				getActiveSemester());

		allTeacherAvailabilities.addAll(teacherAvailabilityList);

		clearData("teacher");
	}

	public void addGroupAvailability() {
		List<GroupAvailability> groupAvailabilityList = availabilitiesService.addGroupAvailability(group,
				groupAvailabilityDayMark, groupAvailabilityTermNumbers, groupAvailabilityType, getActiveSemester());

		allGroupAvailabilities.addAll(groupAvailabilityList);

		clearData("group");
	}

	public void addRoomAvailability() {
		List<RoomAvailability> roomAvailabilityList = availabilitiesService.addRoomAvailability(room,
				roomAvailabilityDayMark, roomAvailabilityTermNumbers, roomAvailabilityType, getActiveSemester());

		allRoomAvailabilities.addAll(roomAvailabilityList);

		clearData("room");
	}

	public List<TeacherAvailability> listAllTeacherAvailabilities() {
		if (allTeacherAvailabilities == null && getActiveSemester() != null) {
			if (loginController.isAdmin()) {
				allTeacherAvailabilities = availabilitiesService
						.listAllTeacherAvailabilities(getActiveSemester().getId());
			} else {
				allTeacherAvailabilities = availabilitiesService.listAllAvailabilitiesForTeacher(
						loginController.getUser().getId(), getActiveSemester().getId());
			}
		}
		return allTeacherAvailabilities;
	}

	public List<GroupAvailability> listAllGroupAvailabilities() {
		if (allGroupAvailabilities == null && getActiveSemester() != null) {
			allGroupAvailabilities = availabilitiesService.listAllGroupAvailabilities(getActiveSemester().getId());
		}
		return allGroupAvailabilities;
	}

	public List<RoomAvailability> listAllRoomAvailabilities() {
		if (allRoomAvailabilities == null && getActiveSemester() != null) {
			allRoomAvailabilities = availabilitiesService.listAllRoomAvailabilities(getActiveSemester().getId());
		}
		return allRoomAvailabilities;
	}

	public void deleteAvailability() {
		if (availabilityObject.equals("Teacher")) {
			TeacherAvailability teacherAvailabilityToDelete = allTeacherAvailabilities.get(selectedAvailabilityIndex);
			availabilitiesService.deleteTeacherAvailability(teacherAvailabilityToDelete.getId());
			allTeacherAvailabilities.remove(selectedAvailabilityIndex);
		} else if (availabilityObject.equals("Group")) {
			GroupAvailability groupAvailabilityToDelete = allGroupAvailabilities.get(selectedAvailabilityIndex);
			availabilitiesService.deleteGroupAvailability(groupAvailabilityToDelete.getId());
			allGroupAvailabilities.remove(selectedAvailabilityIndex);
		} else if (availabilityObject.equals("Room")) {
			RoomAvailability roomAvailabilityToDelete = allRoomAvailabilities.get(selectedAvailabilityIndex);
			availabilitiesService.deleteRoomAvailability(roomAvailabilityToDelete.getId());
			allRoomAvailabilities.remove(selectedAvailabilityIndex);
		}
	}

	public String getAvailabilityTypeValue(String availabilityType) {
		if (availabilityType.equals(AvailabilityType.DESIRABLE.toString())) {
			return "Poželjni";
		} else if (availabilityType.equals(AvailabilityType.FORBIDDEN.toString())) {
			return "Zabranjeni";
		} else if (availabilityType.equals(AvailabilityType.MANDATORY.toString())) {
			return "Obavezni";
		} else if (availabilityType.equals(AvailabilityType.UNDESIRABLE.toString())) {
			return "Nepoželjni";
		} else {
			return "Obični";
		}
	}

	public String getTermTime(int termNumber) {
		List<Period> allAvailablePeriods = listAllAvailablePeriods();
		if (allAvailablePeriods != null) {
			List<String> termsTimeList = Arrays.asList(allAvailablePeriods.get(0).getTermsTime().split(","));

			return termNumber + ". " + termsTimeList.get(termNumber - 1).trim();
		}
		return "";
	}

	/*
	 * Update methods
	 */

	public void updateTeacherAvailability() {
		availabilitiesService.updateTeacherAvailability(selectedTeacherAvailability);
	}

	public void updateGroupAvailability() {

	}

	public void updateRoomAvailability() {

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

	public Semester getActiveSemester() {
		return semesterController.getActiveSemester();
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

	public int getSelectedAvailabilityIndex() {
		return selectedAvailabilityIndex;
	}

	public void setSelectedAvailabilityIndex(int selectedAvailabilityIndex) {
		this.selectedAvailabilityIndex = selectedAvailabilityIndex;
	}

	public String getAvailabilityObject() {
		return availabilityObject;
	}

	public void setAvailabilityObject(String availabilityObject) {
		this.availabilityObject = availabilityObject;
	}

	public Map<Integer, String> getTeacherAvailabilityTermsForSelectedDay() {
		return teacherAvailabilityTermsForSelectedDay;
	}

	public void setTeacherAvailabilityTermsForSelectedDay(Map<Integer, String> teacherAvailabilityTermsForSelectedDay) {
		this.teacherAvailabilityTermsForSelectedDay = teacherAvailabilityTermsForSelectedDay;
	}

	public Map<Integer, String> getGroupAvailabilityTermsForSelectedDay() {
		return groupAvailabilityTermsForSelectedDay;
	}

	public void setGroupAvailabilityTermsForSelectedDay(Map<Integer, String> groupAvailabilityTermsForSelectedDay) {
		this.groupAvailabilityTermsForSelectedDay = groupAvailabilityTermsForSelectedDay;
	}

	public Map<Integer, String> getRoomAvailabilityTermsForSelectedDay() {
		return roomAvailabilityTermsForSelectedDay;
	}

	public void setRoomAvailabilityTermsForSelectedDay(Map<Integer, String> roomAvailabilityTermsForSelectedDay) {
		this.roomAvailabilityTermsForSelectedDay = roomAvailabilityTermsForSelectedDay;
	}

	public TeacherAvailability getSelectedTeacherAvailability() {
		return selectedTeacherAvailability;
	}

	public void setSelectedTeacherAvailability(TeacherAvailability selectedTeacherAvailability) {
		this.selectedTeacherAvailability = selectedTeacherAvailability;
	}

	public GroupAvailability getSelectedGroupAvailability() {
		return selectedGroupAvailability;
	}

	public void setSelectedGroupAvailability(GroupAvailability selectedGroupAvailability) {
		this.selectedGroupAvailability = selectedGroupAvailability;
	}

	public RoomAvailability getSelectedRoomAvailability() {
		return selectedRoomAvailability;
	}

	public void setSelectedRoomAvailability(RoomAvailability selectedRoomAvailability) {
		this.selectedRoomAvailability = selectedRoomAvailability;
	}
}
