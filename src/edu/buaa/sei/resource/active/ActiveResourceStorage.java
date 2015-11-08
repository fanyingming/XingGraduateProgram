package edu.buaa.sei.resource.active;

import edu.buaa.sei.controller.SimulationController;
import edu.buaa.sei.event.StorageFinishedEvent;
import edu.buaa.sei.processes.ISchedulableProcess;

public class ActiveResourceStorage extends AbstractActiveResource{

	//speed of this storage resource.
	int speed = 10;
	
	// headTime of this storage resource.
	int headTime = 0;
	
	public ActiveResourceStorage(String name, String id) {
		super(name, id, 1);
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
		
		ISchedulableProcess process = waitingProcesses.remove();
		int time = calculateTime(process.getStorageDemand()) +
				SimulationController.instance.currentTime();
		process.proceed();
		StorageFinishedEvent event = new StorageFinishedEvent(this, process, time);
		event.schedule();
	}

	@Override
	public void start() {
		// left empty
	}

	@Override
	public void resume() {
		// left empty
	}

	@Override
	public void stop() {
		// left empty
	}

	@Override
	public void release(ISchedulableProcess process) {
		setCapacity(1);
	}
}
