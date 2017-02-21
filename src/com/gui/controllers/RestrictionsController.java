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

	@ManagedProperty(value = "#{groupsController}")
	private GroupsController groupsController;

	@ManagedProperty(value = "#{usersController}")
	private UsersController usersController;

	// Group num days restriction
	private Group numDaysGroup = null;
	private Integer minNumDaysGroup = null;
	private Integer optNumDaysGroup = null;
	private Integer maxNumDaysGroup = null;

	// Teacher num days restriction
	private User numDaysTeacher = null;
	private Integer minNumDaysTeacher = null;
	private Integer optNumDaysTeacher = null;
	private Integer maxNumDaysTeacher = null;

	// Group idles restriction
	private Group idlesGroup = null;
	private Integer maxIdlesGroup = null;
	private Boolean multipleIdlesGroup = false;
	private Integer daysIdlesGroup = null;

	// Teacher idles restriction
	private User idlesTeacher = null;
	private Integer maxIdlesTeacher = null;
	private Boolean multipleIdlesTeacher = false;
	private Integer daysIdlesTeacher = null;

	// Group load restriction
	private Group loadGroup = null;
	private Integer maxLoadGroup = null;
	private Integer minLoadGroup = null;

	// Teacher load restriction
	private User loadTeacher = null;
	private Integer maxLoadTeacher = null;
	private Integer minLoadTeacher = null;

	public List<Group> listAllGroups() {
		return groupsController.listGroups();
	}

	public List<User> listAllTeachers() {
		return usersController.listUsers();
	}

	public void resetNumDaysFields(boolean group) {
		if (group) {
			numDaysGroup = null;
			minNumDaysGroup = null;
			optNumDaysGroup = null;
			maxNumDaysGroup = null;
		} else {
			numDaysTeacher = null;
			minNumDaysTeacher = null;
			optNumDaysTeacher = null;
			maxNumDaysTeacher = null;
		}
	}

	public void resetIdlesFields(boolean group) {
		if (group) {
			idlesGroup = null;
			maxIdlesGroup = null;
			multipleIdlesGroup = false;
			daysIdlesGroup = null;
		} else {
			idlesTeacher = null;
			maxIdlesTeacher = null;
			multipleIdlesTeacher = false;
			daysIdlesTeacher = null;
		}
	}

	public void resetLoadFields(boolean group) {
		if (group) {
			loadGroup = null;
			maxLoadGroup = null;
			minLoadGroup = null;
		} else {
			loadTeacher = null;
			maxLoadTeacher = null;
			minLoadTeacher = null;
		}
	}

	public void addNewNumDaysGroupRestriction() {
		if (numDaysGroup != null) {
			GroupNumDays newGroupNumDays = new GroupNumDays();
			newGroupNumDays.setGroup(numDaysGroup);
			newGroupNumDays.setMin(minNumDaysGroup);
			newGroupNumDays.setOpt(optNumDaysGroup);
			newGroupNumDays.setMax(maxNumDaysGroup);
			restrictionsService.addNewNumDaysGroupRestriction(newGroupNumDays);
			resetNumDaysFields(true);
		}
	}

	public void addNewNumDaysTeacherRestriction() {
		if (numDaysTeacher != null) {
			TeacherNumDays newTeacherNumDays = new TeacherNumDays();
			newTeacherNumDays.setTeacher(numDaysTeacher);
			newTeacherNumDays.setMin(minNumDaysTeacher);
			newTeacherNumDays.setOpt(optNumDaysTeacher);
			newTeacherNumDays.setMax(maxNumDaysTeacher);
			restrictionsService
					.addNewNumDaysTeacherRestriction(newTeacherNumDays);
			resetNumDaysFields(false);
		}
	}

	public void addIdlesGroupRestriction() {
		if (idlesGroup != null) {
			GroupIdles groupIdles = new GroupIdles();
			groupIdles.setGroup(idlesGroup);
			groupIdles.setMax(maxIdlesGroup);
			groupIdles.setMultiple(multipleIdlesGroup);
			groupIdles.setDays(daysIdlesGroup);
			restrictionsService.addIdlesGroupRestriction(groupIdles);
			resetIdlesFields(true);
		}
	}

	public void addIdlesTeacherRestriction() {
		if (idlesTeacher != null) {
			TeacherIdles teacherIdles = new TeacherIdles();
			teacherIdles.setTeacher(idlesTeacher);
			teacherIdles.setMax(maxIdlesTeacher);
			teacherIdles.setMultiple(multipleIdlesTeacher);
			teacherIdles.setDays(daysIdlesTeacher);
			restrictionsService.addIdlesTeacherRestriction(teacherIdles);
			resetIdlesFields(false);
		}
	}

	public void addLoadGroupRestriction() {
		if (loadGroup != null) {
			GroupLoad groupLoad = new GroupLoad();
			groupLoad.setGroup(loadGroup);
			groupLoad.setMin(minLoadGroup);
			groupLoad.setMax(maxLoadGroup);
			restrictionsService.addLoadGroupRestriction(groupLoad);
			resetLoadFields(true);
		}
	}

	public void addLoadTeacherRestriction() {
		if (loadTeacher != null) {
			TeacherLoad teacherLoad = new TeacherLoad();
			teacherLoad.setTeacher(loadTeacher);
			teacherLoad.setMin(minLoadTeacher);
			teacherLoad.setMax(maxLoadTeacher);
			restrictionsService.addLoadTeacherRestriction(teacherLoad);
			resetLoadFields(false);
		}
	}

	/*
	 * Getters and setters
	 */
	public Group getNumDaysGroup() {
		return numDaysGroup;
	}

	public void setNumDaysGroup(Group numDaysGroup) {
		this.numDaysGroup = numDaysGroup;
	}

	public void setGroupsController(GroupsController groupsController) {
		this.groupsController = groupsController;
	}

	public Integer getMinNumDaysGroup() {
		return minNumDaysGroup;
	}

	public void setMinNumDaysGroup(Integer minNumDaysGroup) {
		this.minNumDaysGroup = minNumDaysGroup;
	}

	public Integer getOptNumDaysGroup() {
		return optNumDaysGroup;
	}

	public void setOptNumDaysGroup(Integer optNumDaysGroup) {
		this.optNumDaysGroup = optNumDaysGroup;
	}

	public Integer getMaxNumDaysGroup() {
		return maxNumDaysGroup;
	}

	public void setMaxNumDaysGroup(Integer maxNumDaysGroup) {
		this.maxNumDaysGroup = maxNumDaysGroup;
	}

	public User getNumDaysTeacher() {
		return numDaysTeacher;
	}

	public void setNumDaysTeacher(User numDaysTeacher) {
		this.numDaysTeacher = numDaysTeacher;
	}

	public Integer getMinNumDaysTeacher() {
		return minNumDaysTeacher;
	}

	public void setMinNumDaysTeacher(Integer minNumDaysTeacher) {
		this.minNumDaysTeacher = minNumDaysTeacher;
	}

	public Integer getOptNumDaysTeacher() {
		return optNumDaysTeacher;
	}

	public void setOptNumDaysTeacher(Integer optNumDaysTeacher) {
		this.optNumDaysTeacher = optNumDaysTeacher;
	}

	public Integer getMaxNumDaysTeacher() {
		return maxNumDaysTeacher;
	}

	public void setMaxNumDaysTeacher(Integer maxNumDaysTeacher) {
		this.maxNumDaysTeacher = maxNumDaysTeacher;
	}

	public void setUsersController(UsersController usersController) {
		this.usersController = usersController;
	}

	public Group getIdlesGroup() {
		return idlesGroup;
	}

	public void setIdlesGroup(Group idlesGroup) {
		this.idlesGroup = idlesGroup;
	}

	public Integer getMaxIdlesGroup() {
		return maxIdlesGroup;
	}

	public void setMaxIdlesGroup(Integer maxIdlesGroup) {
		this.maxIdlesGroup = maxIdlesGroup;
	}

	public Boolean getMultipleIdlesGroup() {
		return multipleIdlesGroup;
	}

	public void setMultipleIdlesGroup(Boolean multipleIdlesGroup) {
		this.multipleIdlesGroup = multipleIdlesGroup;
	}

	public Integer getDaysIdlesGroup() {
		return daysIdlesGroup;
	}

	public void setDaysIdlesGroup(Integer daysIdlesGroup) {
		this.daysIdlesGroup = daysIdlesGroup;
	}

	public User getIdlesTeacher() {
		return idlesTeacher;
	}

	public void setIdlesTeacher(User idlesTeacher) {
		this.idlesTeacher = idlesTeacher;
	}

	public Integer getMaxIdlesTeacher() {
		return maxIdlesTeacher;
	}

	public void setMaxIdlesTeacher(Integer maxIdlesTeacher) {
		this.maxIdlesTeacher = maxIdlesTeacher;
	}

	public Boolean getMultipleIdlesTeacher() {
		return multipleIdlesTeacher;
	}

	public void setMultipleIdlesTeacher(Boolean multipleIdlesTeacher) {
		this.multipleIdlesTeacher = multipleIdlesTeacher;
	}

	public Integer getDaysIdlesTeacher() {
		return daysIdlesTeacher;
	}

	public void setDaysIdlesTeacher(Integer daysIdlesTeacher) {
		this.daysIdlesTeacher = daysIdlesTeacher;
	}

	public Group getLoadGroup() {
		return loadGroup;
	}

	public void setLoadGroup(Group loadGroup) {
		this.loadGroup = loadGroup;
	}

	public Integer getMaxLoadGroup() {
		return maxLoadGroup;
	}

	public void setMaxLoadGroup(Integer maxLoadGroup) {
		this.maxLoadGroup = maxLoadGroup;
	}

	public Integer getMinLoadGroup() {
		return minLoadGroup;
	}

	public void setMinLoadGroup(Integer minLoadGroup) {
		this.minLoadGroup = minLoadGroup;
	}

	public User getLoadTeacher() {
		return loadTeacher;
	}

	public void setLoadTeacher(User loadTeacher) {
		this.loadTeacher = loadTeacher;
	}

	public Integer getMaxLoadTeacher() {
		return maxLoadTeacher;
	}

	public void setMaxLoadTeacher(Integer maxLoadTeacher) {
		this.maxLoadTeacher = maxLoadTeacher;
	}

	public Integer getMinLoadTeacher() {
		return minLoadTeacher;
	}

	public void setMinLoadTeacher(Integer minLoadTeacher) {
		this.minLoadTeacher = minLoadTeacher;
	}
}
