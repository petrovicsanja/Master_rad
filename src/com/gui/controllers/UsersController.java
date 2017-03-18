package com.gui.controllers;

import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.richfaces.component.SortOrder;

import com.ejb.services.UsersService;
import com.jpa.entities.User;

@ManagedBean
@SessionScoped
public class UsersController {

	@EJB
	private UsersService usersService;

	private User newUser = new User();
	private List<SelectItem> userSelectItems = null;
	private List<User> userList = null;
	private int selectedUserIndex;
	private User selectedUser;

	/*
	 * Sort order for columns
	 */
	private SortOrder nameOrder = SortOrder.unsorted;
	private SortOrder surnameOrder = SortOrder.unsorted;
	private SortOrder usernameOrder = SortOrder.unsorted;
	private SortOrder userTypeOrder = SortOrder.unsorted;

	public void addUser() {
		usersService.addUser(this.newUser);
		userList.add(newUser);
		newUser = new User();
	}

	/**
	 * @return List<User> List of all users
	 */
	public List<User> listUsers() {
		if (userList == null) {
			userList = usersService.listUsers();
		}
		return userList;
	}

	public void deleteUser() {
		User userToDelete = userList.get(selectedUserIndex);
		usersService.deleteUser(userToDelete.getId());
		userList.remove(userToDelete);
	}

	public void updateUser() {
		usersService.updateUser(selectedUser);
		userList.set(selectedUserIndex, selectedUser);
	}

	public User findUserById(Long userId) {
		for (User user : userList) {
			if (user.getId().equals(userId)) {
				return user;
			}
		}
		return null;
	}

	/* Sorting of columns */

	public void sortByName() {
		surnameOrder = SortOrder.unsorted;
		usernameOrder = SortOrder.unsorted;
		userTypeOrder = SortOrder.unsorted;

		if (nameOrder.equals(SortOrder.ascending)) {
			setNameOrder(SortOrder.descending);
		} else {
			setNameOrder(SortOrder.ascending);
		}
	}

	public void sortBySurname() {
		nameOrder = SortOrder.unsorted;
		usernameOrder = SortOrder.unsorted;
		userTypeOrder = SortOrder.unsorted;

		if (surnameOrder.equals(SortOrder.ascending)) {
			setSurnameOrder(SortOrder.descending);
		} else {
			setSurnameOrder(SortOrder.ascending);
		}
	}

	public void sortByUsername() {
		nameOrder = SortOrder.unsorted;
		surnameOrder = SortOrder.unsorted;
		userTypeOrder = SortOrder.unsorted;

		if (usernameOrder.equals(SortOrder.ascending)) {
			setUsernameOrder(SortOrder.descending);
		} else {
			setUsernameOrder(SortOrder.ascending);
		}
	}

	public void sortByUserType() {
		nameOrder = SortOrder.unsorted;
		surnameOrder = SortOrder.unsorted;
		usernameOrder = SortOrder.unsorted;

		if (userTypeOrder.equals(SortOrder.ascending)) {
			setUserTypeOrder(SortOrder.descending);
		} else {
			setUserTypeOrder(SortOrder.ascending);
		}
	}

	public Comparator<User> getUserTypeComparator() {
		return new Comparator<User>() {
			public int compare(User k1, User k2) {
				int k1UserType = k1.getType();
				int k2UserType = k2.getType();

				if (k1UserType == k2UserType) {
					return 0;
				} else if (k1UserType > k2UserType) {
					return -1;
				} else {
					return 1;
				}
			}
		};
	}

	/* Getters and setters */

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public List<SelectItem> getUserSelectItems() {
		return userSelectItems;
	}

	public void setUserSelectItems(List<SelectItem> userSelectItems) {
		this.userSelectItems = userSelectItems;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public int getSelectedUserIndex() {
		return selectedUserIndex;
	}

	public void setSelectedUserIndex(int selectedUserIndex) {
		this.selectedUserIndex = selectedUserIndex;
	}

	public SortOrder getNameOrder() {
		return nameOrder;
	}

	public void setNameOrder(SortOrder nameOrder) {
		this.nameOrder = nameOrder;
	}

	public SortOrder getSurnameOrder() {
		return surnameOrder;
	}

	public void setSurnameOrder(SortOrder surnameOrder) {
		this.surnameOrder = surnameOrder;
	}

	public SortOrder getUsernameOrder() {
		return usernameOrder;
	}

	public void setUsernameOrder(SortOrder usernameOrder) {
		this.usernameOrder = usernameOrder;
	}

	public SortOrder getUserTypeOrder() {
		return userTypeOrder;
	}

	public void setUserTypeOrder(SortOrder userTypeOrder) {
		this.userTypeOrder = userTypeOrder;
	}
}
