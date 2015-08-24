package edu.buaa.sei.datamodel;

import java.util.ArrayList;

public class Process {
	public String name;
	public ArrayList<String> threads = new ArrayList<String>();
	private double needTime;
	private double timeUsed;
	
	public void addThreads(String str) {
		threads.add(str);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getThreads() {
		return threads;
	}

	public void setThreads(ArrayList<String> threads) {
		this.threads = threads;
	}

	public double getNeedTime() {
		return needTime;
	}

	public void setNeedTime(double needTime) {
		this.needTime = needTime;
	}

	public double getTimeUsed() {
		return timeUsed;
	}

	public void setTimeUsed(double timeUsed) {
		this.timeUsed = timeUsed;
	}
	
	
}
