package com.ejb.services;

import com.jpa.entities.GroupIdles;
import com.jpa.entities.GroupLoad;
import com.jpa.entities.GroupNumDays;
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

	/**
	 * Add new restriction about number of working days per week for group
	 * 
	 * @param GroupNumDays groupNumDays
	 */
	public void addNewNumDaysGroupRestriction(GroupNumDays groupNumDays);

	/**
	 * Add new restriction about number of working days per week for teacher
	 * 
	 * @param TeacherNumDays teacherNumDays
	 */
	public void addNewNumDaysTeacherRestriction(TeacherNumDays teacherNumDays);

	/**
	 * Add new restriction about idles for group
	 * 
	 * @param GroupIdles groupIdles
	 */
	public void addIdlesGroupRestriction(GroupIdles groupIdles);

	/**
	 * Add new restriction about idles for teacher
	 * 
	 * @param TeacherIdles teacherIdles
	 */
	public void addIdlesTeacherRestriction(TeacherIdles teacherIdles);

	/**
	 * Add new restriction about minimum and maximum number of classes per day for group
	 * 
	 * @param GroupLoad groupLoad
	 */
	public void addLoadGroupRestriction(GroupLoad groupLoad);

	/**
	 * Add new restriction about minimum and maximum number of classes per day for teacher
	 * 
	 * @param TeacherLoad teacherLoad
	 */
	public void addLoadTeacherRestriction(TeacherLoad teacherLoad);
}
