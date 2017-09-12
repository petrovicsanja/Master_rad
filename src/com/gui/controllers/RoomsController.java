package com.gui.controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.richfaces.component.SortOrder;
import org.richfaces.model.Filter;

import com.ejb.services.RoomsService;
import com.jpa.entities.Room;

@ManagedBean
@ViewScoped
public class RoomsController {

	@EJB
	private RoomsService roomsService;

	private Room newClassroom = new Room();
	private List<Room> classroomList = null;
	private int selectedClassroomIndex;
	private Room classroomToUpdate;

	/*
	 * Filtering
	 */
	private String nameFilter;

	/*
	 * Sort order of columns
	 */
	private SortOrder nameOrder = SortOrder.unsorted;
	private SortOrder floorOrder = SortOrder.unsorted;
	private SortOrder buildingOrder = SortOrder.unsorted;
	private SortOrder sizeOrder = SortOrder.unsorted;
	private SortOrder markOrder = SortOrder.unsorted;

	/**
	 * @return List<Room> List of all rooms
	 */
	public List<Room> listClassrooms() {
		classroomList = roomsService.listClassrooms();
		return classroomList;
	}

	public void deleteClassroom() {
		Room roomToDelete = classroomList.get(selectedClassroomIndex);
		roomsService.deleteClassroom(roomToDelete.getId());
	}

	public void addClassroom() {
		roomsService.addClassroom(newClassroom);
		newClassroom = new Room();
	}

	public void updateClassroom() {
		roomsService.updateClassroom(classroomToUpdate);
	}

	public Room findRoomById(Long roomId) {
		for (Room room : classroomList) {
			if (roomId.equals(room.getId())) {
				return room;
			}
		}
		return null;
	}

	/* Filtering */

	public Filter<?> getFilterNameImpl() {
		return new Filter<Room>() {
			public boolean accept(Room room) {
				String name = getNameFilter();
				if (name == null || name.length() == 0 || name.equals(room.getName())) {
					return true;
				}
				return false;
			}
		};
	}

	/* Sorting of columns */

	public void sortByName() {
		floorOrder = SortOrder.unsorted;
		buildingOrder = SortOrder.unsorted;
		sizeOrder = SortOrder.unsorted;
		markOrder = SortOrder.unsorted;

		if (nameOrder.equals(SortOrder.ascending)) {
			setNameOrder(SortOrder.descending);
		} else {
			setNameOrder(SortOrder.ascending);
		}
	}

	public void sortByFloor() {
		nameOrder = SortOrder.unsorted;
		buildingOrder = SortOrder.unsorted;
		sizeOrder = SortOrder.unsorted;
		markOrder = SortOrder.unsorted;

		if (floorOrder.equals(SortOrder.ascending)) {
			setFloorOrder(SortOrder.descending);
		} else {
			setFloorOrder(SortOrder.ascending);
		}
	}

	public void sortByBuilding() {
		nameOrder = SortOrder.unsorted;
		floorOrder = SortOrder.unsorted;
		sizeOrder = SortOrder.unsorted;
		markOrder = SortOrder.unsorted;

		if (buildingOrder.equals(SortOrder.ascending)) {
			setZgradaRedosled(SortOrder.descending);
		} else {
			setZgradaRedosled(SortOrder.ascending);
		}
	}

	public void sortirajPoVelicini() {
		nameOrder = SortOrder.unsorted;
		floorOrder = SortOrder.unsorted;
		buildingOrder = SortOrder.unsorted;
		markOrder = SortOrder.unsorted;

		if (sizeOrder.equals(SortOrder.ascending)) {
			setVelicinaRedosled(SortOrder.descending);
		} else {
			setVelicinaRedosled(SortOrder.ascending);
		}
	}

	public void sortByMark() {
		nameOrder = SortOrder.unsorted;
		floorOrder = SortOrder.unsorted;
		buildingOrder = SortOrder.unsorted;
		sizeOrder = SortOrder.unsorted;

		if (markOrder.equals(SortOrder.ascending)) {
			setMarkOrder(SortOrder.descending);
		} else {
			setMarkOrder(SortOrder.ascending);
		}
	}

	/** Getters and setters */

	public Room getNewClassroom() {
		return newClassroom;
	}

	public void setNewClassroom(Room newClassroom) {
		this.newClassroom = newClassroom;
	}

	public int getSelectedClassroomIndex() {
		return selectedClassroomIndex;
	}

	public void setSelectedClassroomIndex(int selectedClassroomIndex) {
		this.selectedClassroomIndex = selectedClassroomIndex;
	}

	public Room getClassroomToUpdate() {
		return classroomToUpdate;
	}

	public void setClassroomToUpdate(Room classroomToUpdate) {
		this.classroomToUpdate = classroomToUpdate;
	}

	public SortOrder getNameOrder() {
		return nameOrder;
	}

	public void setNameOrder(SortOrder nameOrder) {
		this.nameOrder = nameOrder;
	}

	public SortOrder getFloorOrder() {
		return floorOrder;
	}

	public void setFloorOrder(SortOrder floorOrder) {
		this.floorOrder = floorOrder;
	}

	public SortOrder getZgradaRedosled() {
		return buildingOrder;
	}

	public void setZgradaRedosled(SortOrder buildingOrder) {
		this.buildingOrder = buildingOrder;
	}

	public SortOrder getVelicinaRedosled() {
		return sizeOrder;
	}

	public void setVelicinaRedosled(SortOrder sizeOrder) {
		this.sizeOrder = sizeOrder;
	}

	public SortOrder getMarkOrder() {
		return markOrder;
	}

	public void setMarkOrder(SortOrder markOrder) {
		this.markOrder = markOrder;
	}

	public String getNameFilter() {
		return nameFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}
}
