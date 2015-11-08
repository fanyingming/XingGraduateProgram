package edu.buaa.sei.event;

import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.active.ActiveResourceProcessor;

public class DispatchStartEvent extends AbstractEvent {

	private ActiveResourceProcessor processor;
	
	private ISchedulableProcess process;
	
	public DispatchStartEvent(ActiveResourceProcessor processor,
			ISchedulableProcess process, int time) {
		super(time);
		
		this.process = process;
		this.processor = processor;
	}

	@Override
	public void eventRoutine() {
		processor.dispatchProcess(process);
	}
}
