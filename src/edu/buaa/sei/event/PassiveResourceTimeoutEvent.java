package edu.buaa.sei.event;

import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.passive.IPassiveResource;

public class PassiveResourceTimeoutEvent extends AbstractEvent {
	
	// The process requiring the resource.
	private ISchedulableProcess process;
	
	// The resource to be required.
	private IPassiveResource resource;
	
	// create this event with the corresponding resource and process.
	public PassiveResourceTimeoutEvent(ISchedulableProcess process,
			IPassiveResource resource, int time) {
		super(time);
		this.resource = resource;
		this.process = process;
	}

	@Override
	public void eventRoutine() {
		process.getProcessor().process(process);
		
		// remove notify of this process.
		resource.cancelWaiting(process);
	}
}
