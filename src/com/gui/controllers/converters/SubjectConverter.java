package com.gui.controllers.converters;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.gui.controllers.SubjectsController;
import com.jpa.entities.Subject;

@ManagedBean(name = "subjectConverter")
@RequestScoped
public class SubjectConverter implements Converter {

	@ManagedProperty(value = "#{subjectsController}")
	private SubjectsController subjectsController;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		Long subjectId = Long.valueOf(value);
		return subjectsController.findSubjectById(subjectId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value != null) {
			Subject subject = (Subject) value;
			Long subjectId = subject.getId();
			return String.valueOf(subjectId);
		}
		return "";
	}

	public void setSubjectsController(SubjectsController subjectsController) {
		this.subjectsController = subjectsController;
	}
}
