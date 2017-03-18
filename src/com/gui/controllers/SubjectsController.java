package com.gui.controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.richfaces.component.SortOrder;

import com.ejb.services.SubjectsService;
import com.jpa.entities.Subject;

@ManagedBean
@SessionScoped
public class SubjectsController {

	@EJB
	private SubjectsService subjectsService;

	private List<Subject> subjectList = null;
	private int selectedSubjectIndex;
	private Subject subjectToUpdate;
	private Subject newSubject = new Subject();

	/*
	 * Sort order of columns
	 */
	private SortOrder nameOrder = SortOrder.unsorted;
	private SortOrder markOrder = SortOrder.unsorted;

	/**
	 * @return List<Subject> List of all subjects
	 */
	public List<Subject> listSubjects() {
		if (subjectList == null) {
			subjectList = subjectsService.listSubjects();
		}
		return subjectList;
	}

	public void deleteSubject() {
		Subject subjectToDelete = subjectList.get(selectedSubjectIndex);
		subjectsService.deleteSubject(subjectToDelete.getId());
		subjectList.remove(subjectToDelete);
	}

	public void updateSubject() {
		subjectsService.updateSubject(subjectToUpdate);
		subjectList.set(selectedSubjectIndex, subjectToUpdate);
	}

	public void addSubject() {
		subjectsService.addSubject(newSubject);
		subjectList.add(newSubject);
		newSubject = new Subject();
	}

	public Subject findSubjectById(Long id) {
		for (Subject subject : subjectList) {
			if (subject.getId().equals(id)) {
				return subject;
			}
		}
		return null;
	}

	/* Sorting of columns */

	public void sortByName() {
		markOrder = SortOrder.unsorted;

		if (nameOrder.equals(SortOrder.ascending)) {
			setNameOrder(SortOrder.descending);
		} else {
			setNameOrder(SortOrder.ascending);
		}
	}

	public void sortByMark() {
		nameOrder = SortOrder.unsorted;

		if (markOrder.equals(SortOrder.ascending)) {
			setMarkOrder(SortOrder.descending);
		} else {
			setMarkOrder(SortOrder.ascending);
		}
	}

	/* Getters and setters */

	public int getSelectedSubjectIndex() {
		return selectedSubjectIndex;
	}

	public void setSelectedSubjectIndex(int selectedSubjectIndex) {
		this.selectedSubjectIndex = selectedSubjectIndex;
	}

	public Subject getSubjectToUpdate() {
		return subjectToUpdate;
	}

	public void setSubjectToUpdate(Subject subjectToUpdate) {
		this.subjectToUpdate = subjectToUpdate;
	}

	public Subject getNewSubject() {
		return newSubject;
	}

	public void setNewSubject(Subject newSubject) {
		this.newSubject = newSubject;
	}

	public SortOrder getNameOrder() {
		return nameOrder;
	}

	public void setNameOrder(SortOrder nameOrder) {
		this.nameOrder = nameOrder;
	}

	public SortOrder getMarkOrder() {
		return markOrder;
	}

	public void setMarkOrder(SortOrder markOrder) {
		this.markOrder = markOrder;
	}
}
