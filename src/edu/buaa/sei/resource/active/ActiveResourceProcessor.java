package edu.buaa.sei.resource.active;

import edu.buaa.sei.controller.SimulationController;
import edu.buaa.sei.event.ProcessorFinishedEvent;
import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.RESOURCE_STATE;
import edu.buaa.sei.resource.ResourceContainer;

/**
 * Default class simulation processor with sharing strategy.
 * @author sei
 */
public class ActiveResourceProcessor extends AbstractActiveResource {
		
	// Time slice for each process.
	int timeSlice = 20;
	
	// Switch time of this processor.
	int switchTime = 0;
	
	// Current state of resource.
	RESOURCE_STATE state;
	
	ResourceContainer container;
	
	public ActiveResourceProcessor(String name, String id,
			ResourceContainer container) {
		super(name, id, 1);
		this.container = container;
	}

	@Override
	public void process(ISchedulableProcess process) {
		waitingProcesses.add(process);
		process();
	}
	
	/**
	 * Process next process in the waiting list.
	 */
	private void process() {
		ISchedulableProcess process = waitingProcesses.peek();
		if (process == null) return;
		if (!canProcess(process)) return;
		// execute this process if processor is available.
		waitingProcesses.remove();
		setCapacity(0);
		int currentTime = SimulationController.instance.currentTime();
		int remainingTime = process.proceed(timeSlice);
		ProcessorFinishedEvent event = new ProcessorFinishedEvent(this, process,
				timeSlice - remainingTime + currentTime + switchTime);
		event.schedule();
	}
	
	/**
	 * Add this process to correspondence demand according to its resource demand.
	 */
	public void dispatchProcess(ISchedulableProcess process) {
		container.rescheduleProcess(process);
	}

	@Override
	public void start() {
		state = RESOURCE_STATE.RUN;
	}
	
	@Override
	public void resume() {
		state = RESOURCE_STATE.SUSPEND;
	}
	
	@Override
	public void stop() {
		state = RESOURCE_STATE.STOP;
	}

	@Override
	public void release(ISchedulableProcess process) {
		setCapacity(1);
		process();
	}
}
