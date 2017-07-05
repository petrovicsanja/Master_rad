package com.ejb.services;

import java.util.List;

import com.jpa.entities.Room;

/**
 * Business interface - It is used for defining the set of methods that are
 * available to client
 * 
 * @author sanja
 *
 */
public interface RoomsService {

	/**
	 * List all classrooms ordered by name
	 * 
	 * @return List<Room>
	 */
	public List<Room> listClassrooms();

	/**
	 * Delete existing room by id
	 * 
	 * @param Long
	 *            roomId
	 */
	public void deleteClassroom(Long roomId);

	/**
	 * Add new classroom
	 * 
	 * @param Room
	 *            room
	 */
	public void addClassroom(Room room);

	/**
	 * Update classroom
	 * 
	 * @param Room
	 *            room
	 */
	public void updateClassroom(Room room);
}
