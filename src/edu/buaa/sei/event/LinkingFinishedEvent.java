package edu.buaa.sei.event;

import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.active.ActiveResourceLinking;

public class LinkingFinishedEvent extends AbstractEvent {

	private ActiveResourceLinking resource;
	
	private ISchedulableProcess process;
	
	public LinkingFinishedEvent(ActiveResourceLinking resource,
			ISchedulableProcess process, int time) {
		super(time);
		this.resource = resource;
		this.process = process;
	}

	@Override
	public void eventRoutine() {
		process.getProcessor().process(process);
		resource.release(process);
	}
}
