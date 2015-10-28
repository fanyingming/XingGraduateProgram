package edu.buaa.sei.datamodel;

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

import edu.buaa.sei.utils.StringHandle;

public class Sender {
	
	private ArrayList<Message> messageList = new ArrayList<Message>();
	
	public double getFIFOSendTime() {
		System.out.println("calaulating time from message list, which size is "
				+ messageList.size());
		double time = 0;

		for (int i = 0; i < messageList.size(); i++) {
			time += messageList.get(i).getTime();
		}
		System.out.println("FIFO: time used " + time + "ms.");

		return time;
	}
	
	public ArrayList<Double> getTimeTableTime(int threads, int timeTableUnit) {
		ArrayList<Double> timeList = new ArrayList<Double>();
		ArrayList<TimeTable> timeTableList = new ArrayList<TimeTable>();
		int threads_t = threads;
		
		double neededTime = getFIFOSendTime();
		
		for (int i = 0; i < threads; i++) {
			TimeTable timeTable = new TimeTable();
			timeTable.setTimeUsed(0);
			timeTable.setNeededTime(neededTime);
			
			timeTableList.add(timeTable);
		}
		
		double timeNow = 0;
		double timePerProcess;
		
		while (threads_t > 0) {
			timePerProcess = (double)timeTableUnit/(double)threads_t;//ms
			
			for (int i = 0; i < timeTableList.size(); i++) {
				if (timeTableList.get(i).getNeededTime() <= 0) {//this process done.
					continue;
				} else if (timeTableList.get(i).getNeededTime() > timePerProcess) {
					timeTableList.get(i).setNeededTime(timeTableList.get(i).getNeededTime() - timePerProcess);
					timeNow += timePerProcess;
				} else {
					timeNow += timeTableList.get(i).getNeededTime();
					timeTableList.get(i).setNeededTime(0);
					timeTableList.get(i).setTimeUsed(timeNow);
					threads_t--;
				}
			}
		}//while
		
		for (int i = 0; i < threads; i++) {
			timeList.add(timeTableList.get(i).getTimeUsed());
		}
		
		return timeList;
	}
	
	public boolean containDumplicate(Message m) {
		for (int i = 0; i < messageList.size(); i++) {
			if (messageList.get(i).title.compareTo(m.title) == 0)
				return true;
		}

		return false;
	}
	
	private Message findTimeById(String id, NodeList list) {

		for (int temp = 0; temp < list.getLength(); temp++) {
			Node nNode = (Node) list.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String timeId = eElement.getAttribute("base_NamedElement");
				if (timeId.equals(id)) {
					Message mes = new Message();

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

	public void getSender(String umlPath)
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(umlPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("message");
		NodeList timeList = doc.getElementsByTagName("GQAM:GaStep");
//		System.out.println("Sender message count : " + nList.getLength());
		
		messageList.clear();
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

					if (containDumplicate(mes))
						continue;

	//				System.out.println("title: " + mes.title + ", name: "
	//						+ mes.name + ", time: " + mes.time);
					messageList.add(mes);
				}

			}
		}

	//	System.out.println("valid count : " + messageList.size());
	}

	public ArrayList<Message> getMessageList() {
		return messageList;
	}

	public void setMessageList(ArrayList<Message> messageList) {
		this.messageList = messageList;
	}
	
	
}
