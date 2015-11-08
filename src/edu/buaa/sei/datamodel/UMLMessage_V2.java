package edu.buaa.sei.datamodel;

public class UMLMessage_V2 {
	String msgName;
	UMLClassIns sender;
	UMLClassIns receiver;

	public UMLMessage_V2(String msgName, UMLClassIns sender, UMLClassIns receiver) {
		super();
		this.msgName = msgName;
		this.sender = sender;
		this.receiver = receiver;
	}

	public String getMsgName() {
		return msgName;
	}

	public UMLClassIns getSender() {
		return sender;
	}

	public UMLClassIns getReceiver() {
		return receiver;
	}
}
