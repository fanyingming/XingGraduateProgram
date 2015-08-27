package edu.buaa.sei.datamodel;

import edu.buaa.sei.run.Publisher;

public class Dependency {
	private Publisher srcPublisher;
	private Publisher dstPublisher;
	
	private SendData sendData;
	
	private double time;
	
	private double reliability;
	
	private LostPackage lostPackage;
	
	public double getReliability() {
		return reliability;
	}
	public void setReliability(double reliability) {
		this.reliability = reliability;
	}
	public SendData getSendData() {
		return sendData;
	}
	public void setSendData(SendData sendData) {
		this.sendData = sendData;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	
	public LostPackage getLostPackage() {
		return lostPackage;
	}
	public void setLostPackage(LostPackage lostPackage) {
		this.lostPackage = lostPackage;
	}
	public Publisher getSrcPublisher() {
		return srcPublisher;
	}
	public void setSrcPublisher(Publisher srcPublisher) {
		this.srcPublisher = srcPublisher;
	}
	public Publisher getDstPublisher() {
		return dstPublisher;
	}
	public void setDstPublisher(Publisher dstPublisher) {
		this.dstPublisher = dstPublisher;
	}
	
	
}
