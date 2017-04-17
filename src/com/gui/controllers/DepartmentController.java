package com.gui.controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ejb.services.DepartmentService;
import com.jpa.entities.Department;

@ManagedBean
@ViewScoped
public class DepartmentController {

	@EJB
	private DepartmentService departmentService;

	private List<Department> departmentList = null;
	private int selectedDepartmentIndex;
	private Department newDepartment = new Department();

	public List<Department> listDepartments() {
		if (departmentList == null) {
			departmentList = departmentService.listDepartments();
		}
		return departmentList;
	}

	public Department findDepartmentById(Long departmentId) {
		for (Department department : departmentList) {
			if (department.getId().equals(departmentId)) {
				return department;
			}
		}
		return null;
	}

	public void deleteDepartment() {
		Department departmentToDelete = departmentList.get(selectedDepartmentIndex);
		departmentService.deleteDepartment(departmentToDelete.getId());
		departmentList.remove(departmentToDelete);
	}

	public void addDepartment() {
		departmentService.addDepartment(newDepartment);
		departmentList.add(newDepartment);
		newDepartment = new Department();
	}

	/*
	 * Getters and setters
	 */

	public int getSelectedDepartmentIndex() {
		return selectedDepartmentIndex;
	}

	public void setSelectedDepartmentIndex(int selectedDepartmentIndex) {
		this.selectedDepartmentIndex = selectedDepartmentIndex;
	}

	public Department getNewDepartment() {
		return newDepartment;
	}

	public void setNewDepartment(Department newDepartment) {
		this.newDepartment = newDepartment;
	}

}
