package com.ejb.services;

import java.util.List;

import com.jpa.entities.Department;

public interface DepartmentService {

	/**
	 * List all departments ordered by name
	 * 
	 * @return List<Department>
	 */
	List<Department> listDepartments();

	/**
	 * Delete department by its id
	 * 
	 * @param Long
	 *            departmentId
	 */
	void deleteDepartment(Long departmentId);

	void addDepartment(Department department);
}
