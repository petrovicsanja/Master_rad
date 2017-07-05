package com.ejb.services;

import java.util.List;

import com.jpa.entities.User;

/**
 * @author Sanja
 *
 *         Interfejs koji sluzi za definisanje skupa metoda koje su dostupne
 *         klijentima tzv. business interface
 */
public interface UsersService {
	/**
	 * Pretraga korisnika u bazi na osnovu korisnickog imena i sifre
	 * 
	 * @return User Nadjeni korisnik ili null ukoliko korisnik nije pronadjen
	 */
	User findUser(String username, String password);

	/**
	 * Dodavanje novog korisnika u bazu
	 */
	void addUser(User user);

	/**
	 * Izlistavanje svih korisnika iz baze
	 * 
	 * @return List<User> Lista korisnika
	 */
	List<User> listUsers();

	/**
	 * Pretraga korisnika u bazi na osnovu prosledjenog id-a
	 * 
	 * @return User
	 */
	User findUserById(Long userId);

	/**
	 * Brisanje korisnika iz baze na osnovu prosledjenog id-a
	 */
	User deleteUser(Long userId);

	/**
	 * Izmena podataka vec postojeceg korisnika u bazi
	 */
	void updateUser(User user);
}
