package com.gui.controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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

	@ManagedProperty(value = "#{periodsController}")
	private PeriodsController periodsController;

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

	// Indexes for deleting
	private int selectedRestrictionIndex;
	private String restrictionType;

	public List<Group> listAllGroups() {
		return groupsController.listGroups();
	}

	public List<User> listAllTeachers() {
		return usersController.listUsers();
	}

	public List<GroupNumDays> listNumDaysGroupRestrictions() {
		groupNumDaysRestrictionList = restrictionsService
				.listNumDaysGroupRestrictions(semesterController.getActiveSemester());
		return groupNumDaysRestrictionList;
	}

	public List<TeacherNumDays> listNumDaysTeacherRestrictionList() {
		Long teacherId = loginController.isAdmin() ? null : loginController.getUser().getId();
		teacherNumDaysRestrictionList = restrictionsService.listNumDaysTeacherRestrictions(teacherId,
				semesterController.getActiveSemester());
		return teacherNumDaysRestrictionList;
	}

	public List<GroupIdles> listGroupIdlesRestrictionList() {
		groupIdlesRestrictionList = restrictionsService
				.listIdlesGroupRestrictions(semesterController.getActiveSemester());
		return groupIdlesRestrictionList;
	}

	public List<TeacherIdles> listTeacherIdlesRestrictionList() {
		Long teacherId = loginController.isAdmin() ? null : loginController.getUser().getId();
		teacherIdlesRestrictionList = restrictionsService.listIdlesTeacherRestrictions(teacherId,
				semesterController.getActiveSemester());
		return teacherIdlesRestrictionList;
	}

	public List<GroupLoad> listGroupLoadRestrictionList() {
		groupLoadRestrictionList = restrictionsService
				.listLoadGroupRestrictions(semesterController.getActiveSemester());
		return groupLoadRestrictionList;
	}

	public List<TeacherLoad> listTeacherLoadRestrictionList() {
		Long teacherId = loginController.isAdmin() ? null : loginController.getUser().getId();
		teacherLoadRestrictionList = restrictionsService.listLoadTeacherRestrictions(teacherId,
				semesterController.getActiveSemester());
		return teacherLoadRestrictionList;
	}

	public void addNewNumDaysGroupRestriction() {
		boolean numDaysCondition = (newGroupNumDays.getMin() >= 1)
				&& (newGroupNumDays.getOpt() >= newGroupNumDays.getMin())
				&& (newGroupNumDays.getMax() >= newGroupNumDays.getOpt())
				&& (periodsController.getDistinctWorkingDays().size() >= newGroupNumDays.getMax());

		if (numDaysCondition && newGroupNumDays.getGroup() != null) {
			GroupNumDays restrictionToAdd = restrictionsService.addNewNumDaysGroupRestriction(newGroupNumDays,
					semesterController.getActiveSemester());

			if (restrictionToAdd == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Ograničenje za izabranu grupu u trenutno aktivnom semestru već postoji.", null));
				return;
			}

			newGroupNumDays = new GroupNumDays();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uslovi za unos ograničenja nisu ispunjeni.", null));
		}
	}

	public void addNewNumDaysTeacherRestriction() {
		if (!loginController.isAdmin()) {
			newTeacherNumDays.setTeacher(loginController.getUser());
		}

		boolean numDaysCondition = (newTeacherNumDays.getMin() >= 1)
				&& (newTeacherNumDays.getOpt() >= newTeacherNumDays.getMin())
				&& (newTeacherNumDays.getMax() >= newTeacherNumDays.getOpt())
				&& (periodsController.getDistinctWorkingDays().size() >= newTeacherNumDays.getMax());

		if (numDaysCondition && newTeacherNumDays.getTeacher() != null) {
			TeacherNumDays restrictionToAdd = restrictionsService.addNewNumDaysTeacherRestriction(newTeacherNumDays,
					semesterController.getActiveSemester());

			if (restrictionToAdd == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Ograničenje za izabranog profesora u trenutno aktivnom semestru već postoji.", null));
				return;
			}

			newTeacherNumDays = new TeacherNumDays();
		} else {
			System.out.println("Conditions for NumDays restriction are not fulfilled.");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uslovi za unos ograničenja nisu ispunjeni.", null));
		}
	}

	public void addIdlesGroupRestriction() {
		if (newGroupIdles.getGroup() != null) {
			GroupIdles restrictionToAdd = restrictionsService.addIdlesGroupRestriction(newGroupIdles,
					semesterController.getActiveSemester());

			if (restrictionToAdd == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Ograničenje za izabranu grupu u trenutno aktivnom semestru već postoji.", null));
				return;
			}

			newGroupIdles = new GroupIdles();
		}
	}

	public void addIdlesTeacherRestriction() {
		if (!loginController.isAdmin()) {
			newTeacherIdles.setTeacher(loginController.getUser());
		}

		if (newTeacherIdles.getTeacher() != null) {
			TeacherIdles restrictionToAdd = restrictionsService.addIdlesTeacherRestriction(newTeacherIdles,
					semesterController.getActiveSemester());

			if (restrictionToAdd == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Ograničenje za izabranog profesora u trenutno aktivnom semestru već postoji.", null));
				return;
			}

			newTeacherIdles = new TeacherIdles();
		}
	}

	public void addLoadGroupRestriction() {
		if (newGroupLoad.getGroup() != null) {
			GroupLoad restrictionToAdd = restrictionsService.addLoadGroupRestriction(newGroupLoad,
					semesterController.getActiveSemester());

			if (restrictionToAdd == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Ograničenje za izabranu grupu u trenutno aktivnom semestru već postoji.", null));
				return;
			}

			newGroupLoad = new GroupLoad();
		}
	}

	public void addLoadTeacherRestriction() {
		if (!loginController.isAdmin()) {
			newTeacherLoad.setTeacher(loginController.getUser());
		}

		if (newTeacherLoad.getTeacher() != null) {
			TeacherLoad restrictionToAdd = restrictionsService.addLoadTeacherRestriction(newTeacherLoad,
					semesterController.getActiveSemester());

			if (restrictionToAdd == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Ograničenje za izabranog profesora u trenutno aktivnom semestru već postoji.", null));
				return;
			}

			newTeacherLoad = new TeacherLoad();
		}
	}

	public void deleteRestriction() {
		if (restrictionType.equals("GroupNumDays")) {
			GroupNumDays groupNumDaysToDelete = groupNumDaysRestrictionList.get(selectedRestrictionIndex);
			restrictionsService.deleteNumDaysGroupRestriction(groupNumDaysToDelete.getId());
		} else if (restrictionType.equals("TeacherNumDays")) {
			TeacherNumDays teacherNumDaysToDelete = teacherNumDaysRestrictionList.get(selectedRestrictionIndex);
			restrictionsService.deleteNumDaysTeacherRestriction(teacherNumDaysToDelete.getId());
		} else if (restrictionType.equals("GroupIdles")) {
			GroupIdles groupIdlesToDelete = groupIdlesRestrictionList.get(selectedRestrictionIndex);
			restrictionsService.deleteIdlesGroupRestriction(groupIdlesToDelete.getId());
		} else if (restrictionType.equals("TeacherIdles")) {
			TeacherIdles teacherIdlesToDelete = teacherIdlesRestrictionList.get(selectedRestrictionIndex);
			restrictionsService.deleteIdlesTeacherRestriction(teacherIdlesToDelete.getId());
		} else if (restrictionType.equals("GroupLoad")) {
			GroupLoad groupLoadToDelete = groupLoadRestrictionList.get(selectedRestrictionIndex);
			restrictionsService.deleteLoadGroupRestriction(groupLoadToDelete.getId());
		} else if (restrictionType.equals("TeacherLoad")) {
			TeacherLoad teacherLoadToDelete = teacherLoadRestrictionList.get(selectedRestrictionIndex);
			restrictionsService.deleteLoadTeacherRestriction(teacherLoadToDelete.getId());
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

	public void setPeriodsController(PeriodsController periodsController) {
		this.periodsController = periodsController;
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

	public int getSelectedRestrictionIndex() {
		return selectedRestrictionIndex;
	}

	public void setSelectedRestrictionIndex(int selectedRestrictionIndex) {
		this.selectedRestrictionIndex = selectedRestrictionIndex;
	}

	public String getRestrictionType() {
		return restrictionType;
	}

	public void setRestrictionType(String restrictionType) {
		this.restrictionType = restrictionType;
	}
}
