package edu.buaa.sei.datamodel;

public class UMLDependency {
	String depName;
	UMLComponent supplier;
	UMLComponent client;

	public UMLDependency(String depName, UMLComponent supplier,
			UMLComponent client) {
		super();
		this.depName = depName;
		this.supplier = supplier;
		this.client = client;
	}

	public UMLComponent getSupplier() {
		return supplier;
	}

	public void setSupplier(UMLComponent supplier) {
		this.supplier = supplier;
	}

	public UMLComponent getClient() {
		return client;
	}

	public void setClient(UMLComponent client) {
		this.client = client;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

}
