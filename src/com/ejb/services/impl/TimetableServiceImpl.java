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
import com.jpa.entities.GroupAvailability;
import com.jpa.entities.GroupIdles;
import com.jpa.entities.GroupLoad;
import com.jpa.entities.GroupNumDays;
import com.jpa.entities.Lesson;
import com.jpa.entities.Period;
import com.jpa.entities.Room;
import com.jpa.entities.RoomAvailability;
import com.jpa.entities.Semester;
import com.jpa.entities.Subject;
import com.jpa.entities.TeacherAvailability;
import com.jpa.entities.TeacherIdles;
import com.jpa.entities.TeacherLoad;
import com.jpa.entities.TeacherNumDays;
import com.jpa.entities.User;
import com.jpa.entities.enums.AvailabilityType;

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
			timetableData.append(getLessons(ordinalNumberOfSemester, schoolYear));
			timetableData.append(getAvailabilities(ordinalNumberOfSemester, schoolYear));
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
				.createQuery("SELECT u FROM User u WHERE u.tipKorisnika = 1 ORDER BY u.id", User.class);

		for (User teacher : teacherList.getResultList()) {
			teachers.append(teacher.getId());
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

		TypedQuery<Group> groupList = em.createQuery("SELECT g FROM Group g ORDER BY g.id", Group.class);

		for (Group group : groupList.getResultList()) {
			groups.append(group.getId());
			groups.append("\t");
			groups.append(group.getName());
			groups.append("\n");
		}

		groups.append("\n");

		return groups;
	}

	private StringBuffer getSubjects() {
		StringBuffer subjects = new StringBuffer("[subjects]");
		subjects.append("\n");

		TypedQuery<Subject> subjectList = em.createQuery("SELECT s FROM Subject s ORDER BY s.id",
				Subject.class);

		for (Subject subject : subjectList.getResultList()) {
			subjects.append(subject.getId());
			subjects.append("\t");
			subjects.append(subject.getName());
			subjects.append("\n");
		}

		subjects.append("\n");

		return subjects;
	}

	private StringBuffer getRooms() {
		StringBuffer rooms = new StringBuffer("[rooms]");
		rooms.append("\n");

		TypedQuery<Room> roomList = em.createQuery("SELECT r FROM Room r ORDER BY r.id", Room.class);

		for (Room room : roomList.getResultList()) {
			rooms.append(room.getId());
			rooms.append("\t");
			rooms.append(room.getName());
			rooms.append("\n");
		}

		rooms.append("\n");

		return rooms;
	}

	private StringBuffer getLessons(Integer ordinalNumberOfSemester, String schoolYear) {
		StringBuffer lessons = new StringBuffer("[lessons]");
		lessons.append("\n");

		TypedQuery<Lesson> lessonList = em.createQuery(
				"SELECT l FROM Lesson l WHERE l.semester = :semester AND l.year = :schoolYear ORDER BY l.id",
				Lesson.class);
		lessonList.setParameter("semester", ordinalNumberOfSemester);
		lessonList.setParameter("schoolYear", schoolYear);

		for (Lesson lesson : lessonList.getResultList()) {
			int i = 1;

			// teachers
			for (User user : lesson.getTeachers()) {
				lessons.append(user.getId());
				if (i < lesson.getTeachers().size()) {
					lessons.append(" ,");
				}
				i++;
			}
			lessons.append("\t");

			// groups
			i = 1;
			for (Group group : lesson.getGroups()) {
				lessons.append(group.getId());
				if (i < lesson.getGroups().size()) {
					lessons.append(" ,");
				}
				i++;
			}
			lessons.append("\t");

			// subject
			lessons.append(lesson.getSubject().getId());
			lessons.append("\t");

			// terms
			lessons.append(lesson.getTerms());
			lessons.append("\t");

			// rooms
			i = 1;
			for (Room room : lesson.getRooms()) {
				lessons.append(room.getId());
				if (i < lesson.getRooms().size()) {
					lessons.append(" ,");
				}
				i++;
			}

			lessons.append("\n");
		}

		lessons.append("\n");

		return lessons;
	}

	private StringBuffer getTeacherAvailabilities(Integer ordinalNumberOfSemester, String schoolYear) {
		StringBuffer teacherAvailabilities = new StringBuffer("");

		TypedQuery<Long> distinctTeacherList = em.createQuery(
				"SELECT DISTINCT(a.tecacher.id) FROM TeacherAvailability a WHERE a.semester = :semester AND a.year = :schoolYear ORDER BY a.id",
				Long.class);
		distinctTeacherList.setParameter("semester", ordinalNumberOfSemester);
		distinctTeacherList.setParameter("schoolYear", schoolYear);

		StringBuffer forbidden = new StringBuffer();
		StringBuffer undesirable = new StringBuffer();
		StringBuffer desirable = new StringBuffer();
		StringBuffer mandatory = new StringBuffer();

		for (Long teacherId : distinctTeacherList.getResultList()) {
			TypedQuery<TeacherAvailability> teacherAvailabilityList = em.createQuery(
					"SELECT a FROM TeacherAvailability a WHERE a.semester = :semester AND a.year = :schoolYear AND a.teacher.id - :teacherId ORDER BY a.id",
					TeacherAvailability.class);
			teacherAvailabilityList.setParameter("semester", ordinalNumberOfSemester);
			teacherAvailabilityList.setParameter("schoolYear", schoolYear);
			teacherAvailabilityList.setParameter("teacherId", teacherId);

			for (TeacherAvailability teacherAvailability : teacherAvailabilityList.getResultList()) {
				if (teacherAvailability.getType().equals(AvailabilityType.FORBIDDEN)) {
					forbidden.append(teacherAvailability.getDayMark() + "_" + teacherAvailability.getTermNumber());
				} else if (teacherAvailability.getType().equals(AvailabilityType.UNDESIRABLE)) {
					undesirable.append(teacherAvailability.getDayMark() + "_" + teacherAvailability.getTermNumber());
				} else if (teacherAvailability.getType().equals(AvailabilityType.DESIRABLE)) {
					desirable.append(teacherAvailability.getDayMark() + "_" + teacherAvailability.getTermNumber());
				} else if (teacherAvailability.getType().equals(AvailabilityType.MANDATORY)) {
					mandatory.append(teacherAvailability.getDayMark() + "_" + teacherAvailability.getTermNumber());
				}
			}

			if (forbidden.length() > 0 || undesirable.length() > 0 || desirable.length() > 0
					|| mandatory.length() > 0) {
				teacherAvailabilities.append(teacherId);
				teacherAvailabilities.append("\t");
				teacherAvailabilities.append(forbidden);
				teacherAvailabilities.append("\t");
				teacherAvailabilities.append(undesirable);
				teacherAvailabilities.append("\t");
				teacherAvailabilities.append(desirable);
				teacherAvailabilities.append("\t");
				teacherAvailabilities.append(mandatory);
				teacherAvailabilities.append("\n");

				forbidden.setLength(0);
				undesirable.setLength(0);
				desirable.setLength(0);
				mandatory.setLength(0);
			}
		}

		return teacherAvailabilities;
	}

	private StringBuffer getGroupAvailabilities(Integer ordinalNumberOfSemester, String schoolYear) {
		StringBuffer groupAvailabilities = new StringBuffer("");

		TypedQuery<Long> distinctGroupList = em.createQuery(
				"SELECT DISTINCT(a.group.id) FROM GroupAvailability a WHERE a.semester = :semester AND a.year = :schoolYear ORDER BY a.id",
				Long.class);
		distinctGroupList.setParameter("semester", ordinalNumberOfSemester);
		distinctGroupList.setParameter("schoolYear", schoolYear);

		StringBuffer forbidden = new StringBuffer();
		StringBuffer undesirable = new StringBuffer();
		StringBuffer desirable = new StringBuffer();
		StringBuffer mandatory = new StringBuffer();

		for (Long groupId : distinctGroupList.getResultList()) {
			TypedQuery<GroupAvailability> groupAvailabilityList = em.createQuery(
					"SELECT a FROM GroupAvailability a WHERE a.semester = :semester AND a.year = :schoolYear AND a.group.id - :groupId ORDER BY a.id",
					GroupAvailability.class);
			groupAvailabilityList.setParameter("semester", ordinalNumberOfSemester);
			groupAvailabilityList.setParameter("schoolYear", schoolYear);
			groupAvailabilityList.setParameter("groupId", groupId);

			for (GroupAvailability groupAvailability : groupAvailabilityList.getResultList()) {
				if (groupAvailability.getType().equals(AvailabilityType.FORBIDDEN)) {
					forbidden.append(groupAvailability.getDayMark() + "_" + groupAvailability.getTermNumber());
				} else if (groupAvailability.getType().equals(AvailabilityType.UNDESIRABLE)) {
					undesirable.append(groupAvailability.getDayMark() + "_" + groupAvailability.getTermNumber());
				} else if (groupAvailability.getType().equals(AvailabilityType.DESIRABLE)) {
					desirable.append(groupAvailability.getDayMark() + "_" + groupAvailability.getTermNumber());
				} else if (groupAvailability.getType().equals(AvailabilityType.MANDATORY)) {
					mandatory.append(groupAvailability.getDayMark() + "_" + groupAvailability.getTermNumber());
				}
			}

			if (forbidden.length() > 0 || undesirable.length() > 0 || desirable.length() > 0
					|| mandatory.length() > 0) {
				groupAvailabilities.append(groupId);
				groupAvailabilities.append("\t");
				groupAvailabilities.append(forbidden);
				groupAvailabilities.append("\t");
				groupAvailabilities.append(undesirable);
				groupAvailabilities.append("\t");
				groupAvailabilities.append(desirable);
				groupAvailabilities.append("\t");
				groupAvailabilities.append(mandatory);
				groupAvailabilities.append("\n");

				forbidden.setLength(0);
				undesirable.setLength(0);
				desirable.setLength(0);
				mandatory.setLength(0);
			}
		}

		return groupAvailabilities;
	}

	private StringBuffer getRoomAvailabilities(Integer ordinalNumberOfSemester, String schoolYear) {
		StringBuffer roomAvailabilities = new StringBuffer("");

		TypedQuery<Long> distinctRoomList = em.createQuery(
				"SELECT DISTINCT(a.room.id) FROM RoomAvailability a WHERE a.semester = :semester AND a.year = :schoolYear ORDER BY a.id",
				Long.class);
		distinctRoomList.setParameter("semester", ordinalNumberOfSemester);
		distinctRoomList.setParameter("schoolYear", schoolYear);

		StringBuffer forbidden = new StringBuffer();
		StringBuffer undesirable = new StringBuffer();
		StringBuffer desirable = new StringBuffer();
		StringBuffer mandatory = new StringBuffer();

		for (Long roomId : distinctRoomList.getResultList()) {
			TypedQuery<RoomAvailability> roomAvailabilityList = em.createQuery(
					"SELECT a FROM RoomAvailability a WHERE a.semester = :semester AND a.year = :schoolYear AND a.room.id - :roomId ORDER BY a.id",
					RoomAvailability.class);
			roomAvailabilityList.setParameter("semester", ordinalNumberOfSemester);
			roomAvailabilityList.setParameter("schoolYear", schoolYear);
			roomAvailabilityList.setParameter("roomId", roomId);

			for (RoomAvailability roomAvailability : roomAvailabilityList.getResultList()) {
				if (roomAvailability.getType().equals(AvailabilityType.FORBIDDEN)) {
					forbidden.append(roomAvailability.getDayMark() + "_" + roomAvailability.getTermNumber());
				} else if (roomAvailability.getType().equals(AvailabilityType.UNDESIRABLE)) {
					undesirable.append(roomAvailability.getDayMark() + "_" + roomAvailability.getTermNumber());
				} else if (roomAvailability.getType().equals(AvailabilityType.DESIRABLE)) {
					desirable.append(roomAvailability.getDayMark() + "_" + roomAvailability.getTermNumber());
				} else if (roomAvailability.getType().equals(AvailabilityType.MANDATORY)) {
					mandatory.append(roomAvailability.getDayMark() + "_" + roomAvailability.getTermNumber());
				}
			}

			if (forbidden.length() > 0 || undesirable.length() > 0 || desirable.length() > 0
					|| mandatory.length() > 0) {
				roomAvailabilities.append(roomId);
				roomAvailabilities.append("\t");
				roomAvailabilities.append(forbidden);
				roomAvailabilities.append("\t");
				roomAvailabilities.append(undesirable);
				roomAvailabilities.append("\t");
				roomAvailabilities.append(desirable);
				roomAvailabilities.append("\t");
				roomAvailabilities.append(mandatory);
				roomAvailabilities.append("\n");

				forbidden.setLength(0);
				undesirable.setLength(0);
				desirable.setLength(0);
				mandatory.setLength(0);
			}
		}

		return roomAvailabilities;
	}

	private StringBuffer getAvailabilities(Integer ordinalNumberOfSemester, String schoolYear) {
		StringBuffer availabilities = new StringBuffer("[availability]");
		availabilities.append("\n");

		availabilities.append(getTeacherAvailabilities(ordinalNumberOfSemester, schoolYear));
		availabilities.append(getGroupAvailabilities(ordinalNumberOfSemester, schoolYear));
		availabilities.append(getRoomAvailabilities(ordinalNumberOfSemester, schoolYear));

		availabilities.append("\n");

		return availabilities;
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
			numDays.append(teacherNumDays.getTeacher().getId());
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
			numDays.append(groupNumDays.getGroup().getId());
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
			idles.append(teacherIdles.getTeacher().getId());
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
			idles.append(groupIdles.getGroup().getId());
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
			loads.append(teacherLoad.getTeacher().getId());
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
			loads.append(groupLoad.getGroup().getId());
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
