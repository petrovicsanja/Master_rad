package com.ejb.services.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.TimetableService;
import com.jpa.entities.Group;
import com.jpa.entities.GroupIdles;
import com.jpa.entities.GroupLoad;
import com.jpa.entities.GroupNumDays;
import com.jpa.entities.Period;
import com.jpa.entities.Room;
import com.jpa.entities.Semester;
import com.jpa.entities.Subject;
import com.jpa.entities.TeacherIdles;
import com.jpa.entities.TeacherLoad;
import com.jpa.entities.TeacherNumDays;
import com.jpa.entities.User;

@Stateless
public class TimetableServiceImpl implements TimetableService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	final Charset ENCODING = StandardCharsets.UTF_8;

	@Override
	public void createTimetable(Semester activeSemester) {
		Integer ordinalNumberOfSemester = activeSemester.getOrdinalNumber();
		String schoolYear = activeSemester.getSchoolYear();

		System.out.println("Writing timetable data to appropriate file...");

		String fileName = "timetable.tts";
		Path path = Paths.get(fileName);

		try (BufferedWriter timetableData = Files.newBufferedWriter(path, ENCODING, StandardOpenOption.APPEND,
				StandardOpenOption.CREATE)) {
			timetableData.append(getPeriods());
			timetableData.append(getTeachers());
			timetableData.append(getGroups());
			timetableData.append(getSubjects());
			timetableData.append(getRooms());
			timetableData.append(getNumDays(ordinalNumberOfSemester, schoolYear));
			timetableData.append(getIdles(ordinalNumberOfSemester, schoolYear));
			timetableData.append(getLoads(ordinalNumberOfSemester, schoolYear));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done!");
	}

	private StringBuffer getPeriods() {
		StringBuffer periods = new StringBuffer("[periods]");
		periods.append("\n");

		TypedQuery<Period> periodList = em.createQuery("SELECT p FROM Period p WHERE termsNumber <> 0", Period.class);

		for (Period period : periodList.getResultList()) {
			periods.append(period.getDayMark());
			periods.append("\t");
			periods.append(period.getDay());
			periods.append("\t");
			for (int i = 1; i <= period.getTermsNumber(); i++) {
				periods.append(i);
				if (i < period.getTermsNumber()) {
					periods.append(",");
				}
			}
			periods.append("\n");
		}

		periods.append("\n");

		return periods;
	}

	private StringBuffer getTeachers() {
		StringBuffer teachers = new StringBuffer("[teachers]");
		teachers.append("\n");

		TypedQuery<User> teacherList = em
				.createQuery("SELECT u FROM User u WHERE u.tipKorisnika = 1 ORDER BY u.idKorisnika", User.class);

		for (User teacher : teacherList.getResultList()) {
			teachers.append(teacher.getIdKorisnika());
			teachers.append("\t");
			teachers.append(teacher.toString());
			teachers.append("\n");
		}

		teachers.append("\n");

		return teachers;
	}

	private StringBuffer getGroups() {
		StringBuffer groups = new StringBuffer("[groups]");
		groups.append("\n");

		TypedQuery<Group> groupList = em.createQuery("SELECT g FROM Group g ORDER BY g.idGrupe", Group.class);

		for (Group group : groupList.getResultList()) {
			groups.append(group.getIdGrupe());
			groups.append("\t");
			groups.append(group.getNaziv());
			groups.append("\n");
		}

		groups.append("\n");

		return groups;
	}

	private StringBuffer getSubjects() {
		StringBuffer subjects = new StringBuffer("[subjects]");
		subjects.append("\n");

		TypedQuery<Subject> subjectList = em.createQuery("SELECT s FROM Subject s ORDER BY s.idPredmeta",
				Subject.class);

		for (Subject subject : subjectList.getResultList()) {
			subjects.append(subject.getIdPredmeta());
			subjects.append("\t");
			subjects.append(subject.getNazivPredmeta());
			subjects.append("\n");
		}

		subjects.append("\n");

		return subjects;
	}

	private StringBuffer getRooms() {
		StringBuffer rooms = new StringBuffer("[rooms]");
		rooms.append("\n");

		TypedQuery<Room> roomList = em.createQuery("SELECT r FROM Room r ORDER BY r.idUcionice", Room.class);

		for (Room room : roomList.getResultList()) {
			rooms.append(room.getIdUcionice());
			rooms.append("\t");
			rooms.append(room.getNaziv());
			rooms.append("\n");
		}

		rooms.append("\n");

		return rooms;
	}

	private StringBuffer getNumDays(Integer ordinalNumberOfSemester, String schoolYear) {
		StringBuffer numDays = new StringBuffer("[num_days]");
		numDays.append("\n");

		TypedQuery<TeacherNumDays> teacherNumDayList = em.createQuery(
				"SELECT n FROM TeacherNumDays n WHERE n.semester = :semester AND n.year = :schoolYear ORDER BY n.id",
				TeacherNumDays.class);
		teacherNumDayList.setParameter("semester", ordinalNumberOfSemester);
		teacherNumDayList.setParameter("schoolYear", schoolYear);

		for (TeacherNumDays teacherNumDays : teacherNumDayList.getResultList()) {
			numDays.append(teacherNumDays.getTeacher().getIdKorisnika());
			numDays.append("\t");
			numDays.append(teacherNumDays.getMin());
			numDays.append("\t");
			numDays.append(teacherNumDays.getOpt());
			numDays.append("\t");
			numDays.append(teacherNumDays.getMax());
			numDays.append("\n");
		}

		TypedQuery<GroupNumDays> groupNumDayList = em.createQuery(
				"SELECT n FROM GroupNumDays n WHERE n.semester = :semester AND n.year = :schoolYear ORDER BY n.id",
				GroupNumDays.class);
		groupNumDayList.setParameter("semester", ordinalNumberOfSemester);
		groupNumDayList.setParameter("schoolYear", schoolYear);

		for (GroupNumDays groupNumDays : groupNumDayList.getResultList()) {
			numDays.append(groupNumDays.getGroup().getIdGrupe());
			numDays.append("\t");
			numDays.append(groupNumDays.getMin());
			numDays.append("\t");
			numDays.append(groupNumDays.getOpt());
			numDays.append("\t");
			numDays.append(groupNumDays.getMax());
			numDays.append("\n");
		}

		numDays.append("\n");

		return numDays;
	}

	private StringBuffer getIdles(Integer ordinalNumberOfSemester, String schoolYear) {
		StringBuffer idles = new StringBuffer("[idles]");
		idles.append("\n");

		TypedQuery<TeacherIdles> teacherIdleList = em.createQuery(
				"SELECT i FROM TeacherIdles i WHERE i.semester = :semester AND i.year = :schoolYear ORDER BY i.id",
				TeacherIdles.class);
		teacherIdleList.setParameter("semester", ordinalNumberOfSemester);
		teacherIdleList.setParameter("schoolYear", schoolYear);

		for (TeacherIdles teacherIdles : teacherIdleList.getResultList()) {
			idles.append(teacherIdles.getTeacher().getIdKorisnika());
			idles.append("\t");
			idles.append(teacherIdles.getMax());
			idles.append("\t");
			idles.append(teacherIdles.getMultiple());
			idles.append("\t");
			idles.append(teacherIdles.getDays());
			idles.append("\n");
		}

		TypedQuery<GroupIdles> groupIdleList = em.createQuery(
				"SELECT i FROM GroupIdles i WHERE i.semester = :semester AND i.year = :schoolYear ORDER BY i.id",
				GroupIdles.class);
		groupIdleList.setParameter("semester", ordinalNumberOfSemester);
		groupIdleList.setParameter("schoolYear", schoolYear);

		for (GroupIdles groupIdles : groupIdleList.getResultList()) {
			idles.append(groupIdles.getGroup().getIdGrupe());
			idles.append("\t");
			idles.append(groupIdles.getMax());
			idles.append("\t");
			idles.append(groupIdles.getMultiple());
			idles.append("\t");
			idles.append(groupIdles.getDays());
			idles.append("\n");
		}

		idles.append("\n");

		return idles;
	}

	private StringBuffer getLoads(Integer ordinalNumberOfSemester, String schoolYear) {
		StringBuffer loads = new StringBuffer("[load]");
		loads.append("\n");

		TypedQuery<TeacherLoad> teacherLoadList = em.createQuery(
				"SELECT l FROM TeacherLoad l WHERE l.semester = :semester AND l.year = :schoolYear ORDER BY l.id",
				TeacherLoad.class);
		teacherLoadList.setParameter("semester", ordinalNumberOfSemester);
		teacherLoadList.setParameter("schoolYear", schoolYear);

		for (TeacherLoad teacherLoad : teacherLoadList.getResultList()) {
			loads.append(teacherLoad.getTeacher().getIdKorisnika());
			loads.append("\t");
			loads.append(teacherLoad.getMin());
			loads.append("\t");
			loads.append(teacherLoad.getMax());
			loads.append("\n");
		}

		TypedQuery<GroupLoad> groupLoadList = em.createQuery(
				"SELECT l FROM GroupLoad l WHERE l.semester = :semester AND l.year = :schoolYear ORDER BY l.id",
				GroupLoad.class);
		groupLoadList.setParameter("semester", ordinalNumberOfSemester);
		groupLoadList.setParameter("schoolYear", schoolYear);

		for (GroupLoad groupLoad : groupLoadList.getResultList()) {
			loads.append(groupLoad.getGroup().getIdGrupe());
			loads.append("\t");
			loads.append(groupLoad.getMin());
			loads.append("\t");
			loads.append(groupLoad.getMax());
			loads.append("\n");
		}

		loads.append("\n");

		return loads;
	}
}
