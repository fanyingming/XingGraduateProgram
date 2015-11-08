package edu.buaa.sei.datamodel;

public class UMLComponent {

	String compName;
	Double initRelValue;
	Double tacticRelValue;

	public UMLComponent(String compName, Double relValue) {
		super();
		this.compName = compName;
		this.initRelValue = relValue;
		this.tacticRelValue = relValue;
	}

	public UMLComponent(String compName) {
		this.compName = compName;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	

	public void setInitRelValue(Double initRelValue) {
		this.initRelValue = initRelValue;
	}

	public Double getInitRelValue() {
		return initRelValue;
	}

	public Double getTacticRelValue() {
		return tacticRelValue;
	}

	public void setTacticRelValue(Double tacticRelValue) {
		this.tacticRelValue = tacticRelValue;
	}

}
