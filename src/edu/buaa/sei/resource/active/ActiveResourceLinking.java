package edu.buaa.sei.resource.active;

import edu.buaa.sei.controller.SimulationController;
import edu.buaa.sei.event.LinkingFinishedEvent;
import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.ResourceContainer;

public class ActiveResourceLinking extends AbstractActiveResource implements Runnable {
	private ResourceContainer from;
	
	private ResourceContainer to;
	
	private int speed = 10;
	
	private int headTime = 0;

	public ActiveResourceLinking(String name, String id, int capacity,
			ResourceContainer from, ResourceContainer to) {
		super(name, id, capacity);
		this.from = from;
		this.to = to;
	}

	public ResourceContainer getFrom() {
		return from;
	}

	public void setFrom(ResourceContainer from) {
		this.from = from;
	}

	public ResourceContainer getTo() {
		return to;
	}

	public void setTo(ResourceContainer to) {
		this.to = to;
	}
	
	private int calculateTime(int demand) {
		return headTime + demand / speed;
	}

	@Override
	public void process(ISchedulableProcess process) {
		waitingProcesses.add(process);
		process();
	}
	
	private void process() {
		ISchedulableProcess process = waitingProcesses.peek();
		if (!canProcess(process)) return;
		waitingProcesses.remove();
		int time = calculateTime(process.getCommuDemand().getSize()) +
				SimulationController.instance.currentTime();
		process.proceed();
		LinkingFinishedEvent event = new LinkingFinishedEvent(this, process, time);
		event.schedule();
		setCapacity(0);
	}

	@Override
	public void start() {
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release(ISchedulableProcess process) {
		setCapacity(1);
		process();
	}
}
