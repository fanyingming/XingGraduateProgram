package edu.buaa.sei.event;

import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.passive.PassiveResource;

public class LogicalAcquireFinishedEvent extends AbstractEvent {
	@SuppressWarnings("unused")
	private PassiveResource resource;
	
	private ISchedulableProcess process;
	
	public LogicalAcquireFinishedEvent(PassiveResource resource, 
			ISchedulableProcess process, int time) {
		super(time);
		
		this.resource = resource;
		this.process = process;
	}

	@Override
	public void eventRoutine() {
		process.getProcessor().process(process);
	}
}
