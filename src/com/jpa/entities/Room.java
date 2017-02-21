package com.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Ucionice")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idUcionice;

	@NotNull
	@Column(name = "NazivUcionice")
	private String naziv;

	@NotNull
	private Integer sprat;

	private String zgrada;

	private Integer velicina;

	@NotNull
	@Column(name = "OznakaUcionice")
	private String oznaka;

	@Override
	public String toString() {
		return this.naziv;
	}

	public Long getIdUcionice() {
		return idUcionice;
	}

	public void setIdUcionice(Long idUcionice) {
		this.idUcionice = idUcionice;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Integer getSprat() {
		return sprat;
	}

	public void setSprat(Integer sprat) {
		this.sprat = sprat;
	}

	public String getZgrada() {
		return zgrada;
	}

	public void setZgrada(String zgrada) {
		this.zgrada = zgrada;
	}

	public Integer getVelicina() {
		return velicina;
	}

	public void setVelicina(Integer velicina) {
		this.velicina = velicina;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}
}
