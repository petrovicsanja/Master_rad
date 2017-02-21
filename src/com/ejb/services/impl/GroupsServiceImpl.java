package com.ejb.services.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.GroupsService;
import com.jpa.entities.Department;
import com.jpa.entities.Group;

@Stateful
public class GroupsServiceImpl implements GroupsService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public void addGroup(Group group) {
		em.persist(group);
		System.out.println("Nova grupa je uspesno dodata.");
	}

	@Override
	public List<Department> listDepartments() {
		TypedQuery<Department> departmentList = em.createQuery("SELECT d FROM Department d ORDER BY d.nazivSmera",
				Department.class);
		return departmentList.getResultList();
	}

	@Override
	public List<Group> listGroups() {
		TypedQuery<Group> groupList = em.createQuery("SELECT g FROM Group g ORDER BY g.naziv", Group.class);
		return groupList.getResultList();
	}

	@Override
	public void deleteGroup(Long groupId) {
		Group groupToDelete = em.find(Group.class, groupId);
		em.remove(groupToDelete);
		System.out.println("Grupa je uspesno obrisana");
	}

	@Override
	public void updateGroup(Group group) {
		Group groupToUpdate = em.find(Group.class, group.getIdGrupe());
		groupToUpdate.setNaziv(group.getNaziv());
		groupToUpdate.setSmer(group.getSmer());
		groupToUpdate.setVelicina(group.getVelicina());
		groupToUpdate.setOznaka(group.getOznaka());
		System.out.println("Grupa je uspesno izmenjena.");
	}
}
