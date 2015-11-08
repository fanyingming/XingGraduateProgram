package edu.buaa.sei.event;

import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.active.ActiveResourceStorage;

public class StorageFinishedEvent extends AbstractEvent {
	
	// storage resource associated with this event.
	private ActiveResourceStorage resource;
	
	// Process associated with this event. left for extension.
	private ISchedulableProcess process;
	
	/**
	 * Default constructor for this class.
	 * @param resource
	 * @param process
	 * @param time
	 */
	public StorageFinishedEvent(ActiveResourceStorage resource, 
			ISchedulableProcess process, int time) {
		super(time);
		this.resource = resource;
		this.process = process;
	}

	/**
	 * Forward the execution to next request.
	 */
	@Override
	public void eventRoutine() {
		process.getProcessor().process(process);
		resource.release(process);
	}	
}
