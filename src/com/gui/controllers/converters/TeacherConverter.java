package com.gui.controllers.converters;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.gui.controllers.UsersController;
import com.jpa.entities.User;

@ManagedBean
@RequestScoped
public class TeacherConverter implements Converter {

	@ManagedProperty(value = "#{usersController}")
	private UsersController usersController;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Long userId = Long.valueOf(value);
		User user = usersController.findUserById(userId);
		return user;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			User user = (User) value;
			Long userId = user.getId();
			return userId.toString();
		}
		return "";
	}

	public void setUsersController(UsersController usersController) {
		this.usersController = usersController;
	}

}
