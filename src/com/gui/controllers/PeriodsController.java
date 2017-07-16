package com.gui.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ejb.services.PeriodsService;
import com.jpa.entities.Period;

@ManagedBean
@ViewScoped
public class PeriodsController {

	@EJB
	private PeriodsService periodsService;

	private Integer termLength = 60;
	private Integer termsMonday = null;
	private Integer termsThuesday = null;
	private Integer termsWednesday = null;
	private Integer termsThursday = null;
	private Integer termsFriday = null;
	private Integer termsSaturday = null;
	private Integer termsSunday = null;
	private String termsTime = null;
	private List<Period> allPeriods = null;

	@PostConstruct
	public void init() {
		listAllPeriods();
	}

	public void addPeriods() {
		HashMap<String, Integer> termsOfPeriods = new HashMap<>();

		termsOfPeriods.put("Ponedeljak", termsMonday);
		termsMonday = null;

		termsOfPeriods.put("Utorak", termsThuesday);
		termsThuesday = null;

		termsOfPeriods.put("Sreda", termsWednesday);
		termsWednesday = null;

		termsOfPeriods.put("Cetvrtak", termsThursday);
		termsThursday = null;

		termsOfPeriods.put("Petak", termsFriday);
		termsFriday = null;

		termsOfPeriods.put("Subota", termsSaturday);
		termsSaturday = null;

		termsOfPeriods.put("Nedelja", termsSunday);
		termsSunday = null;

		periodsService.addPeriods(termsOfPeriods, termLength, termsTime);
		allPeriods = periodsService.listAllPeriods();
		termsTime = null;
	}

	public List<Period> listAllPeriods() {
		if (allPeriods == null) {
			allPeriods = periodsService.listAllPeriods();
		}
		return allPeriods;
	}

	public int getNumberOfWorkingDays() {
		List<String> distinctPeriods = new ArrayList<>();
		for (Period period : getAllPeriods()) {
			if (!distinctPeriods.contains(period.getDay())) {
				distinctPeriods.add(period.getDay());
			}
		}
		return distinctPeriods.size();
	}

	/*
	 * Getters and setters
	 */

	public Integer getTermLength() {
		return termLength;
	}

	public void setTermLength(Integer termLength) {
		this.termLength = termLength;
	}

	public Integer getTermsMonday() {
		return termsMonday;
	}

	public void setTermsMonday(Integer termsMonday) {
		this.termsMonday = termsMonday;
	}

	public Integer getTermsThuesday() {
		return termsThuesday;
	}

	public void setTermsThuesday(Integer termsThuesday) {
		this.termsThuesday = termsThuesday;
	}

	public Integer getTermsWednesday() {
		return termsWednesday;
	}

	public void setTermsWednesday(Integer termsWednesday) {
		this.termsWednesday = termsWednesday;
	}

	public Integer getTermsThursday() {
		return termsThursday;
	}

	public void setTermsThursday(Integer termsThursday) {
		this.termsThursday = termsThursday;
	}

	public Integer getTermsFriday() {
		return termsFriday;
	}

	public void setTermsFriday(Integer termsFriday) {
		this.termsFriday = termsFriday;
	}

	public Integer getTermsSaturday() {
		return termsSaturday;
	}

	public void setTermsSaturday(Integer termsSaturday) {
		this.termsSaturday = termsSaturday;
	}

	public Integer getTermsSunday() {
		return termsSunday;
	}

	public void setTermsSunday(Integer termsSunday) {
		this.termsSunday = termsSunday;
	}

	public String getTermsTime() {
		return termsTime;
	}

	public void setTermsTime(String termsStartTime) {
		this.termsTime = termsStartTime;
	}

	public List<Period> getAllPeriods() {
		return allPeriods;
	}

	public void setAllPeriods(List<Period> allPeriods) {
		this.allPeriods = allPeriods;
	}
}
