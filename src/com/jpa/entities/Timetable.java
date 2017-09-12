package com.jpa.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Timetable")
public class Timetable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "subjectId")
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "roomId")
	private Room room;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "TimetableTeacher", joinColumns = @JoinColumn(name = "timetableId"), inverseJoinColumns = @JoinColumn(name = "teacherId"))
	private Set<User> teachers;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "TimetableGroup", joinColumns = @JoinColumn(name = "timetableId"), inverseJoinColumns = @JoinColumn(name = "groupId"))
	private Set<Group> groups;

	private Integer lessonLength;

	private String dayMark;

	private Integer startTerm;

	@ManyToOne
	@JoinColumn(name = "semesterId")
	@NotNull
	private Semester semester;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Set<User> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<User> teachers) {
		this.teachers = teachers;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Integer getLessonLength() {
		return lessonLength;
	}

	public void setLessonLength(Integer lessonLength) {
		this.lessonLength = lessonLength;
	}

	public String getDayMark() {
		return dayMark;
	}

	public void setDayMark(String dayMark) {
		this.dayMark = dayMark;
	}

	public Integer getStartTerm() {
		return startTerm;
	}

	public void setStartTerm(Integer startTerm) {
		this.startTerm = startTerm;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}
}
