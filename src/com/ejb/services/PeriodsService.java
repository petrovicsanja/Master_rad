package com.ejb.services;

import java.util.HashMap;
import java.util.List;

import com.jpa.entities.Period;

public interface PeriodsService {

	public void addPeriods(HashMap<String, Integer> termsOfPeriods, Integer termLength);
	
	public List<Period> listAllPeriods();
}
