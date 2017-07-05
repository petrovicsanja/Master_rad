package com.ejb.services;

import java.util.List;

import com.jpa.entities.User;

/**
 * @author Sanja
 *
 *         Business interface - It is used for defining the set of methods that
 *         are available to client
 */
public interface UsersService {
	/**
	 * Search for user by username and password
	 * 
	 * @return Found user or null
	 */
	User findUser(String username, String password);

	/**
	 * Adding new user to the database
	 * 
	 * @param user
	 */
	void addUser(User user);

	/**
	 * Listing all users
	 * 
	 * @return List<User> Lista korisnika
	 */
	List<User> listUsers();

	/**
	 * Search for user by id
	 * 
	 * @return User
	 */
	User findUserById(Long userId);

	/**
	 * Delete existing user by id
	 */
	User deleteUser(Long userId);

	/**
	 * Updating existing user
	 * 
	 * @param user
	 */
	void updateUser(User user);
}
