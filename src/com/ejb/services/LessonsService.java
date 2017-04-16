package com.ejb.services;

import java.util.List;
import java.util.Set;

import com.jpa.entities.Group;
import com.jpa.entities.Lesson;
import com.jpa.entities.Room;
import com.jpa.entities.Semester;
import com.jpa.entities.Subject;
import com.jpa.entities.User;

public interface LessonsService {

	void addLesson(Set<User> teachers, Set<Group> groups, Subject subject, String terms, Set<Room> rooms, String note,
			Semester activeSemester);

	List<Lesson> listLessons(Long subjectId, Long semesterId);

}
