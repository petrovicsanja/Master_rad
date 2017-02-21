package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RadniDaniGrupe")
public class GroupNumDays {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idRadniDaniGrupe;

	@OneToOne
	@JoinColumn(name = "idGrupe")
	private Group group;

	private Integer min;

	private Integer opt;

	private Integer max;

	public Long getIdRadniDaniGrupe() {
		return idRadniDaniGrupe;
	}

	public void setIdRadniDaniGrupe(Long idRadniDaniGrupe) {
		this.idRadniDaniGrupe = idRadniDaniGrupe;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getOpt() {
		return opt;
	}

	public void setOpt(Integer opt) {
		this.opt = opt;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}
}
