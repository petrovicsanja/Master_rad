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
@Table(name = "Lesson")
public class Lesson {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "subjectId")
	private Subject subject;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "LessonTeacher", joinColumns = @JoinColumn(name = "lessonId"), inverseJoinColumns = @JoinColumn(name = "teacherId"))
	private Set<User> teachers;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "LessonGroup", joinColumns = @JoinColumn(name = "lessonId"), inverseJoinColumns = @JoinColumn(name = "groupId"))
	private Set<Group> groups;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "LessonRoom", joinColumns = @JoinColumn(name = "lessonId"), inverseJoinColumns = @JoinColumn(name = "roomId"))
	private Set<Room> rooms;

	@NotNull
	private String terms;

	private String note;

	@NotNull
	private Integer semester;

	@NotNull
	private String year;

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

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}
