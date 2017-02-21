package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Smerovi")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idSmera;

	@NotNull
	private String nazivSmera;

	public Long getIdSmera() {
		return idSmera;
	}

	public void setIdSmera(Long idSmera) {
		this.idSmera = idSmera;
	}

	public String getNazivSmera() {
		return nazivSmera;
	}

	public void setNazivSmera(String nazivSmera) {
		this.nazivSmera = nazivSmera;
	}
}
