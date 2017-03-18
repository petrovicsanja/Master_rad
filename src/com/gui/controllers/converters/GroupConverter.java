package com.gui.controllers.converters;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.gui.controllers.GroupsController;
import com.jpa.entities.Group;

@ManagedBean(name = "groupConverter")
@RequestScoped
public class GroupConverter implements Converter {

	@ManagedProperty(value = "#{groupsController}")
	private GroupsController groupsController;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		Long groupId = Long.valueOf(value);
		return groupsController.findGroupById(groupId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value != null) {
			Group group = (Group) value;
			Long groupId = group.getId();
			return groupId.toString();
		}
		return "";
	}

	// Setter must exist because of dependency injection
	public void setGroupsController(GroupsController groupsController) {
		this.groupsController = groupsController;
	}

}
