package edu.buaa.sei.resource.passive;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import edu.buaa.sei.controller.SimulationController;
import edu.buaa.sei.event.LogicalAcquireFinishedEvent;
import edu.buaa.sei.event.PassiveResourceTimeoutEvent;
import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.AbstractResource;

/**
 * Simulates a simple passive resource.
 * 
 * @author sei
 *
 */
public class PassiveResource extends AbstractResource implements IPassiveResource {
	
	public PassiveResource(String name, String id, int capacity) {
		super(name, id, capacity);
	}

	protected Queue<ISchedulableProcess> waitingProcesses;
	private int available;
	
	/**
	 * Test if this process can execute forward.
	 * @param process
	 * @param num
	 * @return
	 */
	private boolean canProceed(ISchedulableProcess process, int num) {
		if (waitingProcesses.isEmpty() || waitingProcesses.peek().equals(process))
			return num <= available;
		return false;
	}
	
	private void grantAccess(ISchedulableProcess process, int num) {
		available -= num;
		process.proceed();
		int time = SimulationController.instance.currentTime() + process.getProcessorDemand();
		LogicalAcquireFinishedEvent event = 
				new LogicalAcquireFinishedEvent(this, process, time);
		event.schedule();
	}
	
	//this is a sugar, add on later.
	private void processTimeout(double timeoutValue,
			ISchedulableProcess process) {
		if (timeoutValue <= 0.0) {
			throw new RuntimeException("timeout value invalide");
		} else {
			int time = (int) (SimulationController.instance.currentTime() + timeoutValue);
			PassiveResourceTimeoutEvent event = new PassiveResourceTimeoutEvent(
					process, this, time);
			event.schedule();
		}
	}
	
	/**
	 * Handle process require, timeout is not implemented current.
	 * @param process
	 * @param num
	 * @param timeout
	 * @param timeoutValue
	 * @return
	 */
	@Override
	public boolean acquire(ISchedulableProcess process, int num,
			double timeoutValue) {
		if (num > getCapacity()) {
			throw new RuntimeException("Too much resource required");
		}
		// grant num instance to process if available;
		if (canProceed(process, num)) {
			grantAccess(process, num);
			return true;
		} else {
			process.passivate();
			processTimeout(timeoutValue, process);
			waitingProcesses.add(process);
			return false;
		}
	}

	/**
	 * Release this resource.
	 * @param process
	 * @param num
	 */
	@Override
	public void release(ISchedulableProcess process, int num) {
		this.available += num;
		notifyWaitingProcesses();
	}
	
	private void notifyWaitingProcesses() {
		ISchedulableProcess process = waitingProcesses.peek();
		while (process != null && canProceed(process, process.getLogicDemand().getSize())) {
			grantAccess(process, process.getLogicDemand().getSize());
			waitingProcesses.remove();
			process.active();
			process = waitingProcesses.peek();
		}
	}

	@Override
	public int getAvailable() {
		return available;
	}
	
	@Override
	public void setAvailable(int available) {
		// TODO Auto-generated method stub
		this.available = available;
	}

	@Override
	public Queue<ISchedulableProcess> getWaitingProcesses() {
		Queue<ISchedulableProcess> queue = new ArrayDeque<ISchedulableProcess>();
		Iterator<ISchedulableProcess> iter = waitingProcesses.iterator();
		while (iter.hasNext()) {
			queue.add(iter.next());
		}
		return queue;
	}
	
	@Override
	public boolean isWaiting(ISchedulableProcess process) {
		return waitingProcesses.contains(process);
	}

	@Override
	public void cancelWaiting(ISchedulableProcess process) {
		if (waitingProcesses.contains(process))
			waitingProcesses.remove(process);
	}
}
