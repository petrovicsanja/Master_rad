package com.ejb.services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.UsersService;
import com.jpa.entities.User;

/**
 * Implementation of services to work with user data
 * 
 * @author sanja
 *
 */

@Stateless
public class UsersServiceImpl implements UsersService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	/**
	 * User search by username and password
	 * 
	 * @return User Found user or null
	 */
	@Override
	public User findUser(String username, String password) {
		TypedQuery<User> userQuery = em.createQuery(
				"SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
		userQuery.setParameter("username", username);
		userQuery.setParameter("password", password);

		User user;
		try {
			user = userQuery.getSingleResult();
		} catch (NoResultException nre) {
			user = null;
		}

		return user;
	}

	/**
	 * Adding new user
	 */
	@Override
	public void addUser(User user) {
		em.persist(user);
		System.out.println("New user is successfully added to the database, id: " + user.getId());
	}

	/**
	 * Listing all users
	 * 
	 * @return List<User> List of users
	 */
	@Override
	public List<User> listUsers() {
		TypedQuery<User> userList = em.createQuery("SELECT u FROM User u ORDER BY u.firstName", User.class);
		return userList.getResultList();
	}

	/**
	 * User search by id
	 * 
	 * @return User
	 */
	@Override
	public User findUserById(Long userId) {
		return em.find(User.class, userId);
	}

	/**
	 * Removing user from the database selected by id
	 */
	@Override
	public User deleteUser(Long userId) {
		User userToDelete = em.find(User.class, userId);

		em.remove(userToDelete);
		System.out.println("User is successfully deleted from the database, id: " + userId);
		return userToDelete;
	}

	/**
	 * Updating user data
	 */
	@Override
	public void updateUser(User user) {
		User userToUpdate = em.find(User.class, user.getId());
		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setLastName(user.getLastName());
		userToUpdate.setType(user.getType());
		System.out.println("User is successfully updated in the database, id: " + user.getId());
	}
}
