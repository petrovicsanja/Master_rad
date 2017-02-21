package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GroupLoad")
public class GroupLoad {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long groupLoadId;

	@OneToOne
	@JoinColumn(name = "groupId")
	private Group group;

	private Integer max;

	private Integer min;

	public Long getGroupLoadId() {
		return groupLoadId;
	}

	public void setGroupLoadId(Long groupLoadId) {
		this.groupLoadId = groupLoadId;
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

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

}
