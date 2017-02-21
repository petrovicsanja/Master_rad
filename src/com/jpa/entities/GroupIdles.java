package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GroupIdles")
public class GroupIdles {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long groupIdlesId;

	@OneToOne
	@JoinColumn(name = "groupId")
	private Group group;

	private Integer max;

	private Boolean multiple;

	private Integer days;

	public Long getGroupIdlesId() {
		return groupIdlesId;
	}

	public void setGroupIdlesId(Long groupIdlesId) {
		this.groupIdlesId = groupIdlesId;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Boolean getMultiple() {
		return multiple;
	}

	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
}
