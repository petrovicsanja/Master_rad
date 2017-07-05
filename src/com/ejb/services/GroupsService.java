package com.ejb.services;

import java.util.List;

import com.jpa.entities.Group;

/**
 * Business interface - It is used for defining the set of methods that are
 * available to client
 * 
 * @author sanja
 *
 */

public interface GroupsService {

	/**
	 * Add new group
	 * 
	 * @param Group
	 *            group
	 */
	public void addGroup(Group group);

	/**
	 * List all groups ordered by name
	 * 
	 * @return List<Group>
	 */
	public List<Group> listGroups();

	/**
	 * Delete group
	 * 
	 * @param Long
	 *            groupId
	 */
	public void deleteGroup(Long groupId);

	/**
	 * Update group
	 * 
	 * @param Group
	 *            group
	 */
	public void updateGroup(Group group);
}
