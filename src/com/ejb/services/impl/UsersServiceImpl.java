package com.ejb.services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.UsersService;
import com.jpa.entities.User;

@Stateless
public class UsersServiceImpl implements UsersService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	/**
	 * Pretraga korisnika u bazi na osnovu korisnickog imena i sifre
	 * 
	 * @return User Nadjeni korisnik ili null ukoliko korisnik nije pronadjen
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
	 * Dodavanje novog korisnika u bazu
	 */
	@Override
	public void addUser(User user) {
		em.persist(user);
		System.out.println("New user is successfully added to the database, id: " + user.getId());
	}

	/**
	 * Izlistavanje svih korisnika iz baze
	 * 
	 * @return List<User> Lista korisnika
	 */
	@Override
	public List<User> listUsers() {
		TypedQuery<User> userList = em.createQuery("SELECT u FROM User u ORDER BY u.firstName", User.class);
		return userList.getResultList();
	}

	/**
	 * Pretraga korisnika u bazi na osnovu prosledjenog id-a
	 * 
	 * @return User
	 */
	@Override
	public User findUserById(Long userId) {
		return em.find(User.class, userId);
	}

	/**
	 * Brisanje korisnika iz baze na osnovu prosledjenog id-a
	 */
	@Override
	public void deleteUser(Long userId) {
		User userToDelete = em.find(User.class, userId);
		em.remove(userToDelete);
		System.out.println("User is successfully deleted from the database, id: " + userId);
	}

	/**
	 * Izmena podataka vec postojeceg korisnika u bazi
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
