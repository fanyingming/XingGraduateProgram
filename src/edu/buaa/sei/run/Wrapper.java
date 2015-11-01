package edu.buaa.sei.run;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.buaa.sei.datamodel.*;

public class Wrapper {
	
	ArrayList<Result> finalResults;
	
	ArrayList<internalResult> finalInternalResults;
	
	String schedPolicy;
	
	public void run(String nodeSendPath, String sendPath, String reveivePath, String transportPath,
			String publisherPath) throws IOException, ParserConfigurationException, SAXException {
		//setp 1: parse uml.
		NodeSend nodeSend = new NodeSend();
		nodeSend.parseNodeSend(nodeSendPath);
		schedPolicy = nodeSend.getSchedPolicy();
		
		Sender sender = new Sender();
		sender.getSender(sendPath);
		
		Receiver receiver = new Receiver();
		receiver.getReveiver(reveivePath);
		
		Transporter transporter = new Transporter();
		transporter.getTransporter(transportPath);
		
		//step 2: 
		DDS dds = new DDS();
		dds.getAllDependency(publisherPath);
		dds.calculateDependency(sender);
		finalInternalResults = dds.showDependencyInfo();
		finalResults = dds.showResults();
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
