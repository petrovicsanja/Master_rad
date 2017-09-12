package com.gui.controllers.converters;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.gui.controllers.RoomsController;
import com.jpa.entities.Room;

@ManagedBean(name = "roomConverter")
@RequestScoped
public class RoomConverter implements Converter {

	@ManagedProperty(value = "#{roomsController}")
	private RoomsController roomsController;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		Long roomId = Long.valueOf(value);
		Room room = roomsController.findRoomById(roomId);
		return room;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value != null) {
			Room room = (Room) value;
			Long roomId = room.getId();
			return String.valueOf(roomId);
		}
		return "";
	}

	// Setter must exist because of dependency injection
	public void setRoomsController(RoomsController roomsController) {
		this.roomsController = roomsController;
	}
}
