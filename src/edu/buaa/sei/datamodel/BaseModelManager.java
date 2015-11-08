package edu.buaa.sei.datamodel;

import java.util.ArrayList;
import java.util.List;

public class BaseModelManager {

	List<UMLComponent> umlCompList = new ArrayList<UMLComponent>();
	List<UMLDependency> umlDepList = new ArrayList<UMLDependency>();
	List<UMLMessage> umlMsgList = new ArrayList<UMLMessage>();

	String startCompName;
	String endCompName;

	private static BaseModelManager instance;

	public static BaseModelManager INS = instance();

	private static BaseModelManager instance() {

		if (instance == null) {
			instance = new BaseModelManager();
		}
		return instance;
	}

	public void addUMLComponent(UMLComponent comp) {
		umlCompList.add(comp);
	}

	public void addUMLDependency(UMLDependency dep) {
		umlDepList.add(dep);
	}

	public void addUMLMessage(UMLMessage msg) {
		umlMsgList.add(msg);
	}

	public UMLComponent findUMLComponent(String compName) {
		for (UMLComponent comp : umlCompList) {
			if (comp.getCompName().equals(compName))
				return comp;
		}
		return null;
	}

	public List<UMLDependency> findUMLDependency(String compName, String type) {
		List<UMLDependency> resultDepList = new ArrayList<UMLDependency>();
		for (UMLDependency dep : umlDepList) {
			if ((type.equals("supplier") && dep.getSupplier().getCompName()
					.equals(compName))
					|| (type.equals("client") && dep.getClient().getCompName()
							.equals(compName)))
				resultDepList.add(dep);
		}
		return resultDepList;
	}

	public List<UMLDependency> findUMLDependency(UMLComponent supplier,
			UMLComponent client) {
		List<UMLDependency> resultDepList = new ArrayList<UMLDependency>();
		for (UMLDependency dep : umlDepList) {
			if (dep.getSupplier().equals(supplier)
					&& dep.getClient().equals(client))
				resultDepList.add(dep);
		}
		return resultDepList;
	}

	public List<UMLMessage> findUMLMessage(String compName, String type) {
		List<UMLMessage> resultMsgList = new ArrayList<UMLMessage>();
		for (UMLMessage msg : umlMsgList) {
			if ((type.equals("sender") && msg.getSender().equals(compName))
					|| (type.equals("receiver") && msg.getReceiver().equals(
							compName)))
				resultMsgList.add(msg);
		}
		return resultMsgList;
	}

	public List<UMLComponent> getUmlCompList() {
		return umlCompList;
	}

	public List<UMLDependency> getUmlDepList() {
		return umlDepList;
	}

	public List<UMLMessage> getUmlMsgList() {
		return umlMsgList;
	}

	public String getStartCompName() {
		return startCompName;
	}

	public void setStartCompName(String startCompName) {
		this.startCompName = startCompName;
	}

	public String getEndCompName() {
		return endCompName;
	}

	public void setEndCompName(String endCompName) {
		this.endCompName = endCompName;
	}

}
