package com.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RoomAvailability")
public class RoomAvailability {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long roomAvailabilityId;

	@ManyToOne
	@JoinColumn(name = "roomId")
	private Room room;

	private String type;

	private String dayMark;

	private Integer termNumber;

	private String year;

	private Integer semester;

	public Long getRoomAvailabilityId() {
		return roomAvailabilityId;
	}

	public void setRoomAvailabilityId(Long roomAvailabilityId) {
		this.roomAvailabilityId = roomAvailabilityId;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDayMark() {
		return dayMark;
	}

	public void setDayMark(String dayMark) {
		this.dayMark = dayMark;
	}

	public Integer getTermNumber() {
		return termNumber;
	}

	public void setTermNumber(Integer termNumber) {
		this.termNumber = termNumber;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}
}
