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
import edu.buaa.sei.utils.StringHandle;

public class Publisher {

	private String publisherName;
	
	private String publisherId;
	
	private ArrayList<String> sonId = new ArrayList<String> ();

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public ArrayList<String> getSonId() {
		return sonId;
	}

	public void setSonId(ArrayList<String> sonId) {
		this.sonId = sonId;
	}
	
	
	
}
