package com.gui.controllers;

import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.richfaces.component.SortOrder;

import com.ejb.services.GroupsService;
import com.jpa.entities.Department;
import com.jpa.entities.Group;

@ManagedBean
@SessionScoped
public class GroupsController {

	@EJB
	private GroupsService groupsService;

	@ManagedProperty(value = "#{departmentController}")
	private DepartmentController departmentController;

	private Group newGroup = new Group();
	private List<Group> groupList = null;
	private int selectedGroupIndex;
	private Group groupToUpdate;

	/**
	 * Sort order of columns
	 */
	private SortOrder nameOrder = SortOrder.unsorted;
	private SortOrder departmentOrder = SortOrder.unsorted;
	private SortOrder sizeOrder = SortOrder.unsorted;
	private SortOrder markOrder = SortOrder.unsorted;

	public void addGroup() {
		groupsService.addGroup(newGroup);
		groupList.add(newGroup);
		newGroup = new Group();
	}

	public List<Department> listDepartments() {
		return departmentController.listDepartments();
	}

	public List<Group> listGroups() {
		if (groupList == null) {
			groupList = groupsService.listGroups();
		}
		return groupList;
	}

	public void deleteGroup() {
		Group groupToDelete = groupList.get(selectedGroupIndex);
		groupsService.deleteGroup(groupToDelete.getId());
		groupList.remove(groupToDelete);
	}

	public void updateGroup() {
		groupsService.updateGroup(groupToUpdate);
		groupList.set(selectedGroupIndex, groupToUpdate);
	}

	public Group findGroupById(Long groupId) {
		for (Group group : groupList) {
			if (group.getId().equals(groupId)) {
				return group;
			}
		}
		return null;
	}

	/* Sorting of columns */

	public void sortByName() {
		departmentOrder = SortOrder.unsorted;
		sizeOrder = SortOrder.unsorted;
		markOrder = SortOrder.unsorted;

		if (nameOrder.equals(SortOrder.ascending)) {
			setNameOrder(SortOrder.descending);
		} else {
			setNameOrder(SortOrder.ascending);
		}
	}

	public void sortByDepartment() {
		nameOrder = SortOrder.unsorted;
		sizeOrder = SortOrder.unsorted;
		markOrder = SortOrder.unsorted;

		if (departmentOrder.equals(SortOrder.ascending)) {
			setDepartmentOrder(SortOrder.descending);
		} else {
			setDepartmentOrder(SortOrder.ascending);
		}
	}

	public void sortBySize() {
		nameOrder = SortOrder.unsorted;
		departmentOrder = SortOrder.unsorted;
		markOrder = SortOrder.unsorted;

		if (sizeOrder.equals(SortOrder.ascending)) {
			setSizeOrder(SortOrder.descending);
		} else {
			setSizeOrder(SortOrder.ascending);
		}
	}

	public void sortByMark() {
		nameOrder = SortOrder.unsorted;
		departmentOrder = SortOrder.unsorted;
		sizeOrder = SortOrder.unsorted;

		if (markOrder.equals(SortOrder.ascending)) {
			setMarkOrder(SortOrder.descending);
		} else {
			setMarkOrder(SortOrder.ascending);
		}
	}

	public Comparator<Group> getDeapartmentComparator() {
		return new Comparator<Group>() {
			public int compare(Group g1, Group g2) {
				String g1Department = g1.getDepartment().getName();
				String g2Department = g2.getDepartment().getName();

				return g1Department.compareToIgnoreCase(g2Department);
			}
		};
	}

	/* Getters and setters */

	public Group getNewGroup() {
		return newGroup;
	}

	public void setNewGroup(Group newGroup) {
		this.newGroup = newGroup;
	}

	public SortOrder getNameOrder() {
		return nameOrder;
	}

	public void setNameOrder(SortOrder nameOrder) {
		this.nameOrder = nameOrder;
	}

	public SortOrder getDepartmentOrder() {
		return departmentOrder;
	}

	public void setDepartmentOrder(SortOrder departmentOrder) {
		this.departmentOrder = departmentOrder;
	}

	public SortOrder getSizeOrder() {
		return sizeOrder;
	}

	public void setSizeOrder(SortOrder sizeOrder) {
		this.sizeOrder = sizeOrder;
	}

	public SortOrder getMarkOrder() {
		return markOrder;
	}

	public void setMarkOrder(SortOrder markOrder) {
		this.markOrder = markOrder;
	}

	public int getSelectedGroupIndex() {
		return selectedGroupIndex;
	}

	public void setSelectedGroupIndex(int selectedGroupIndex) {
		this.selectedGroupIndex = selectedGroupIndex;
	}

	public Group getGroupToUpdate() {
		return groupToUpdate;
	}

	public void setGroupToUpdate(Group groupToUpdate) {
		this.groupToUpdate = groupToUpdate;
	}

	public void setDepartmentController(DepartmentController departmentController) {
		this.departmentController = departmentController;
	}
}
