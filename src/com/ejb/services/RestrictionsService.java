package com.ejb.services;

import java.util.List;

import com.jpa.entities.GroupIdles;
import com.jpa.entities.GroupLoad;
import com.jpa.entities.GroupNumDays;
import com.jpa.entities.Semester;
import com.jpa.entities.TeacherIdles;
import com.jpa.entities.TeacherLoad;
import com.jpa.entities.TeacherNumDays;

/**
 * @author Sanja
 *
 *         Interfejs koji sluzi za definisanje skupa metoda koje su dostupne
 *         klijentima tzv. business interface
 */
public interface RestrictionsService {

	List<GroupNumDays> listNumDaysGroupRestrictions(Semester activeSemester);

	List<TeacherNumDays> listNumDaysTeacherRestrictions(Long teacherId, Semester activeSemester);

	List<GroupIdles> listIdlesGroupRestrictions(Semester activeSemester);

	List<TeacherIdles> listIdlesTeacherRestrictions(Long teacherId, Semester activeSemester);

	List<GroupLoad> listLoadGroupRestrictions(Semester activeSemester);

	List<TeacherLoad> listLoadTeacherRestrictions(Long teacherId, Semester activeSemester);

	/**
	 * Add new restriction about number of working days per week for group
	 * 
	 * @param GroupNumDays
	 *            groupNumDays
	 */
	void addNewNumDaysGroupRestriction(GroupNumDays groupNumDays, Semester activeSemester);

	/**
	 * Add new restriction about number of working days per week for teacher
	 * 
	 * @param TeacherNumDays
	 *            teacherNumDays
	 */
	void addNewNumDaysTeacherRestriction(TeacherNumDays teacherNumDays, Semester activeSemester);

	/**
	 * Add new restriction about idles for group
	 * 
	 * @param GroupIdles
	 *            groupIdles
	 */
	public void addIdlesGroupRestriction(GroupIdles groupIdles, Semester activeSemester);

	/**
	 * Add new restriction about idles for teacher
	 * 
	 * @param TeacherIdles
	 *            teacherIdles
	 */
	void addIdlesTeacherRestriction(TeacherIdles teacherIdles, Semester activeSemester);

	/**
	 * Add new restriction about minimum and maximum number of classes per day
	 * for group
	 * 
	 * @param GroupLoad
	 *            groupLoad
	 */
	void addLoadGroupRestriction(GroupLoad groupLoad, Semester activeSemester);

	/**
	 * Add new restriction about minimum and maximum number of classes per day
	 * for teacher
	 * 
	 * @param TeacherLoad
	 *            teacherLoad
	 */
	void addLoadTeacherRestriction(TeacherLoad teacherLoad, Semester activeSemester);

	void deleteNumDaysGroupRestriction(Long groupNumDaysId);

	void deleteNumDaysTeacherRestriction(Long teacherNumDaysId);

	void deleteIdlesGroupRestriction(Long groupIdlesId);

	void deleteIdlesTeacherRestriction(Long teacherIdlesId);

	void deleteLoadGroupRestriction(Long groupLoadId);

	void deleteLoadTeacherRestriction(Long teacherLoadId);
}
