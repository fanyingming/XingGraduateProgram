/**
 * 
 */
/**
 * @author zhaoyaxing
 *
 */
package edu.buaa.sei.run;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import edu.buaa.sei.run.AnalyzerMain_V2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.buaa.sei.utils.RandomGenerator;
import edu.buaa.sei.utils.StringHandle;

public class AnalyzerMain_V2 {

	private ArrayList<Message> messageList;
	// FIFO
	// FixedPriority
	// TimeTableDrivenff
	private String schedPolicy;
	
	private double paskageUnitSize = 1.4;
	
	private double totalsize;//kb
	
	private double minKb;
	
	private double maxKb;

	public static void main(String[] args) throws IOException {

		AnalyzerMain_V2 am_V2 = new AnalyzerMain_V2();

		try {
			ArrayList<Message> ml = am_V2
					.getPublisher("CaseStudy/publisher.uml");
			am_V2.getSchedPolicy("CaseStudy/NodeSend.uml");

			am_V2.calculatePublisher(ml);

			am_V2.getTransport("CaseStudy/transport.uml");
			
			am_V2.calculateTransport();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean containDumplicate(Message m) {
		for (int i = 0; i < messageList.size(); i++) {
			if (messageList.get(i).title.compareTo(m.title) == 0)
				return true;
		}

		return false;
	}

	public Message findTimeById(String id, NodeList list) {

		for (int temp = 0; temp < list.getLength(); temp++) {
			Node nNode = (Node) list.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String timeId = eElement.getAttribute("base_NamedElement");
				if (timeId.equals(id)) {
					String priority = eElement.getAttribute("priority");
					if (priority.length() <= 0)
						continue;
					Message mes = new Message();
					mes.priority = Integer.valueOf(priority);

					for (Node node = nNode.getFirstChild(); node != null; node = node
							.getNextSibling()) {
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							if (node.getNodeName().equals("execTime")) {
								String timeStr = node.getFirstChild()
										.getNodeValue();
								String timeStr1 = StringHandle
										.delUnusedStr(timeStr);
								String[] str = timeStr1.split(",");
								if (str.length < 2)
									continue;
								mes.time = Double.valueOf(str[0]);
								return mes;
							}
						}
					}
				}
			}
		}
		return null;
	}

	public ArrayList<Message> getPublisher(String umlPath)
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(umlPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("message");
		NodeList timeList = doc.getElementsByTagName("GQAM:GaStep");
		System.out.println("message count : " + nList.getLength());
		messageList = new ArrayList<Message>();

		// scan xml and get all valid message.
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = (Node) nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String type = eElement.getAttribute("xmi:type");
				if (type.equals("uml:Message")) {
					Message mes = new Message();
					String nameStr = eElement.getAttribute("name");
					String[] strList = nameStr.split(": ");
					if (strList.length < 2)
						continue;
					mes.title = strList[0];
					mes.name = strList[1];
					mes.id = eElement.getAttribute("xmi:id");

					Message mes_t = findTimeById(mes.id, timeList);
					if (mes_t == null)
						continue;
					mes.time = mes_t.time;
					mes.priority = mes_t.priority;

					if (containDumplicate(mes))
						continue;

					System.out.println("title: " + mes.title + ", name: "
							+ mes.name + ", time: " + mes.time + ", priority: "
							+ mes.priority);
					messageList.add(mes);
				}

			}
		}

		System.out.println("valid count : " + messageList.size());
		return messageList;
	}

	public void getSchedPolicy(String umlPath)
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(umlPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("GRM:Scheduler");

		messageList = new ArrayList<Message>();

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = (Node) nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String type = eElement.getAttribute("schedPolicy");
				schedPolicy = type;
			}
		}
		System.out.println("SchedPolicy:" + schedPolicy);
	}

	public double calculatePublisher(ArrayList<Message> ml) {
		System.out.println("calaulating time from message list, which size is "
				+ ml.size());
		double time = 0;
		if (schedPolicy.compareTo("FIFO") == 0) {

			for (int i = 0; i < ml.size(); i++) {
				time += ml.get(i).getTime();
			}
			System.out.println("FIFO: time used " + time + "ms.");
		}
		
		return time;
	}

	public void getTransport(String umlPath)
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(umlPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("GQAM:GaCommStep");

		int size_kb = -1;
		String transportId = null;
		double maxThroughPut = 0, minThroughput = 0;

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = (Node) nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				transportId = eElement.getAttribute("base_NamedElement");

				for (Node node = nNode.getFirstChild(); node != null; node = node
						.getNextSibling()) {
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						if (node.getNodeName().equals("msgSize")) {
							String sizeStr = node.getFirstChild()
									.getNodeValue();
							size_kb = StringHandle.getKbFromStr(sizeStr);
							System.out.println("size:" + size_kb + "kb");
							break;
						}
					}
				}
				
				if (size_kb != -1)
					break;
			}
		}
		
		// get MaxThroughPut and MinThroughput
		NodeList throughPutList = doc.getElementsByTagName("Profile:SendMessage");
		for (int temp = 0; temp < throughPutList.getLength(); temp++) {
			Node nNode = (Node) throughPutList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String id = eElement.getAttribute("base_Message");
				
				if (id.compareTo(transportId) == 0) {
					for (Node node = nNode.getFirstChild(); node != null; node = node
							.getNextSibling()) {
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							if (node.getNodeName().equals("MaxThroughPut")) {
								String maxStr = ((Element)node).getAttribute("precision");
								System.out.println("max:" + maxStr);
								
								maxThroughPut = Double.valueOf(maxStr);
							}
						}
						
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							if (node.getNodeName().equals("MinThroughput")) {
								String minStr = ((Element)node).getAttribute("precision");
								System.out.println("min:" + minStr);
								
								minThroughput = Double.valueOf(minStr);
							}
						}
					}
				}
			}
		}
		
		totalsize = size_kb;
		minKb = minThroughput;
		maxKb = maxThroughPut;	
	}
	
	public double calculateTransport() {

		double time = 0;
		if (schedPolicy.compareTo("FIFO") == 0) {
			int packageNum = (int) Math.ceil(totalsize/paskageUnitSize);
			double bandWidth = RandomGenerator.getARandomNum(minKb, maxKb);
			time = (totalsize/bandWidth)*1000;
			System.out.println("transport time used " + time + "ms.");
		}
		
		return time;
	}
}
