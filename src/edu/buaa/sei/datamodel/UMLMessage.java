package edu.buaa.sei.datamodel;

public class UMLMessage {
	String msgName;
	UMLComponent sender;
	UMLComponent receiver;

	public UMLMessage(String msgName, UMLComponent sender, UMLComponent receiver) {
		super();
		this.msgName = msgName;
		this.sender = sender;
		this.receiver = receiver;
	}

	public String getMsgName() {
		return msgName;
	}

	public UMLComponent getSender() {
		return sender;
	}

	public UMLComponent getReceiver() {
		return receiver;
	}

}
