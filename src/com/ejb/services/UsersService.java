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
	public User findUser(String username, String password);

	/**
	 * Dodavanje novog korisnika u bazu
	 */
	public void addUser(User user);

	/**
	 * Izlistavanje svih korisnika iz baze
	 * 
	 * @return List<User> Lista korisnika
	 */
	public List<User> listUsers();

	/**
	 * Pretraga korisnika u bazi na osnovu prosledjenog id-a
	 * 
	 * @return User
	 */
	public User findUserById(Long userId);

	/**
	 * Brisanje korisnika iz baze na osnovu prosledjenog id-a
	 */
	public void deleteUser(Long userId);

	/**
	 * Izmena podataka vec postojeceg korisnika u bazi
	 */
	public void updateUser(User user);
}
