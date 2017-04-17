package com.gui.controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ejb.services.TimetableService;
import com.jpa.entities.Group;
import com.jpa.entities.Room;
import com.jpa.entities.Semester;
import com.jpa.entities.User;

@ManagedBean
@ViewScoped
public class TimetableController {

	@EJB
	private TimetableService timetableService;

	@ManagedProperty(value = "#{semesterController}")
	private SemesterController semesterController;

	@ManagedProperty(value = "#{groupsController}")
	private GroupsController groupsController;

	@ManagedProperty(value = "#{usersController}")
	private UsersController usersController;

	@ManagedProperty(value = "#{roomsController}")
	private RoomsController roomsController;

	private User selectedTeacher = null;
	private Group selectedGroup = null;
	private Room selectedRoom = null;

	public String createTimetable() {
		Semester activeSemester = semesterController.getActiveSemester();
		String action = null;

		if (activeSemester != null) {
			timetableService.createTimetable(activeSemester);
			action = "timetable?faces-redirect=true";
		}

		return action;
	}

	public List<Group> listGroups() {
		return groupsController.listGroups();
	}

	public List<Room> listRooms() {
		return roomsController.listClassrooms();
	}

	public List<User> listTeachers() {
		return usersController.listUsers();
	}

	/*
	 * Getters and setters
	 */

	public User getSelectedTeacher() {
		return selectedTeacher;
	}

	public void setSelectedTeacher(User selectedTeacher) {
		this.selectedTeacher = selectedTeacher;
	}

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public Room getSelectedRoom() {
		return selectedRoom;
	}

	public void setSelectedRoom(Room selectedRoom) {
		this.selectedRoom = selectedRoom;
	}

	public void setSemesterController(SemesterController semesterController) {
		this.semesterController = semesterController;
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
}
