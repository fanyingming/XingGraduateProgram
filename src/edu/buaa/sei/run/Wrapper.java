package edu.buaa.sei.run;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.buaa.sei.datamodel.Message;

public class Wrapper {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		
		NodeSend nodeSend = new NodeSend();
		
		nodeSend.parseNodeSend("CaseStudy/NodeSend.uml");
		String SchedPolicy = nodeSend.getSchedPolicy();
		
		
		Publisher publisher = new Publisher();
		publisher.init();
		publisher.getPublisher("CaseStudy/publisher.uml");
		
		Transporter transporter = new Transporter();
		transporter.getTransporter("CaseStudy/transport.uml");


	}
}
