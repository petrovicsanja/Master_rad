package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "korisnik")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKorisnika;

	@NotNull
	@Size(min = 3, max = 20)
	private String korisnickoIme;

	@NotNull
	@Size(min = 3, max = 20)
	private String lozinka;

	@NotNull
	private Integer tipKorisnika;

	@NotNull
	private String ime;

	@NotNull
	private String prezime;

	public Long getIdKorisnika() {
		return idKorisnika;
	}

	public void setIdKorisnika(Long idKorisnika) {
		this.idKorisnika = idKorisnika;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public Integer getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(Integer tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	@Override
	public String toString() {
		return this.ime + " " + this.prezime;
	}
}
