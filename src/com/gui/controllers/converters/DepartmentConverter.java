package com.gui.controllers.converters;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.gui.controllers.DepartmentController;
import com.jpa.entities.Department;

@ManagedBean(name = "departmentConverter")
@RequestScoped
public class DepartmentConverter implements Converter {

	@ManagedProperty(value = "#{departmentController}")
	private DepartmentController departmentController;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		Long departmentId = Long.valueOf(value);
		return departmentController.findDepartmentById(departmentId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value != null) {
			Department department = (Department) value;
			Long departmentId = department.getId();
			return String.valueOf(departmentId);
		}
		return "";
	}

	// Setter must exists because of dependency injection
	public void setDepartmentController(DepartmentController departmentController) {
		this.departmentController = departmentController;
	}

}
