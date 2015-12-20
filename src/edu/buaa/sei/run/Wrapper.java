package edu.buaa.sei.run;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.buaa.sei.datamodel.*;

public class Wrapper {
	
	ArrayList<Result> finalResults;
	
	ArrayList<internalResult> finalInternalResults;
	
	ArrayList<Dependency> dependencyList;
	
	ArrayList<Publisher> publisherList;
	
	String schedPolicy;
	
	public ArrayList<Publisher> getPublisherList() {
		return publisherList;
	}


	public void setPublisherList(ArrayList<Publisher> publisherList) {
		this.publisherList = publisherList;
	}


	public void run(String nodeSendPath, String sendPath, String reveivePath, String transportPath,
			String publisherPath) throws IOException, ParserConfigurationException, SAXException {
		//setp 1: parse uml.
		NodeSend nodeSend = new NodeSend();
		nodeSend.parseNodeSend(nodeSendPath);
		schedPolicy = nodeSend.getSchedPolicy();
		
		Sender sender = new Sender();
		sender.getSender(sendPath);
		sender.getFIFOSendTime();
		
		Receiver receiver = new Receiver();
		receiver.getReveiver(reveivePath);
		
		Transporter transporter = new Transporter();
		transporter.getTransporter(transportPath);
		double speedMin = transporter.speedMin();
		double speedMax = transporter.speedMax();
		//step 2: 
		DDS dds = new DDS();
		dependencyList = dds.getAllDependency(publisherPath);
		dds.calculateDependency(sender, receiver, speedMin, speedMax);
		finalInternalResults = dds.showDependencyInfo();
		finalResults = dds.showResults();
		publisherList = dds.getPublisherList();
	}

	
	public ArrayList<Dependency> getDependencyList() {
		return dependencyList;
	}


	public void setDependencyList(ArrayList<Dependency> dependencyList) {
		this.dependencyList = dependencyList;
	}


	public ArrayList<Result> getFinalResults() {
		return finalResults;
	}

	public void setFinalResults(ArrayList<Result> finalResults) {
		this.finalResults = finalResults;
	}

	public ArrayList<internalResult> getFinalInternalResults() {
		return finalInternalResults;
	}

	public void setFinalInternalResults(
			ArrayList<internalResult> finalInternalResults) {
		this.finalInternalResults = finalInternalResults;
	}

	public String getSchedPolicy() {
		return schedPolicy;
	}

	public void setSchedPolicy(String schedPolicy) {
		this.schedPolicy = schedPolicy;
	}
	
	
}
