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

import edu.buaa.sei.datamodel.Dependency;
import edu.buaa.sei.datamodel.LostPackage;
import edu.buaa.sei.datamodel.Message;
import edu.buaa.sei.datamodel.SendData;
import edu.buaa.sei.datamodel.Sender;
import edu.buaa.sei.utils.RandomGenerator;
import edu.buaa.sei.utils.StringHandle;

import edu.buaa.sei.datamodel.*;
public class DDS {
	ArrayList<Dependency> dependencyList = new ArrayList<Dependency>();
	
	ArrayList<Publisher> publisherList = new ArrayList<Publisher>();
	
	ArrayList <LostPackage> lostPackageList = new ArrayList <LostPackage>();
	
	ArrayList <SendData> sendDataList = new ArrayList <SendData>();
	
	public Publisher getPublisherByBonId(String sonId) {
		for (int i = 0; i < publisherList.size(); i++) {
			Publisher pub = publisherList.get(i);
			for (int j = 0; j < pub.getSonId().size(); j++) {
				String sonIdStr = pub.getSonId().get(j);
				
				if (sonIdStr.equals(sonId)) {
					return pub;
				}
			}
		}
		
		return null;
	}
	
	public LostPackage getLostPackageByDepId(String depID) {
		for (int i = 0; i < lostPackageList.size(); i++) {
			LostPackage lostPackage = lostPackageList.get(i);
			if (lostPackage.getBaseDependencyId().equals(depID)) {
				return lostPackage;
			}
		}
		
		return null;
	}
	
	public SendData getSendDataByDepDstId(String depID) {
		for (int i = 0; i < sendDataList.size(); i++) {
			SendData sendData = sendDataList.get(i);
			if (sendData.getDependencyDstId().equals(depID)) {
				return sendData;
			}
		}
		
		return null;
	}
	
	public void getAllDependency(String umlPath) 
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(umlPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("packagedElement");
		
		getALLPublisher(umlPath);
		getAllLostPackage(umlPath);
		getAllSendData(umlPath);
		
		dependencyList.clear();
		// scan xml and get all valid message.
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = (Node) nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String type = eElement.getAttribute("xmi:type");
				if (type.equals("uml:Dependency")) {
					Dependency dep = new Dependency();
					String srcId = eElement.getAttribute("client");
					String dstId = eElement.getAttribute("supplier");
					
					Publisher srcPub = getPublisherByBonId(srcId);
					Publisher dstPub = getPublisherByBonId(dstId);
					
					dep.setSrcPublisher(srcPub);
					dep.setDstPublisher(dstPub);
					
					String depId = eElement.getAttribute("xmi:id");
					dep.setLostPackage(getLostPackageByDepId(depId));
					
					dep.setSendData(getSendDataByDepDstId(srcPub.getPublisherId()));
					
					dependencyList.add(dep);
				}

			}
		}
		
		
	}
	
	public void printDependencyInfo() {
		System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n", "from", "to", "sendDataSize(KB)", "number", "lostPackage1st", "2nd", "time(ms)", "reliability(%)");
		for (int i = 0; i < dependencyList.size(); i++) {
			Dependency dep = dependencyList.get(i);
			System.out.printf("%s\t%s\t%14d\t%13d\t%12d\t%2d\t%.3f\t%18.3f\n",dep.getSrcPublisher().getPublisherName(), dep.getDstPublisher().getPublisherName()
					, dep.getSendData().getDataSize(), dep.getSendData().getDataNum()
					, dep.getLostPackage().getFirstLostPackage()
					, dep.getLostPackage().getSecondLostPackage()
					, dep.getTime(), dep.getReliability()*100);
		}
	}
	
	public void calculateDependency(Sender sender) {
		//calculate send time.
		
		//calculate transmite time.
		for (int i = 0; i < dependencyList.size(); i++) {
			//TODO: get the true value.
			Dependency dep = dependencyList.get(i);
			
			dep.setTime(RandomGenerator.getARandomNum(1, 10));
			dep.setReliability(RandomGenerator.getARandomNumD(0.8, 1));
		}
	}
	
	public double recursiveGetTime(Publisher pub) {
		double minTime = 99999999;
		
		for (int i = 0; i < dependencyList.size(); i++) {
			Dependency dep = dependencyList.get(i);
			if (dep.getDstPublisher().getPublisherId().equals(pub.getPublisherId())) {
				double curTime = dep.getTime() + recursiveGetTime(dep.getSrcPublisher());
				
				if (curTime <= minTime) {
					minTime = curTime;
				}
			}
		}
		if (minTime == 99999999)
			return 0;
		
		return minTime;
	}
	
	public double recursiveGetReliability(Publisher pub) {
		double reliability = 1;
		
		for (int i = 0; i < dependencyList.size(); i++) {
			Dependency dep = dependencyList.get(i);
			if (dep.getDstPublisher().getPublisherId().equals(pub.getPublisherId())) {
				reliability *= dep.getReliability() * recursiveGetReliability(dep.getSrcPublisher());
			}
		}
		
		return reliability;
	}
	
	public void calculateTime() {
		ArrayList<Publisher> leafPublisher = getLeafPublisher();
		
		for (int i = 0; i < leafPublisher.size(); i++) {
			Publisher pub = leafPublisher.get(i);
			
			double time = recursiveGetTime(pub);
			
			System.out.printf("%s : %.3f ms.\n", pub.getPublisherName(), time);
		}
	}
	
	public void calculateReliability() {
		ArrayList<Publisher> leafPublisher = getLeafPublisher();
		
		for (int i = 0; i < leafPublisher.size(); i++) {
			Publisher pub = leafPublisher.get(i);
			
			double reliability = recursiveGetReliability(pub);
			
			System.out.printf("%s : %.3f%%.\n", pub.getPublisherName(), reliability*100);
		}
	}
	
	public ArrayList<Result> showResults() {
		ArrayList<Publisher> leafPublisher = getLeafPublisher();
		ArrayList<Result> rl = new ArrayList<Result>();
		System.out.printf("\n%7s\t%10s\t%6s\n", "name", "reliability(%s)", "time(ms)");

		for (int i = 0; i < leafPublisher.size(); i++) {
			Publisher pub = leafPublisher.get(i);
			
			double reliability = recursiveGetReliability(pub);
			double time = recursiveGetTime(pub);
			
			System.out.printf("%7s\t%10.3f\t%6.3f\n", pub.getPublisherName(), reliability*100, time);

			Result res = new Result(pub.getPublisherName(), reliability*100, time);
			rl.add(res);
		}
		
		return rl;
	}
	
	
	public ArrayList<Publisher> getRootPublisher() {
		ArrayList<Publisher> rl = new ArrayList<Publisher>();
		
		return rl;
	}
	
	public boolean isLeafPublisher(Publisher pub) {
		for (int i = 0; i < dependencyList.size(); i++) {
			Dependency dep = dependencyList.get(i);
			
			if (dep.getSrcPublisher().getPublisherId().equals(pub.getPublisherId())) {
				return false;
			}
		}
		
		return true;
	}
	
	public ArrayList<Publisher> getLeafPublisher() {
		ArrayList<Publisher> rl = new ArrayList<Publisher>();
		for (int i = 0; i < publisherList.size(); i++) {
			Publisher pub = publisherList.get(i);
			
			if (isLeafPublisher(pub)) {
				rl.add(pub);
			}
		}
		
		return rl;
	}
	
	public void getAllLostPackage(String umlPath) throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(umlPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("Profile:lostpackage");
		
		lostPackageList.clear();
		// scan xml and get all valid message.
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = (Node) nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String baseDependencyId = eElement.getAttribute("base_Dependency");
				String firstLostPackageStr = eElement.getAttribute("FirstLostPackge");
				String secondLostPackageStr = eElement.getAttribute("SecondLostPackage");
				
				LostPackage lostPackage = new LostPackage();
				lostPackage.setBaseDependencyId(baseDependencyId);
				if (firstLostPackageStr != null) {
					if (firstLostPackageStr.length() != 0)
						lostPackage.setFirstLostPackage(Integer.valueOf(firstLostPackageStr));
				}
				if (secondLostPackageStr != null) {
					if (secondLostPackageStr.length() != 0)	
						lostPackage.setSecondLostPackage(Integer.valueOf(secondLostPackageStr));
				}
				
				lostPackageList.add(lostPackage);
			}
		}
	}
	
	public void getAllSendData(String umlPath) throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(umlPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("SW_Interaction:MessageComResource");
		
		sendDataList.clear();
		// scan xml and get all valid message.
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = (Node) nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String baseDependencyId = eElement.getAttribute("base_Classifier");
				
				Publisher pub = getPublisherByBonId(baseDependencyId);
				SendData sendData = new SendData();
				sendData.setDependencyDstId(pub.getPublisherId());
				
				int size = 0;
				int count = 0;
				for (Node node = nNode.getFirstChild(); node != null; node = node
						.getNextSibling()) {
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						if (node.getNodeName().equals("capacity")) {
							String str = node.getFirstChild().getNodeValue();
							String[] strList = str.split("\\*");
							if (strList.length < 2)
								continue;
							
							int count_t = Integer.valueOf(strList[1]);
							int sizeKB = StringHandle.getKbFromStr(strList[0]);
							
							size += sizeKB*count_t;
							count += count_t;
						}
					}
				}
				
				sendData.setDataSize(size);
				sendData.setDataNum(count);
				sendDataList.add(sendData);
			}
		}
	}
	
	public void getALLPublisher(String umlPath) 
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(umlPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("nestedClassifier");
		
		publisherList.clear();
		// scan xml and get all valid message.
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = (Node) nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String type = eElement.getAttribute("xmi:type");
				if (type.equals("uml:Device")) {
					Dependency dep = new Dependency();
					String id = eElement.getAttribute("xmi:id");
					String name = eElement.getAttribute("name");
					Publisher publisher = new Publisher();
					
					publisher.setPublisherId(id);
					publisher.setPublisherName(name);
					
					ArrayList<String> sonIdList = new ArrayList<String>();
					
					for (Node node = nNode.getFirstChild(); node != null; node = node
							.getNextSibling()) {
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							if (node.getNodeName().equals("nestedClassifier")) {
								if (((Element)node).getAttribute("xmi:type").equals("uml:Artifact")) {
									String sonId = ((Element)node).getAttribute("xmi:id");
									sonIdList.add(sonId);
								}
							}
						}
					}
					
					publisher.setSonId(sonIdList);
					
					publisherList.add(publisher);
				}

			}
		}
	}
	
	public void calculateDependencyTime() {
		
	}
	
	public void calculateDDSTime() {
		
	}
}
