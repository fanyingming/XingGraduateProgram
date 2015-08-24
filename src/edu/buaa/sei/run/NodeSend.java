package edu.buaa.sei.run;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.buaa.sei.datamodel.Message;
import edu.buaa.sei.datamodel.Process;
import edu.buaa.sei.utils.StringHandle;

public class NodeSend {
	
	private String schedPolicy;
	
	private ArrayList<Process> pList = new ArrayList<Process>();
	
	public void parseNodeSend(String umlPath)
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(umlPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("GRM:Scheduler");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = (Node) nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				//get schedPolicy, FIFO timeTable priority.
				String type = eElement.getAttribute("schedPolicy");
				schedPolicy = type;
				
				//get processList, eg. t1,t2,t3
				String processStr = eElement.getAttribute("schedule");
				String[] processList = processStr.split(";");
				System.out.println("find process: " + processStr);
				getProcessContent(doc, processList);
				
				break;
			}
		}
		System.out.println("SchedPolicy:" + schedPolicy);
	}
	
	private void getProcessContent(Document doc, String[] processList) {
		pList.clear();
		NodeList nList = doc.getElementsByTagName("packagedElement");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = (Node) nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String processStr = eElement.getAttribute("name");
				
				for (int i = 0; i < processList.length; i++) {
					String name = processList[i] + ":Task";
					if (name.compareTo(processStr) == 0) {
						Process process = new Process();
						process.name = processList[i];
						
						System.out.println("find " + process.name);
						
						for (Node node = nNode.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								if (node.getNodeName().equals("ownedOperation")) {
									Element e = (Element) node;
									String ownedOperationName = e.getAttribute("name");
									process.addThreads(ownedOperationName);
									System.out.println("add thread " + ownedOperationName);
								}
							}
						}
						
						pList.add(process);
						break;
					}
				}

			}
		}
	}
	
	public String getSchedPolicy() {
		return schedPolicy;
	}

	public void setSchedPolicy(String schedPolicy) {
		this.schedPolicy = schedPolicy;
	}

	public ArrayList<Process> getpList() {
		return pList;
	}

	public void setpList(ArrayList<Process> pList) {
		this.pList = pList;
	}
	
}
