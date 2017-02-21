package com.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Grupe")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idGrupe;

	@NotNull
	@Column(name = "NazivGrupe")
	private String naziv;

	@ManyToOne
	@JoinColumn(name = "idSmera")
	@NotNull
	private Department smer;

	private Integer velicina;

	@NotNull
	@Column(name = "OznakaGrupe")
	private String oznaka;

	public Long getIdGrupe() {
		return idGrupe;
	}

	public void setIdGrupe(Long idGrupe) {
		this.idGrupe = idGrupe;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Department getSmer() {
		return smer;
	}

	public void setSmer(Department smer) {
		this.smer = smer;
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
	
	@Override
	public String toString() {
		return this.naziv;
	}
}
