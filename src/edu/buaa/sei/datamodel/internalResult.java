package edu.buaa.sei.datamodel;

public class internalResult {
	public String from;
	public String to;
	public int sendDataSize;
	public int number;
	public int lostPackage1st;
	public int lostPackage2nd;
	public double time;
	public double reliability;
	
	public internalResult(String from, String to, int sendDataSize, int number, int lostPackage1st, int lostPackage2nd,
			double time,double reliability) {
		this.from = from;
		this.to = to;
		this.sendDataSize = sendDataSize;
		this.number = number;
		this.lostPackage1st = lostPackage1st;
		this.lostPackage2nd = lostPackage2nd;
		this.time = time;
		this.reliability = reliability;
	}
}
