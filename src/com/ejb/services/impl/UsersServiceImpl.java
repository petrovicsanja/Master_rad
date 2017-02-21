package com.ejb.services.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.UsersService;
import com.jpa.entities.User;

@Stateful
public class UsersServiceImpl implements UsersService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	/**
	 * Pretraga korisnika u bazi na osnovu korisnickog imena i sifre
	 * 
	 * @return User Nadjeni korisnik ili null ukoliko korisnik nije pronadjen
	 */
	@Override
	public User findUser(String korisnickoIme, String lozinka) {
		TypedQuery<User> pronadjiKorisnika = em.createQuery(
				"SELECT u FROM User u WHERE u.korisnickoIme = :korisnickoIme AND u.lozinka = :lozinka", User.class);
		pronadjiKorisnika.setParameter("korisnickoIme", korisnickoIme);
		pronadjiKorisnika.setParameter("lozinka", lozinka);

		User korisnik;
		try {
			korisnik = pronadjiKorisnika.getSingleResult();
		} catch (NoResultException nre) {
			korisnik = null;
		}

		return korisnik;
	}

	/**
	 * Dodavanje novog korisnika u bazu
	 */
	@Override
	public void addUser(User korisnik) {
		em.persist(korisnik);
		System.out.println("Novi korisnik je uspesno dodat u bazu.");
	}

	/**
	 * Izlistavanje svih korisnika iz baze
	 * 
	 * @return List<User> Lista korisnika
	 */
	@Override
	public List<User> listUsers() {
		TypedQuery<User> izlistajKorisnike = em.createQuery("SELECT u FROM User u ORDER BY u.ime", User.class);
		return izlistajKorisnike.getResultList();
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
		System.out.println("Korisnik je uspesno obrisan iz baze.");
	}

	/**
	 * Izmena podataka vec postojeceg korisnika u bazi
	 */
	@Override
	public void updateUser(User user) {
		User userToUpdate = em.find(User.class, user.getIdKorisnika());
		userToUpdate.setIme(user.getIme());
		userToUpdate.setPrezime(user.getPrezime());
		userToUpdate.setTipKorisnika(user.getTipKorisnika());
		System.out.println("Podaci korisnika su uspesno izmenjeni.");
	}
}
