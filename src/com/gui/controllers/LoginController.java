package com.gui.controllers;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.ejb.services.UsersService;
import com.jpa.entities.User;

@ManagedBean
@SessionScoped
public class LoginController {

	@EJB
	private UsersService service;

	private User user;
	private String username;
	private String password;
	private String message = null;

	/**
	 * User login
	 * 
	 * @return String Name of the page to redirect user if login is successful
	 */
	public String login() {
		String action = null;
		this.user = service.findUser(username, password);

		FacesContext context = FacesContext.getCurrentInstance();

		if (user != null) {
			System.out.println("Korisnik je uspesno ulogovan.");
			action = "dataOverview?faces-redirect=true";
			context.getExternalContext().getSessionMap().put("korisnik", user);
			this.message = null;
		} else {
			System.out.println("Korisnik nije pronadjen");
			context.addMessage(null, new FacesMessage("Neuspesno logovanje. Molimo pokusajte ponovo."));
			this.username = null;
			this.password = null;
			this.message = context.getMessageList().get(0).toString();
		}
		return action;
	}

	/**
	 * User logout
	 * 
	 * @return String Name of the page to redirect user after logout
	 */
	public String logout() {
		System.out.println("Logout");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		this.user = null;
		return "index?faces-redirect=true";
	}

	public boolean isLoggedIn() {
		return user != null;
	}

	public int userType() {
		return user.getTipKorisnika();
	}

	/* Getters and setters */

	public User getUser() {
		return user;
	}

	public void setUser(User korisnik) {
		this.user = korisnik;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String korisnickoIme) {
		this.username = korisnickoIme;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String lozinka) {
		this.password = lozinka;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String poruka) {
		this.message = poruka;
	}
}
