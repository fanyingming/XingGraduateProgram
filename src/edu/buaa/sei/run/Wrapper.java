package edu.buaa.sei.run;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.buaa.sei.datamodel.Message;
import edu.buaa.sei.datamodel.Process;

public class Wrapper {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		
		//setp 1: parse uml.
		NodeSend nodeSend = new NodeSend();
		nodeSend.parseNodeSend("CaseStudy/NodeSend.uml");
		String schedPolicy = nodeSend.getSchedPolicy();
		ArrayList<Process> processList = nodeSend.getpList();
		
		Publisher publisher = new Publisher();
		publisher.init();
		publisher.getPublisher("CaseStudy/publisher.uml");
		
		Transporter transporter = new Transporter();
		transporter.getTransporter("CaseStudy/transport.uml");
		
		//step 2: calculate time according to SchedPolicy type.
		System.out.println("-------------------------");
		double time = 0;
		
		//calculate process time, sub threads was merged.
		for (int i = 0; i < processList.size(); i++) {
			Process process = processList.get(i);
			double processTime = 0;
			ArrayList<String> threadList = process.getThreads();
			for (int j = 0; j < threadList.size(); j++) {
				String threadName = threadList.get(j);
				
				if (threadName.toLowerCase().compareTo("publish") == 0) {
					processTime += publisher.calculatePublishFIFO();
				} else if (threadName.toLowerCase().compareTo("send") == 0) {
					processTime += transporter.calculateTransportFIFO();
				} else {
					System.out.println("unknown thread name : " + threadName);
				}
			}
			processList.get(i).setNeedTime(processTime);
			processList.get(i).setTimeUsed(0);

			time += processTime;
		}
		//
		if (schedPolicy.compareTo("FIFO") == 0) {
			//just use time;
		} else if (schedPolicy.compareTo("TimeTableDriven") == 0) {
			int processNum = processList.size();
			double timePerProcess = 1000/(double)processNum;//ms
			//simulate time table.
			if (processNum <= 1) {
				//just use time;
			} else {
				time = 0;
				//invariant: if process queue still have process, executing goes on.
				while (processNum > 0) {
					for (int i = 0; i < processList.size(); i++) {
						if (processList.get(i).getNeedTime() <= 0) {//this process done.
							continue;
						} else if (processList.get(i).getNeedTime() > timePerProcess) {
							processList.get(i).setNeedTime(processList.get(i).getNeedTime() - timePerProcess);
							time += timePerProcess;
						} else {
							time += processList.get(i).getNeedTime();
							processList.get(i).setNeedTime(0);
							processList.get(i).setTimeUsed(time);
							processNum--;
							timePerProcess = 1000/(double)processNum;//ms
						}
					}
				}//while
			}
		}
		
		//step 3: print info.
		System.out.println("-------------------------");
		System.out.printf("%s done, time used: %.2f ms.\n", schedPolicy, time);
		for (int i = 0; i < processList.size(); i++) {
			System.out.printf("process %s, time used: %.2f ms\n" , processList.get(i).getName(), processList.get(i).getTimeUsed());
		}
	}
}
