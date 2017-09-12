package com.gui.controllers;

import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.richfaces.component.SortOrder;
import org.richfaces.model.Filter;

import com.ejb.services.UsersService;
import com.jpa.entities.User;

@ManagedBean
@ViewScoped
public class UsersController {

	@EJB
	private UsersService usersService;

	private User newUser = new User();
	private List<SelectItem> userSelectItems = null;
	private List<User> userList = null;
	private int selectedUserIndex;
	private User selectedUser;

	/*
	 * Filtering
	 */
	private String nameFilter;
	private String surnameFilter;

	/*
	 * Sort order for columns
	 */
	private SortOrder nameOrder = SortOrder.unsorted;
	private SortOrder surnameOrder = SortOrder.unsorted;
	private SortOrder usernameOrder = SortOrder.unsorted;
	private SortOrder userTypeOrder = SortOrder.unsorted;

	public void addUser() {
		usersService.addUser(this.newUser);
		newUser = new User();
	}

	/**
	 * @return List<User> List of all users
	 */
	public List<User> listUsers() {
		userList = usersService.listUsers();
		return userList;
	}

	public void deleteUser() {
		User userToDelete = userList.get(selectedUserIndex);
		usersService.deleteUser(userToDelete.getId());
	}

	public void updateUser() {
		usersService.updateUser(selectedUser);
	}

	public User findUserById(Long userId) {
		for (User user : userList) {
			if (user.getId().equals(userId)) {
				return user;
			}
		}
		return null;
	}

	/* Filtering of columns */

	public Filter<User> getFilterNameImpl() {
		return new Filter<User>() {
			public boolean accept(User user) {
				String firstName = getNameFilter();
				if (firstName == null || firstName.length() == 0 || firstName.equals(user.getFirstName())) {
					return true;
				}
				return false;
			}
		};
	}

	public Filter<User> getFilterSurnameImpl() {
		return new Filter<User>() {
			public boolean accept(User user) {
				String lastName = getNameFilter();
				if (lastName == null || lastName.length() == 0 || lastName.equals(user.getLastName())) {
					return true;
				}
				return false;
			}
		};
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

	public String getNameFilter() {
		return nameFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	public String getSurnameFilter() {
		return surnameFilter;
	}

	public void setSurnameFilter(String surnameFilter) {
		this.surnameFilter = surnameFilter;
	}
}
