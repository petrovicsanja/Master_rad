package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Predmeti")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idPredmeta;

	@NotNull
	private String nazivPredmeta;

	@NotNull
	private String oznakaPredmeta;

	public Long getIdPredmeta() {
		return idPredmeta;
	}

	public void setIdPredmeta(Long idPredmeta) {
		this.idPredmeta = idPredmeta;
	}

	public String getNazivPredmeta() {
		return nazivPredmeta;
	}

	public void setNazivPredmeta(String nazivPredmeta) {
		this.nazivPredmeta = nazivPredmeta;
	}

	public String getOznakaPredmeta() {
		return oznakaPredmeta;
	}

	public void setOznakaPredmeta(String oznakaPredmeta) {
		this.oznakaPredmeta = oznakaPredmeta;
	}

	@Override
	public String toString() {
		return nazivPredmeta;
	}
}
