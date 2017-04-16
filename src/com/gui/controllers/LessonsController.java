package com.gui.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ejb.services.LessonsService;
import com.jpa.entities.Group;
import com.jpa.entities.Lesson;
import com.jpa.entities.Room;
import com.jpa.entities.Subject;
import com.jpa.entities.User;

@ManagedBean
@ViewScoped
public class LessonsController {

	@EJB
	private LessonsService lessonsService;

	@ManagedProperty(value = "#{groupsController}")
	private GroupsController groupsController;

	@ManagedProperty(value = "#{usersController}")
	private UsersController usersController;

	@ManagedProperty(value = "#{roomsController}")
	private RoomsController roomsController;

	@ManagedProperty(value = "#{subjectsController}")
	private SubjectsController subjectsController;

	@ManagedProperty(value = "#{semesterController}")
	private SemesterController semesterController;

	private Long lessonsSearchSubjectId;
	private List<Lesson> subjectLessonsList;

	/*
	 * Insert fields
	 */
	private Subject subject;
	private User teacher;
	private Group group;
	private List<Room> selectedRooms = new ArrayList<Room>();
	private Set<User> selectedTeachers = new HashSet<User>();
	private Set<Group> selectedGroups = new HashSet<Group>();
	private String terms;
	private String note;

	public List<Group> listAllGroups() {
		return groupsController.listGroups();
	}

	public List<User> listAllTeachers() {
		return usersController.listUsers();
	}

	public List<Room> listAllRooms() {
		return roomsController.listClassrooms();
	}

	public List<Subject> listAllSubjects() {
		return subjectsController.listSubjects();
	}

	public void listLessonsForSubject() {
		subjectLessonsList = lessonsService.listLessons(lessonsSearchSubjectId,
				semesterController.getActiveSemester().getId());
	}

	public void addTeacherToList() {
		// avoid duplicated objects
		if (selectedTeachers.contains(teacher)) {
			return;
		}

		selectedTeachers.add(teacher);
	}

	public void addGroupToList() {
		// avoid duplicated objects
		if (selectedGroups.contains(group)) {
			return;
		}

		selectedGroups.add(group);
	}

	private void resetFields() {
		subject = null;
		group = null;
		teacher = null;
		terms = null;
		note = null;
		selectedGroups.clear();
		selectedTeachers.clear();
		selectedRooms.clear();
	}

	public void addLesson() {
		Set<Room> selectedRoomsSet = new HashSet<>(selectedRooms);
		lessonsService.addLesson(selectedTeachers, selectedGroups, subject, terms, selectedRoomsSet, note,
				semesterController.getActiveSemester());
		resetFields();
	}

	/*
	 * Getters and setters
	 */

	public Long getLessonsSearchSubjectId() {
		return lessonsSearchSubjectId;
	}

	public void setLessonsSearchSubjectId(Long lessonsSearchSubjectId) {
		this.lessonsSearchSubjectId = lessonsSearchSubjectId;
	}

	public List<Lesson> getSubjectLessonsList() {
		return subjectLessonsList;
	}

	public void setSubjectLessonsList(List<Lesson> subjectLessonsList) {
		this.subjectLessonsList = subjectLessonsList;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<Room> getSelectedRooms() {
		return selectedRooms;
	}

	public void setSelectedRooms(List<Room> selectedRooms) {
		this.selectedRooms = selectedRooms;
	}

	public Set<User> getSelectedTeachers() {
		return selectedTeachers;
	}

	public void setSelectedTeachers(Set<User> selectedTeachers) {
		this.selectedTeachers = selectedTeachers;
	}

	public Set<Group> getSelectedGroups() {
		return selectedGroups;
	}

	public void setSelectedGroups(Set<Group> selectedGroups) {
		this.selectedGroups = selectedGroups;
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

	public void setSubjectsController(SubjectsController subjectsController) {
		this.subjectsController = subjectsController;
	}

	public void setSemesterController(SemesterController semesterController) {
		this.semesterController = semesterController;
	}
}
