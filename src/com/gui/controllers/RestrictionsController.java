package com.gui.controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ejb.services.RestrictionsService;
import com.jpa.entities.Group;
import com.jpa.entities.GroupIdles;
import com.jpa.entities.GroupLoad;
import com.jpa.entities.GroupNumDays;
import com.jpa.entities.TeacherIdles;
import com.jpa.entities.TeacherLoad;
import com.jpa.entities.TeacherNumDays;
import com.jpa.entities.User;

@ManagedBean
@ViewScoped
public class RestrictionsController {

	@EJB
	private RestrictionsService restrictionsService;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	@ManagedProperty(value = "#{groupsController}")
	private GroupsController groupsController;

	@ManagedProperty(value = "#{usersController}")
	private UsersController usersController;

	@ManagedProperty(value = "#{semesterController}")
	private SemesterController semesterController;

	// Group num days restriction
	private List<GroupNumDays> groupNumDaysRestrictionList = null;
	private GroupNumDays newGroupNumDays = new GroupNumDays();

	// Teacher num days restriction
	private List<TeacherNumDays> teacherNumDaysRestrictionList = null;
	private TeacherNumDays newTeacherNumDays = new TeacherNumDays();

	// Group idles restriction
	private List<GroupIdles> groupIdlesRestrictionList = null;
	private GroupIdles newGroupIdles = new GroupIdles();

	// Teacher idles restriction
	private List<TeacherIdles> teacherIdlesRestrictionList = null;
	private TeacherIdles newTeacherIdles = new TeacherIdles();

	// Group load restriction
	private List<GroupLoad> groupLoadRestrictionList = null;
	private GroupLoad newGroupLoad = new GroupLoad();

	// Teacher load restriction
	private List<TeacherLoad> teacherLoadRestrictionList = null;
	private TeacherLoad newTeacherLoad = new TeacherLoad();

	public List<Group> listAllGroups() {
		return groupsController.listGroups();
	}

	public List<User> listAllTeachers() {
		return usersController.listUsers();
	}

	public List<GroupNumDays> listNumDaysGroupRestrictions() {
		if (groupNumDaysRestrictionList == null) {
			groupNumDaysRestrictionList = restrictionsService
					.listNumDaysGroupRestrictions(semesterController.getActiveSemester());
		}
		return groupNumDaysRestrictionList;
	}

	public List<TeacherNumDays> listNumDaysTeacherRestrictionList() {
		if (teacherNumDaysRestrictionList == null) {
			Long teacherId = loginController.isAdmin() ? null : loginController.getUser().getId();
			teacherNumDaysRestrictionList = restrictionsService.listNumDaysTeacherRestrictions(teacherId,
					semesterController.getActiveSemester());
		}
		return teacherNumDaysRestrictionList;
	}

	public List<GroupIdles> listGroupIdlesRestrictionList() {
		if (groupIdlesRestrictionList == null) {
			groupIdlesRestrictionList = restrictionsService
					.listIdlesGroupRestrictions(semesterController.getActiveSemester());
		}
		return groupIdlesRestrictionList;
	}

	public List<TeacherIdles> listTeacherIdlesRestrictionList() {
		if (teacherIdlesRestrictionList == null) {
			Long teacherId = loginController.isAdmin() ? null : loginController.getUser().getId();
			teacherIdlesRestrictionList = restrictionsService.listIdlesTeacherRestrictions(teacherId,
					semesterController.getActiveSemester());
		}
		return teacherIdlesRestrictionList;
	}

	public List<GroupLoad> listGroupLoadRestrictionList() {
		if (groupLoadRestrictionList == null) {
			groupLoadRestrictionList = restrictionsService
					.listLoadGroupRestrictions(semesterController.getActiveSemester());
		}
		return groupLoadRestrictionList;
	}

	public List<TeacherLoad> listTeacherLoadRestrictionList() {
		if (teacherLoadRestrictionList == null) {
			Long teacherId = loginController.isAdmin() ? null : loginController.getUser().getId();
			teacherLoadRestrictionList = restrictionsService.listLoadTeacherRestrictions(teacherId,
					semesterController.getActiveSemester());
		}
		return teacherLoadRestrictionList;
	}

	public void addNewNumDaysGroupRestriction() {
		if (newGroupNumDays.getGroup() != null) {
			restrictionsService.addNewNumDaysGroupRestriction(newGroupNumDays, semesterController.getActiveSemester());
			groupNumDaysRestrictionList.add(newGroupNumDays);

			newGroupNumDays = new GroupNumDays();
		}
	}

	public void addNewNumDaysTeacherRestriction() {
		if (newTeacherNumDays.getTeacher() != null) {
			restrictionsService.addNewNumDaysTeacherRestriction(newTeacherNumDays,
					semesterController.getActiveSemester());
			teacherNumDaysRestrictionList.add(newTeacherNumDays);

			newTeacherNumDays = new TeacherNumDays();
		}
	}

	public void addIdlesGroupRestriction() {
		if (newGroupIdles.getGroup() != null) {
			restrictionsService.addIdlesGroupRestriction(newGroupIdles, semesterController.getActiveSemester());
			groupIdlesRestrictionList.add(newGroupIdles);

			newGroupIdles = new GroupIdles();
		}
	}

	public void addIdlesTeacherRestriction() {
		if (newTeacherIdles.getTeacher() != null) {
			restrictionsService.addIdlesTeacherRestriction(newTeacherIdles, semesterController.getActiveSemester());
			teacherIdlesRestrictionList.add(newTeacherIdles);

			newTeacherIdles = new TeacherIdles();
		}
	}

	public void addLoadGroupRestriction() {
		if (newGroupLoad.getGroup() != null) {
			restrictionsService.addLoadGroupRestriction(newGroupLoad, semesterController.getActiveSemester());
			groupLoadRestrictionList.add(newGroupLoad);

			newGroupLoad = new GroupLoad();
		}
	}

	public void addLoadTeacherRestriction() {
		if (newTeacherLoad.getTeacher() != null) {
			restrictionsService.addLoadTeacherRestriction(newTeacherLoad, semesterController.getActiveSemester());
			teacherLoadRestrictionList.add(newTeacherLoad);

			newTeacherLoad = new TeacherLoad();
		}
	}

	/*
	 * Getters and setters
	 */

	public void setGroupsController(GroupsController groupsController) {
		this.groupsController = groupsController;
	}

	public void setUsersController(UsersController usersController) {
		this.usersController = usersController;
	}

	public void setSemesterController(SemesterController semesterController) {
		this.semesterController = semesterController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public GroupNumDays getNewGroupNumDays() {
		return newGroupNumDays;
	}

	public void setNewGroupNumDays(GroupNumDays newGroupNumDays) {
		this.newGroupNumDays = newGroupNumDays;
	}

	public TeacherNumDays getNewTeacherNumDays() {
		return newTeacherNumDays;
	}

	public void setNewTeacherNumDays(TeacherNumDays newTeacherNumDays) {
		this.newTeacherNumDays = newTeacherNumDays;
	}

	public GroupIdles getNewGroupIdles() {
		return newGroupIdles;
	}

	public void setNewGroupIdles(GroupIdles newGroupIdles) {
		this.newGroupIdles = newGroupIdles;
	}

	public TeacherIdles getNewTeacherIdles() {
		return newTeacherIdles;
	}

	public void setNewTeacherIdles(TeacherIdles newTeacherIdles) {
		this.newTeacherIdles = newTeacherIdles;
	}

	public GroupLoad getNewGroupLoad() {
		return newGroupLoad;
	}

	public void setNewGroupLoad(GroupLoad newGroupLoad) {
		this.newGroupLoad = newGroupLoad;
	}

	public TeacherLoad getNewTeacherLoad() {
		return newTeacherLoad;
	}

	public void setNewTeacherLoad(TeacherLoad newTeacherLoad) {
		this.newTeacherLoad = newTeacherLoad;
	}
}
