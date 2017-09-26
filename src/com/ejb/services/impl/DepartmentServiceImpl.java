package com.ejb.services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.DepartmentService;
import com.jpa.entities.Department;

/**
 * Implementation of services to work with department data
 * 
 * @author sanja
 *
 */

@Stateless
public class DepartmentServiceImpl implements DepartmentService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public List<Department> listDepartments() {
		TypedQuery<Department> departmentList = em
				.createQuery("SELECT d FROM Department d WHERE d.deleted = 0 ORDER BY d.name", Department.class);
		return departmentList.getResultList();
	}

	@Override
	public void deleteDepartment(Long departmentId) {
		Department departmentToDelete = em.find(Department.class, departmentId);
		departmentToDelete.setDeleted(true);
		System.out.println("Department is successfully deleted from the database, id: " + departmentId);
	}

	@Override
	public void addDepartment(Department department) {
		department.setDeleted(false);
		em.persist(department);
		System.out.println("New department is successfully added to the database, id: " + department.getId());
	}
}
