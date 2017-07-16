package com.ejb.services;

import java.util.HashMap;
import java.util.List;

import com.jpa.entities.Period;

/**
 * Business interface - It is used for defining the set of methods that are
 * available to client
 * 
 * @author sanja
 *
 */
public interface PeriodsService {

	/**
	 * Add periods and their info for working days
	 * 
	 * @param termsOfPeriods
	 * @param termLength
	 * @param termsStartTime
	 */
	public void addPeriods(HashMap<String, Integer> termsOfPeriods, Integer termLength, String termsStartTime);

	/**
	 * Listing all periods
	 * 
	 * @return List<Period>
	 */
	public List<Period> listAllPeriods();
}
