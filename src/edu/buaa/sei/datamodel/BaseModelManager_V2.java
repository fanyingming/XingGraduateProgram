package edu.buaa.sei.datamodel;

import java.util.ArrayList;
import java.util.List;

public class BaseModelManager_V2 {

	List<UMLClassIns> umlClaInsList = new ArrayList<UMLClassIns>();
	List<UMLMessage_V2> umlMsgList = new ArrayList<UMLMessage_V2>();

	String startInsName;
	String endInsName = "finishNode";

	private static BaseModelManager_V2 instance;

	public static BaseModelManager_V2 INS = instance();

	private static BaseModelManager_V2 instance() {

		if (instance == null) {
			instance = new BaseModelManager_V2();
		}
		return instance;
	}

	public void addUMLMessage(UMLMessage_V2 msg) {
		umlMsgList.add(msg);
	}

	public List<UMLMessage_V2> findUMLMessage(String claInsName, String type) {
		List<UMLMessage_V2> resultMsgList = new ArrayList<UMLMessage_V2>();
		for (UMLMessage_V2 msg : umlMsgList) {
			if ((type.equals("sender") && msg.getSender().getInsName()
					.equals(claInsName))
					|| (type.equals("receiver") && msg.getReceiver()
							.getInsName().equals(claInsName)))
				resultMsgList.add(msg);
		}
		return resultMsgList;
	}

	public List<UMLMessage_V2> getUmlMsgList() {
		return umlMsgList;
	}

	public void addUMLClaIns(UMLClassIns umlClaIns) {
		this.umlClaInsList.add(umlClaIns);
	}

	public List<UMLClassIns> findInsByClaName(String umlClaName) {
		List<UMLClassIns> resultList = new ArrayList<UMLClassIns>();
		for (UMLClassIns umlClaIns : this.umlClaInsList) {
			if (umlClaIns.getClassName().equals(umlClaName))
				resultList.add(umlClaIns);
		}
		return resultList;
	}

	public UMLClassIns findInsByInsName(String insName) {
		for (UMLClassIns umlClaIns : this.umlClaInsList) {
			if (umlClaIns.getInsName().equals(insName))
				return umlClaIns;
		}
		return null;
	}

	public String getStartInsName() {
		return startInsName;
	}

	public void setStartInsName(String startInsName) {
		this.startInsName = startInsName;
	}

	public String getEndInsName() {
		return endInsName;
	}

	public void setEndInsName(String endInsName) {
		this.endInsName = endInsName;
	}

	public List<UMLClassIns> getUmlClaInsList() {
		return umlClaInsList;
	}

	public void clearAll() {
		this.umlMsgList.clear();
		this.umlClaInsList.clear();
		this.startInsName = "";
	}

}
