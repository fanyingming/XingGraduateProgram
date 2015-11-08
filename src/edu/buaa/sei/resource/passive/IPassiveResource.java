package edu.buaa.sei.resource.passive;

import java.util.Queue;

import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.IResource;

/**
 * A passive can be hold by a process for some time. As the number of available
 * instances is limited the process might has to wait until it gets the
 * requested number of instances.
 * @author sei
 */
public interface IPassiveResource extends IResource{
	
	/**
	 * Acquires num instances of the passive resources for the given process. The
	 * process is blocked until it successfully receive the requested number of
	 * resource instances.
	 * 
	 * @param process
	 * @param num
	 * @param timeout
	 * @param timeoutValue
	 * @return
	 */
	public boolean acquire(ISchedulableProcess process, int demand,
			double timeoutValue);

	/**
	 * Releases num instances of the passive resource form the given process.
	 * @param process
	 * @param num
	 */
	public void release(ISchedulableProcess process, int num);
	
	/**
	 * returns the maximal number of instances that can be acquired at the same
	 * time. 
	 * @return
	 */
	public int getCapacity();
	
	public void setCapacity(int capacity);
	
	/**
	 * Returns the number of remaining instances.
	 * @return
	 */
	public int getAvailable();
	
	public void setAvailable(int available);
		
	/**
	 * Return a queue containing the waiting processes for this passive resource.
	 */
	public Queue<ISchedulableProcess> getWaitingProcesses();
	
	/**
	 * Test if this process is waiting
	 * @param process
	 * @return
	 */
	public boolean isWaiting(ISchedulableProcess process);
	
	public void cancelWaiting(ISchedulableProcess process);
}
