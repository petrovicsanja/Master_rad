package com.gui.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.ejb.services.TimetableService;
import com.jpa.entities.Group;
import com.jpa.entities.Room;
import com.jpa.entities.Semester;
import com.jpa.entities.Subject;
import com.jpa.entities.Timetable;
import com.jpa.entities.User;

@ManagedBean
@ViewScoped
public class TimetableController {

	@EJB
	private TimetableService timetableService;

	@ManagedProperty(value = "#{semesterController.activeSemester}")
	private Semester activeSemester;

	@ManagedProperty(value = "#{groupsController.listGroups()}")
	private List<Group> groupList;

	@ManagedProperty(value = "#{usersController.listUsers()}")
	private List<User> teacherList;

	@ManagedProperty(value = "#{roomsController.listClassrooms()}")
	private List<Room> roomList;

	@ManagedProperty(value = "#{subjectsController.listSubjects()}")
	private List<Subject> subjectList;

	@ManagedProperty(value = "#{periodsController}")
	private PeriodsController periodsController;

	private User selectedTeacher = null;
	private Group selectedGroup = null;
	private Room selectedRoom = null;
	private String timetableParams;
	private List<Timetable> timetable;

	private String heading;
	private String info;

	public String createTimetable() {
		String action = null;

		if (activeSemester != null) {
			timetableParams = timetableService.createTimetable(activeSemester);
			timetableService.parseTimetableData(activeSemester);
			action = "timetable?faces-redirect=true";
		}

		return action;
	}

	public void download() {
		if (timetableParams != null && timetableParams.length() > 0) {
			byte[] exportContent = timetableParams.getBytes();
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

	public void getTimetableForTeacher() {
		selectedGroup = null;
		selectedRoom = null;

		setHeading("Raspored časova za profesora: " + selectedTeacher.toString());

		timetable = timetableService.getTimetableDataForTeacher(activeSemester, selectedTeacher);

		paintTimetable();
	}

	public void getTimetableForGroup() {
		selectedTeacher = null;
		selectedRoom = null;

		setHeading("Raspored časova za grupu: " + selectedGroup.toString());

		timetable = timetableService.getTimetableDataForGroup(activeSemester, selectedGroup);

		paintTimetable();
	}

	public void getTimeTableForRoom() {
		selectedGroup = null;
		selectedTeacher = null;

		setHeading("Raspored časova za učionicu: " + selectedRoom.toString());

		timetable = timetableService.getTimetableDataForRoom(activeSemester, selectedRoom);

		paintTimetable();
	}

	private void paintTimetable() {
		if (timetable.size() > 0) {
			StringBuffer timetableHtml = new StringBuffer("<table id='timetable' class='table table-bordered'>");

			/*
			 * HEADING
			 */

			// terms time
			List<String> termsTime = periodsController.getTermsTimeValues();
			int NUMBER_OF_TERMS = termsTime.size();

			timetableHtml.append("<tr class='timetable-cell-colored'>");
			timetableHtml.append("<td></td>");
			for (int i = 1; i <= NUMBER_OF_TERMS; i++) {
				timetableHtml.append("<td>" + i + ": " + termsTime.get(i - 1) + "</td>");
			}
			timetableHtml.append("</tr>");

			/*
			 * BODY
			 */

			List<String> workingDays = periodsController.getDistinctWorkingDays();
			for (String day : workingDays) {
				timetableHtml.append("<tr>");

				timetableHtml.append("<td class='timetable-cell-colored'>" + day + "</td>");
				List<Timetable> timetableForWorkingDay = timetable.stream()
						.filter(item -> day.substring(0, 3).equalsIgnoreCase(item.getDayMark()))
						.collect(Collectors.toList());

				for (Timetable timetableItem : timetableForWorkingDay) {
					int termNumber = 1;

					while (termNumber <= NUMBER_OF_TERMS) {
						if (timetableItem.getStartTerm() == termNumber) {
							if (timetableItem.getLessonLength() > 1) {
								timetableHtml.append("<td colspan='" + timetableItem.getLessonLength() + "'>");
							} else {
								timetableHtml.append("<td>");
							}
							timetableHtml.append(timetableItem.getSubject().toString());
							timetableHtml.append("</br>");
							for (User teacher : timetableItem.getTeachers()) {
								timetableHtml.append(teacher.toString());
							}
							timetableHtml.append("</br>");
							timetableHtml.append(timetableItem.getRoom().toString() + "</td>");

							termNumber += timetableItem.getLessonLength();
						} else {
							timetableHtml.append("<td></td>");
							termNumber++;
						}
					}
				}

				timetableHtml.append("</tr>");
			}

			timetableHtml.append("</table>");

			setInfo(timetableHtml.toString());
		} else {
			setInfo("Nema podataka za odabrane kriterijume.");
		}
	}

	/*
	 * Getters and setters
	 */

	public String getTimetableData() {
		return timetableParams;
	}

	public void setTimetableData(String timetableData) {
		this.timetableParams = timetableData;
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

	public void setActiveSemester(Semester activeSemester) {
		this.activeSemester = activeSemester;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public List<User> getTeacherList() {
		return teacherList;
	}

	public void setTeacherList(List<User> userList) {
		this.teacherList = userList;
	}

	public List<Room> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<Room> roomList) {
		this.roomList = roomList;
	}

	public List<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	public void setPeriodsController(PeriodsController periodsController) {
		this.periodsController = periodsController;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String test) {
		this.heading = test;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<Timetable> getTimetable() {
		return timetable;
	}
}
