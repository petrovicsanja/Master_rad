package com.ejb.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ejb.services.PeriodsService;
import com.jpa.entities.Period;

@Stateless
public class PeriodsServiceImpl implements PeriodsService {

	@PersistenceContext(name = "Raspored_casova")
	private EntityManager em;

	@Override
	public void addPeriods(HashMap<String, Integer> termsOfPeriods, Integer termLength) {
		for (Map.Entry<String, Integer> entry : termsOfPeriods.entrySet()) {
			if (entry.getValue() != null) {
				Period period = getPeriodByDay(entry.getKey());
				if (period == null) {
					period = new Period();
				}
				period.setDay(entry.getKey());
				period.setDayMark(entry.getKey().substring(0, 3).toLowerCase());
				period.setTermsNumber(entry.getValue());
				period.setTermLength(termLength);
				em.persist(period);
				System.out.println(entry.getValue() + " terms are saved for " + entry.getKey().toLowerCase());
			}
		}
	}

	@Override
	public List<Period> listAllPeriods() {
		TypedQuery<Period> periodsList = em.createQuery("SELECT p FROM Period p WHERE termsNumber <> 0", Period.class);
		return periodsList.getResultList();
	}

	private Period getPeriodByDay(String day) {
		List<Period> periods = em.createQuery("SELECT p FROM Period p WHERE p.day = :day", Period.class)
				.setParameter("day", day).getResultList();
		if (periods.isEmpty()) {
			return null;
		} else {
			return periods.get(0);
		}
	}
}
