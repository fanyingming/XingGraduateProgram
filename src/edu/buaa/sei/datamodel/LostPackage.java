package edu.buaa.sei.datamodel;

public class LostPackage {
	private int firstLostPackage;
	
	private int secondLostPackage;
	
	private String baseDependencyId;

	public int getFirstLostPackage() {
		return firstLostPackage;
	}

	public void setFirstLostPackage(int firstLostPackage) {
		this.firstLostPackage = firstLostPackage;
	}

	public int getSecondLostPackage() {
		return secondLostPackage;
	}

	public void setSecondLostPackage(int secondLostPackage) {
		this.secondLostPackage = secondLostPackage;
	}

	public String getBaseDependencyId() {
		return baseDependencyId;
	}

	public void setBaseDependencyId(String baseDependencyId) {
		this.baseDependencyId = baseDependencyId;
	}
	
	
}
