package com.gui.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.ejb.services.TimetableService;
import com.jpa.entities.Group;
import com.jpa.entities.Room;
import com.jpa.entities.Semester;
import com.jpa.entities.User;

@ManagedBean
@SessionScoped
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
	private String timetableData;

	private String heading;

	public String createTimetable() {
		Semester activeSemester = semesterController.getActiveSemester();
		String action = null;

		if (activeSemester != null) {
			timetableData = timetableService.createTimetable(activeSemester);
			action = "timetable?faces-redirect=true";
		}

		return action;
	}

	public void download() {
		if (timetableData != null && timetableData.length() > 0) {
			byte[] exportContent = timetableData.getBytes();
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();

			ec.responseReset();
			ec.setResponseContentType("text/plain");
			ec.setResponseContentLength(exportContent.length);
			String attachmentName = "attachment; filename=\"timetable.tts\"";
			ec.setResponseHeader("Content-Disposition", attachmentName);

			try {
				OutputStream output = ec.getResponseOutputStream();
				output.write(exportContent);
				output.flush();
				output.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			fc.responseComplete();
		}
	}

	public void getTimetableForGroup() {
		selectedTeacher = null;
		selectedRoom = null;

		setHeading("Raspored časova za grupu: " + selectedGroup.toString());
	}

	public void getTimetableForTeacher() {
		selectedGroup = null;
		selectedRoom = null;

		setHeading("Raspored časova za profesora: " + selectedTeacher.toString());
	}

	public void getTimeTableForRoom() {
		selectedGroup = null;
		selectedTeacher = null;

		setHeading("Raspored časova za učionicu: " + selectedRoom.toString());
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

	public String getTimetableData() {
		return timetableData;
	}

	public void setTimetableData(String timetableData) {
		this.timetableData = timetableData;
	}

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

	public String getHeading() {
		return heading;
	}

	public void setHeading(String test) {
		this.heading = test;
	}
}
