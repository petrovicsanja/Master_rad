package com.ejb.services;

import java.util.List;

import com.jpa.entities.Department;
import com.jpa.entities.Group;

public interface GroupsService {

	/**
	 * Add new group
	 * 
	 * @param Group group
	 */
	public void addGroup(Group group);

	/**
	 * List all departments ordered by name
	 * 
	 * @return List<Department>
	 */
	public List<Department> listDepartments();

	/**
	 * List all groups ordered by name
	 * 
	 * @return List<Group>
	 */
	public List<Group> listGroups();

	/**
	 * Delete group
	 * 
	 * @param Long groupId
	 */
	public void deleteGroup(Long groupId);

	/**
	 * Update group
	 * 
	 * @param Group group
	 */
	public void updateGroup(Group group);
}
