package edu.buaa.sei.event;

import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.active.ActiveResourceProcessor;

public class ProcessorFinishedEvent extends AbstractEvent {
	// processor produces this event.
	private ActiveResourceProcessor processor;
	
	// process execute this event.
	private ISchedulableProcess process;

	/**
	 * Default constructor of this class.
	 * @param processor
	 * @param process
	 */
	public ProcessorFinishedEvent(ActiveResourceProcessor processor,
			ISchedulableProcess process, int time) {
		super(time);
		this.process = process;
		this.processor = processor;
	}

	@Override
	public void eventRoutine() {
		// When process finished. Proceed with the next instruction.
		processor.dispatchProcess(process);
		processor.release(process);
	}
}
