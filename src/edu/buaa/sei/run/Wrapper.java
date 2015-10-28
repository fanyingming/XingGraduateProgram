package edu.buaa.sei.run;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.buaa.sei.datamodel.*;

public class Wrapper {
	
	public static ArrayList<Result> run(String nodeSendPath, String sendPath, String reveivePath, String transportPath,
			String publisherPath) throws IOException, ParserConfigurationException, SAXException {
		//setp 1: parse uml.
		NodeSend nodeSend = new NodeSend();
		nodeSend.parseNodeSend(nodeSendPath);
		String schedPolicy = nodeSend.getSchedPolicy();
		
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
		dds.printDependencyInfo();
		return dds.showResults();
	}
}
