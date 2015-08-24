package edu.buaa.sei.datamodel;

import java.util.ArrayList;

public class Process {
	public String name;
	public ArrayList<String> threads = new ArrayList<String>();
	
	public void addThreads(String str) {
		threads.add(str);
	}
}
