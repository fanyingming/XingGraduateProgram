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
import edu.buaa.sei.utils.RandomGenerator;
import edu.buaa.sei.utils.StringHandle;

public class Transporter {
	// FIFO
	// FixedPriority
	// TimeTableDrivenff
	private String schedPolicy;

	private double paskageUnitSize = 1.4;

	private double totalsize;// kb

	private double minKb;

	private double maxKb;

	public void getTransporter(String umlPath)
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
	//						System.out.println("size:" + size_kb + "kb");
							break;
						}
					}
				}

				if (size_kb != -1)
					break;
			}
		}

		// get MaxThroughPut and MinThroughput
		NodeList throughPutList = doc
				.getElementsByTagName("Profile:SendMessage");
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
								String maxStr = ((Element) node)
										.getAttribute("precision");
	//							System.out.println("max:" + maxStr);

								maxThroughPut = Double.valueOf(maxStr);
							}
						}

						if (node.getNodeType() == Node.ELEMENT_NODE) {
							if (node.getNodeName().equals("MinThroughput")) {
								String minStr = ((Element) node)
										.getAttribute("precision");
//								System.out.println("min:" + minStr);

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
//		int packageNum = (int) Math.ceil(totalsize / paskageUnitSize);
		double bandWidth = RandomGenerator.getARandomNum(minKb, maxKb);
		
		time = (totalsize / bandWidth);
		System.out.println("transport time used " + time + "ms.");

		return time;
	}

	public double speedMin() {
		return (double)totalsize/maxKb;
	}
	public double speedMax() {
		return (double)totalsize/minKb;
	}
}
