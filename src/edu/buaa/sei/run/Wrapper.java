package edu.buaa.sei.run;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.buaa.sei.datamodel.Message;
import edu.buaa.sei.datamodel.Process;
import edu.buaa.sei.datamodel.Receiver;
import edu.buaa.sei.datamodel.Sender;

public class Wrapper {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		
		//setp 1: parse uml.
		NodeSend nodeSend = new NodeSend();
		nodeSend.parseNodeSend("CaseStudy/NodeSend.uml");
		String schedPolicy = nodeSend.getSchedPolicy();
		
		Sender sender = new Sender();
		sender.getSender("CaseStudy/send.uml");
		//test
		ArrayList<Double> rl = sender.getTimeTableTime(2, 1);
		for(int i = 0; i < rl.size(); i++) {
			System.out.println(rl.get(i));
		}
		
		Receiver receiver = new Receiver();
		receiver.getReveiver("CaseStudy/receive.uml");
		
		Transporter transporter = new Transporter();
		transporter.getTransporter("CaseStudy/transport.uml");
		
		
		
		//step 2: 
		System.out.println("-------------------------");
		
		DDS dds = new DDS();
		dds.getAllDependency("CaseStudy/publisher.uml");
		dds.calculateDependency();
		dds.printDependencyInfo();
		System.out.println("-------------------------");
		dds.calculateTime();
		System.out.println("-------------------------");
		dds.calculateReliability();
	}
}
