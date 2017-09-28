package com.ejb.services.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.jpa.entities.Timetable;
import com.jpa.entities.User;
import com.jpa.entities.enums.AvailabilityType;

/**
 * Implementation of services to work with all data that is needed for timetable
 * to be created or listed
 * 
 * @author sanja
 *
 */

@Stateless
public class TimetableServiceImpl implements TimetableService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	final Charset ENCODING = StandardCharsets.UTF_8;
	final String SUBJECT_PREFIX = "subject";
	final String TEACHER_PREFIX = "teacher";
	final String ROOM_PREFIX = "room";
	final String GROUP_PREFIX = "group";

	@Override
	public String createTimetable(Semester activeSemester) {
		Long semesterId = activeSemester.getId();

		System.out.println("Writting timetable data to 'timetable.tts' file...");

		String fileName = "timetable/timetable.tts";
		Path path = Paths.get(fileName);

		StringBuffer timetableResult = new StringBuffer();
		timetableResult.append(getPeriods());
		timetableResult.append(getTeachers());
		timetableResult.append(getGroups());
		timetableResult.append(getSubjects());
		timetableResult.append(getRooms());
		timetableResult.append(getLessons(semesterId));
		timetableResult.append(getAvailabilities(semesterId));
		timetableResult.append(getNumDays(semesterId));
		timetableResult.append(getIdles(semesterId));
		timetableResult.append(getLoads(semesterId));

		try (BufferedWriter timetableData = Files.newBufferedWriter(path, ENCODING,
				StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
			timetableData.write(timetableResult.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done!");
		return timetableResult.toString();
	}

	private int cleanTimetableForSemester(Long semesterId) {
		TypedQuery<Timetable> cleanTimetable = em
				.createQuery("SELECT t FROM Timetable t WHERE t.semester.id = :semesterId", Timetable.class);
		cleanTimetable.setParameter("semesterId", semesterId);

		int i = 0;
		for (Timetable timetableRec : cleanTimetable.getResultList()) {
			em.remove(timetableRec);
			i++;
		}

		return i;
	}

	@Override
	public void parseTimetableData(Semester activeSemester) {
		String fileName = "timetable/timetable.ttbl";
		Path path = Paths.get(fileName);

		try {
			List<String> lines = Files.readAllLines(path, ENCODING);

			System.out.println("Cleaning up the timetable data before creating new for semester...");
			int deletedRecs = cleanTimetableForSemester(activeSemester.getId());
			System.out.println("Number of deleted old timetable records: " + deletedRecs);

			System.out.println("Parsing timetable data and saving into database...");

			for (String line : lines) {
				String[] lineData = line.split("\t");

				if (lineData.length > 1) {
					Timetable timetableRec = new Timetable();

					// teachers
					String[] teachers = lineData[0].split(",");
					Set<User> teachersSet = new HashSet<User>();
					for (String teacher : teachers) {
						Long teacherId = Long.valueOf(teacher.trim().replaceAll("\\D+", ""));
						teachersSet.add(em.find(User.class, teacherId));
					}
					timetableRec.setTeachers(teachersSet);

					// groups
					String[] groups = lineData[1].split(",");
					Set<Group> groupsSet = new HashSet<Group>();
					for (String group : groups) {
						Long groupId = Long.valueOf(group.trim().replaceAll("\\D+", ""));
						groupsSet.add(em.find(Group.class, groupId));
					}
					timetableRec.setGroups(groupsSet);

					// subject
					Long subjectId = Long.valueOf(lineData[2].trim().replaceAll("\\D+", ""));
					timetableRec.setSubject(em.find(Subject.class, subjectId));

					// lessonLength
					timetableRec.setLessonLength(Integer.valueOf(lineData[3].trim()));

					// room
					Long roomId = Long.valueOf(lineData[4].trim().replaceAll("\\D+", ""));
					timetableRec.setRoom(em.find(Room.class, roomId));

					// dayMark
					timetableRec.setDayMark(lineData[5].trim());

					// startTerm
					timetableRec.setStartTerm(Integer.valueOf(lineData[6].trim()));

					// semester
					timetableRec.setSemester(activeSemester);

					em.persist(timetableRec);
				}
			}
			System.out.println("Done!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File timetable.ttbl does not exist!");
		}
	}

	@Override
	public List<Timetable> getTimetableDataForTeacher(Semester semester, User teacher) {
		TypedQuery<Timetable> timetableList = em.createQuery(
				"SELECT t from Timetable t WHERE t.semester.id = :semesterId AND :teacher MEMBER OF t.teachers ORDER BY t.startTerm ASC",
				Timetable.class);
		timetableList.setParameter("semesterId", semester.getId());
		timetableList.setParameter("teacher", teacher);

		return timetableList.getResultList();
	}

	@Override
	public List<Timetable> getTimetableDataForGroup(Semester semester, Group group) {
		TypedQuery<Timetable> timetableList = em.createQuery(
				"SELECT t from Timetable t WHERE t.semester.id = :semesterId AND :group MEMBER OF t.groups ORDER BY t.startTerm ASC",
				Timetable.class);
		timetableList.setParameter("semesterId", semester.getId());
		timetableList.setParameter("group", group);

		return timetableList.getResultList();
	}

	@Override
	public List<Timetable> getTimetableDataForRoom(Semester semester, Room room) {
		TypedQuery<Timetable> timetableList = em.createQuery(
				"SELECT t from Timetable t WHERE t.semester.id = :semesterId AND t.room.id = :roomId ORDER BY t.startTerm ASC",
				Timetable.class);
		timetableList.setParameter("semesterId", semester.getId());
		timetableList.setParameter("roomId", room.getId());

		return timetableList.getResultList();
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

		TypedQuery<User> teacherList = em.createQuery("SELECT u FROM User u ORDER BY u.id", User.class);

		for (User teacher : teacherList.getResultList()) {
			teachers.append(TEACHER_PREFIX + teacher.getId());
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
			groups.append(GROUP_PREFIX + group.getId());
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

		TypedQuery<Subject> subjectList = em.createQuery("SELECT s FROM Subject s ORDER BY s.id", Subject.class);

		for (Subject subject : subjectList.getResultList()) {
			subjects.append(SUBJECT_PREFIX + subject.getId());
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
			rooms.append(ROOM_PREFIX + room.getId());
			rooms.append("\t");
			rooms.append(room.getName());
			rooms.append("\n");
		}

		rooms.append("\n");

		return rooms;
	}

	private StringBuffer getLessons(Long semesterId) {
		StringBuffer lessons = new StringBuffer("[lessons]");
		lessons.append("\n");

		TypedQuery<Lesson> lessonList = em.createQuery(
				"SELECT l FROM Lesson l WHERE l.semester.id = :semesterId AND l.approved = 1 ORDER BY l.id",
				Lesson.class);
		lessonList.setParameter("semesterId", semesterId);

		for (Lesson lesson : lessonList.getResultList()) {
			int i = 1;

			// teachers
			for (User user : lesson.getTeachers()) {
				lessons.append(TEACHER_PREFIX + user.getId());
				if (i < lesson.getTeachers().size()) {
					lessons.append(",");
				}
				i++;
			}
			lessons.append("\t");

			// groups
			i = 1;
			for (Group group : lesson.getGroups()) {
				lessons.append(GROUP_PREFIX + group.getId());
				if (i < lesson.getGroups().size()) {
					lessons.append(",");
				}
				i++;
			}
			lessons.append("\t");

			// subject
			lessons.append(SUBJECT_PREFIX + lesson.getSubject().getId());
			lessons.append("\t");

			// terms
			lessons.append(lesson.getTerms());
			lessons.append("\t");

			// rooms
			i = 1;
			for (Room room : lesson.getRooms()) {
				lessons.append(ROOM_PREFIX + room.getId());
				if (i < lesson.getRooms().size()) {
					lessons.append(",");
				}
				i++;
			}

			lessons.append("\n");
		}

		lessons.append("\n");

		return lessons;
	}

	private StringBuffer getTeacherAvailabilities(Long semesterId) {
		StringBuffer teacherAvailabilities = new StringBuffer("");

		TypedQuery<Long> distinctTeacherList = em.createQuery(
				"SELECT DISTINCT(a.teacher.id) FROM TeacherAvailability a WHERE a.semester.id = :semesterId",
				Long.class);
		distinctTeacherList.setParameter("semesterId", semesterId);

		StringBuffer forbidden = new StringBuffer();
		StringBuffer undesirable = new StringBuffer();
		StringBuffer desirable = new StringBuffer();
		StringBuffer mandatory = new StringBuffer();

		for (Long teacherId : distinctTeacherList.getResultList()) {
			TypedQuery<TeacherAvailability> teacherAvailabilityList = em.createQuery(
					"SELECT a FROM TeacherAvailability a WHERE a.semester.id = :semesterId AND a.teacher.id = :teacherId ORDER BY a.id",
					TeacherAvailability.class);
			teacherAvailabilityList.setParameter("semesterId", semesterId);
			teacherAvailabilityList.setParameter("teacherId", teacherId);

			for (TeacherAvailability teacherAvailability : teacherAvailabilityList.getResultList()) {
				if (teacherAvailability.getType().equals(AvailabilityType.FORBIDDEN.toString())) {
					if (forbidden.length() > 0) {
						forbidden.append(",");
					}
					forbidden.append(teacherAvailability.getDayMark() + "_" + teacherAvailability.getTermNumber());
				} else if (teacherAvailability.getType().equals(AvailabilityType.UNDESIRABLE.toString())) {
					if (undesirable.length() > 0) {
						undesirable.append(",");
					}
					undesirable.append(teacherAvailability.getDayMark() + "_" + teacherAvailability.getTermNumber());
				} else if (teacherAvailability.getType().equals(AvailabilityType.DESIRABLE.toString())) {
					if (desirable.length() > 0) {
						desirable.append(",");
					}
					desirable.append(teacherAvailability.getDayMark() + "_" + teacherAvailability.getTermNumber());
				} else if (teacherAvailability.getType().equals(AvailabilityType.MANDATORY.toString())) {
					if (mandatory.length() > 0) {
						mandatory.append(",");
					}
					mandatory.append(teacherAvailability.getDayMark() + "_" + teacherAvailability.getTermNumber());
				}
			}

			if (forbidden.length() > 0 || undesirable.length() > 0 || desirable.length() > 0
					|| mandatory.length() > 0) {
				teacherAvailabilities.append(TEACHER_PREFIX + teacherId);
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

	private StringBuffer getGroupAvailabilities(Long semesterId) {
		StringBuffer groupAvailabilities = new StringBuffer("");

		TypedQuery<Long> distinctGroupList = em.createQuery(
				"SELECT DISTINCT(a.group.id) FROM GroupAvailability a WHERE a.semester.id = :semesterId", Long.class);
		distinctGroupList.setParameter("semesterId", semesterId);

		StringBuffer forbidden = new StringBuffer();
		StringBuffer undesirable = new StringBuffer();
		StringBuffer desirable = new StringBuffer();
		StringBuffer mandatory = new StringBuffer();

		for (Long groupId : distinctGroupList.getResultList()) {
			TypedQuery<GroupAvailability> groupAvailabilityList = em.createQuery(
					"SELECT a FROM GroupAvailability a WHERE a.semester.id = :semesterId AND a.group.id = :groupId ORDER BY a.id",
					GroupAvailability.class);
			groupAvailabilityList.setParameter("semesterId", semesterId);
			groupAvailabilityList.setParameter("groupId", groupId);

			for (GroupAvailability groupAvailability : groupAvailabilityList.getResultList()) {
				if (groupAvailability.getType().equals(AvailabilityType.FORBIDDEN.toString())) {
					if (forbidden.length() > 0) {
						forbidden.append(",");
					}
					forbidden.append(groupAvailability.getDayMark() + "_" + groupAvailability.getTermNumber());
				} else if (groupAvailability.getType().equals(AvailabilityType.UNDESIRABLE.toString())) {
					if (undesirable.length() > 0) {
						undesirable.append(",");
					}
					undesirable.append(groupAvailability.getDayMark() + "_" + groupAvailability.getTermNumber());
				} else if (groupAvailability.getType().equals(AvailabilityType.DESIRABLE.toString())) {
					if (desirable.length() > 0) {
						desirable.append(",");
					}
					desirable.append(groupAvailability.getDayMark() + "_" + groupAvailability.getTermNumber());
				} else if (groupAvailability.getType().equals(AvailabilityType.MANDATORY.toString())) {
					if (mandatory.length() > 0) {
						mandatory.append(",");
					}
					mandatory.append(groupAvailability.getDayMark() + "_" + groupAvailability.getTermNumber());
				}
			}

			if (forbidden.length() > 0 || undesirable.length() > 0 || desirable.length() > 0
					|| mandatory.length() > 0) {
				groupAvailabilities.append(GROUP_PREFIX + groupId);
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

	private StringBuffer getRoomAvailabilities(Long semesterId) {
		StringBuffer roomAvailabilities = new StringBuffer("");

		TypedQuery<Long> distinctRoomList = em.createQuery(
				"SELECT DISTINCT(a.room.id) FROM RoomAvailability a WHERE a.semester.id = :semesterId", Long.class);
		distinctRoomList.setParameter("semesterId", semesterId);

		StringBuffer forbidden = new StringBuffer();
		StringBuffer undesirable = new StringBuffer();
		StringBuffer desirable = new StringBuffer();
		StringBuffer mandatory = new StringBuffer();

		for (Long roomId : distinctRoomList.getResultList()) {
			TypedQuery<RoomAvailability> roomAvailabilityList = em.createQuery(
					"SELECT a FROM RoomAvailability a WHERE a.semester.id = :semesterId AND a.room.id = :roomId ORDER BY a.id",
					RoomAvailability.class);
			roomAvailabilityList.setParameter("semesterId", semesterId);
			roomAvailabilityList.setParameter("roomId", roomId);

			for (RoomAvailability roomAvailability : roomAvailabilityList.getResultList()) {
				if (roomAvailability.getType().equals(AvailabilityType.FORBIDDEN.toString())) {
					if (forbidden.length() > 0) {
						forbidden.append(",");
					}
					forbidden.append(roomAvailability.getDayMark() + "_" + roomAvailability.getTermNumber());
				} else if (roomAvailability.getType().equals(AvailabilityType.UNDESIRABLE.toString())) {
					if (undesirable.length() > 0) {
						undesirable.append(",");
					}
					undesirable.append(roomAvailability.getDayMark() + "_" + roomAvailability.getTermNumber());
				} else if (roomAvailability.getType().equals(AvailabilityType.DESIRABLE.toString())) {
					if (desirable.length() > 0) {
						desirable.append(",");
					}
					desirable.append(roomAvailability.getDayMark() + "_" + roomAvailability.getTermNumber());
				} else if (roomAvailability.getType().equals(AvailabilityType.MANDATORY.toString())) {
					if (mandatory.length() > 0) {
						mandatory.append(",");
					}
					mandatory.append(roomAvailability.getDayMark() + "_" + roomAvailability.getTermNumber());
				}
			}

			if (forbidden.length() > 0 || undesirable.length() > 0 || desirable.length() > 0
					|| mandatory.length() > 0) {
				roomAvailabilities.append(ROOM_PREFIX + roomId);
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

	private StringBuffer getAvailabilities(Long semesterId) {
		StringBuffer availabilities = new StringBuffer("[availability]");
		availabilities.append("\n");

		availabilities.append(getTeacherAvailabilities(semesterId));
		availabilities.append(getGroupAvailabilities(semesterId));
		availabilities.append(getRoomAvailabilities(semesterId));

		availabilities.append("\n");

		return availabilities;
	}

	private StringBuffer getNumDays(Long semesterId) {
		StringBuffer numDays = new StringBuffer("[num_days]");
		numDays.append("\n");

		TypedQuery<TeacherNumDays> teacherNumDayList = em.createQuery(
				"SELECT n FROM TeacherNumDays n WHERE n.semester.id = :semesterId ORDER BY n.id", TeacherNumDays.class);
		teacherNumDayList.setParameter("semesterId", semesterId);

		for (TeacherNumDays teacherNumDays : teacherNumDayList.getResultList()) {
			numDays.append(TEACHER_PREFIX + teacherNumDays.getTeacher().getId());
			numDays.append("\t");
			numDays.append(teacherNumDays.getMin());
			numDays.append("\t");
			numDays.append(teacherNumDays.getOpt());
			numDays.append("\t");
			numDays.append(teacherNumDays.getMax());
			numDays.append("\n");
		}

		TypedQuery<GroupNumDays> groupNumDayList = em.createQuery(
				"SELECT n FROM GroupNumDays n WHERE n.semester.id = :semesterId ORDER BY n.id", GroupNumDays.class);
		groupNumDayList.setParameter("semesterId", semesterId);

		for (GroupNumDays groupNumDays : groupNumDayList.getResultList()) {
			numDays.append(GROUP_PREFIX + groupNumDays.getGroup().getId());
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

	private StringBuffer getIdles(Long semesterId) {
		StringBuffer idles = new StringBuffer("[idles]");
		idles.append("\n");

		TypedQuery<TeacherIdles> teacherIdleList = em.createQuery(
				"SELECT i FROM TeacherIdles i WHERE i.semester.id = :semesterId ORDER BY i.id", TeacherIdles.class);
		teacherIdleList.setParameter("semesterId", semesterId);

		for (TeacherIdles teacherIdles : teacherIdleList.getResultList()) {
			idles.append(TEACHER_PREFIX + teacherIdles.getTeacher().getId());
			idles.append("\t");
			idles.append(teacherIdles.getMax());
			idles.append("\t");
			idles.append(teacherIdles.getMultiple() == true ? 1 : 0);
			idles.append("\t");
			idles.append(teacherIdles.getDays());
			idles.append("\n");
		}

		TypedQuery<GroupIdles> groupIdleList = em.createQuery(
				"SELECT i FROM GroupIdles i WHERE i.semester.id = :semesterId ORDER BY i.id", GroupIdles.class);
		groupIdleList.setParameter("semesterId", semesterId);

		for (GroupIdles groupIdles : groupIdleList.getResultList()) {
			idles.append(GROUP_PREFIX + groupIdles.getGroup().getId());
			idles.append("\t");
			idles.append(groupIdles.getMax());
			idles.append("\t");
			idles.append(groupIdles.getMultiple() == true ? 1 : 0);
			idles.append("\t");
			idles.append(groupIdles.getDays());
			idles.append("\n");
		}

		idles.append("\n");

		return idles;
	}

	private StringBuffer getLoads(Long semesterId) {
		StringBuffer loads = new StringBuffer("[load]");
		loads.append("\n");

		TypedQuery<TeacherLoad> teacherLoadList = em.createQuery(
				"SELECT l FROM TeacherLoad l WHERE l.semester.id = :semesterId ORDER BY l.id", TeacherLoad.class);
		teacherLoadList.setParameter("semesterId", semesterId);

		for (TeacherLoad teacherLoad : teacherLoadList.getResultList()) {
			loads.append(TEACHER_PREFIX + teacherLoad.getTeacher().getId());
			loads.append("\t");
			loads.append(teacherLoad.getMin());
			loads.append("\t");
			loads.append(teacherLoad.getMax());
			loads.append("\n");
		}

		TypedQuery<GroupLoad> groupLoadList = em.createQuery(
				"SELECT l FROM GroupLoad l WHERE l.semester.id = :semesterId ORDER BY l.id", GroupLoad.class);
		groupLoadList.setParameter("semesterId", semesterId);

		for (GroupLoad groupLoad : groupLoadList.getResultList()) {
			loads.append(GROUP_PREFIX + groupLoad.getGroup().getId());
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
