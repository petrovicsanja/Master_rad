package com.gui.controllers;

import javax.ejb.EJB;
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

	private String activeTab = "periods";

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
			context.getExternalContext().getSessionMap().put("user", user);
			this.message = null;
		} else {
			System.out.println("Korisnik nije pronadjen");
			this.username = null;
			this.password = null;
			this.message = "Neuspešno logovanje. Molimo pokušajte ponovo.";
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

	public boolean isAdmin() {
		return user.getType().equals(2);
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

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String poruka) {
		this.message = poruka;
	}

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}
}
