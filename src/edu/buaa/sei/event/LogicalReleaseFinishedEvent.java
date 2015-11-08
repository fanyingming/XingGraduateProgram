package edu.buaa.sei.event;

import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.passive.IPassiveResource;

public class LogicalReleaseFinishedEvent extends AbstractEvent {
	private IPassiveResource resource;
	
	private ISchedulableProcess process;
	

	public LogicalReleaseFinishedEvent(IPassiveResource resource, 
			ISchedulableProcess process, int time) {
		super(time);
		this.resource = resource;
		this.process = process;
	}

	@Override
	public void eventRoutine() {
		resource.release(process, process.getLogicDemand().getSize());
	}
}
