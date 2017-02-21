package com.ejb.services;

import java.util.List;

import com.jpa.entities.Room;

public interface RoomsService {

	/**
	 * List all classrooms ordered by name
	 * 
	 * @return List<Room>
	 */
	public List<Room> listClassrooms();

	/**
	 * Delete room
	 * 
	 * @param Long roomId
	 */
	public void deleteClassroom(Long roomId);

	/**
	 * Add new classroom
	 * 
	 * @param Room room
	 */
	public void addClassroom(Room room);

	/**
	 * Update classroom
	 * 
	 * @param Room room
	 */
	public void updateClassroom(Room room);
}
