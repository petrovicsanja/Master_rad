package com.ejb.services.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.RoomsService;
import com.jpa.entities.Room;

@Stateful
public class RoomsServiceImpl implements RoomsService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public List<Room> listClassrooms() {
		TypedQuery<Room> roomList = em.createQuery("SELECT r FROM Room r ORDER BY r.name", Room.class);
		return roomList.getResultList();
	}

	@Override
	public void deleteClassroom(Long roomId) {
		Room roomToDelete = em.find(Room.class, roomId);
		em.remove(roomToDelete);
		System.out.println("Ucionica je uspesno obrisana.");
	}

	@Override
	public void addClassroom(Room room) {
		em.persist(room);
		System.out.println("Nova ucionica je uspesno dodata u bazu.");
	}

	@Override
	public void updateClassroom(Room room) {
		Room roomToUpdate = em.find(Room.class, room.getId());
		roomToUpdate.setName(room.getName());
		roomToUpdate.setFloor(room.getFloor());
		roomToUpdate.setBuilding(room.getBuilding());
		roomToUpdate.setSize(room.getSize());
		roomToUpdate.setMark(room.getMark());
		System.out.println("Ucionica je uspesno izmenjena.");
	}
}
