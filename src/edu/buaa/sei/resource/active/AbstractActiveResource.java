package edu.buaa.sei.resource.active;

import java.util.ArrayDeque;
import java.util.Queue;

import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.AbstractResource;

public abstract class AbstractActiveResource extends AbstractResource
		implements IActiveResource {

	protected Queue<ISchedulableProcess> waitingProcesses;
	
	public AbstractActiveResource(String name, String id, int capacity) {
		super(name, id, capacity);
		waitingProcesses = new ArrayDeque<ISchedulableProcess>();
	}
	
	public int getWaitingLength() {
		return waitingProcesses.size();
	}
	
	@Override
	public boolean canProcess(ISchedulableProcess process) {
		if (getCapacity() > 0) return true;
		return false;
	}
}
