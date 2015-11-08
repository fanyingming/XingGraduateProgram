package edu.buaa.sei.datamodel;

import java.util.ArrayList;
import java.util.List;

public class UMLClassIns {
	String insName;
	String className;
	double tacticSafety = 1.0;
	double reliability;
	double failureRate;
	double cost;
	double meanFR;
	double lowFR;
	double highFR;
	List<UMLClassIns> assInsList = new ArrayList<UMLClassIns>();

	public UMLClassIns(String insName, String className) {
		super();
		this.insName = insName;
		this.className = className;
	}

	public void addassIns(UMLClassIns assIns) {
		this.assInsList.add(assIns);
	}

	public String getInsName() {
		return insName;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getMeanFR() {
		return meanFR;
	}

	public void setMeanFR(double meanFR) {
		this.meanFR = meanFR;
	}

	public double getLowFR() {
		return lowFR;
	}

	public void setLowFR(double lowFR) {
		this.lowFR = lowFR;
	}

	public double getHighFR() {
		return highFR;
	}

	public void setHighFR(double highFR) {
		this.highFR = highFR;
	}

	public double getFailureRate() {
		return failureRate;
	}

	public void setFailureRate(double failureRate) {
		this.failureRate = failureRate;
	}

	public String getClassName() {
		return className;
	}

	public List<UMLClassIns> getAssInsList() {
		return assInsList;
	}

	public double getReliability() {
		return reliability;
	}

	public void setReliability(double reliability) {
		this.reliability = reliability;
	}

	public double getTacticSafety() {
		return tacticSafety;
	}

	public void setTacticSafety(double tacticSafety) {
		this.tacticSafety = tacticSafety;
	}

	

}
