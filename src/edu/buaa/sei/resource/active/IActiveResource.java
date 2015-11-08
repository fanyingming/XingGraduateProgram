package edu.buaa.sei.resource.active;

import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.IResource;

/**
 * An active resource can execute demands of schedulabe processes. Active
 * resource are shared by multiple processes so that they need to use
 * scheduling strategies to assign processing time of the resources to process.
 * @author sei
 */
public interface IActiveResource extends IResource {
	
	/**
	 * process the specified demand of the process.
	 */
	public void process(ISchedulableProcess process);
	
	/**
	 * Release the resource.
	 */
	public void release(ISchedulableProcess process);
	
	/**
	 * 
	 */
	public boolean canProcess(ISchedulableProcess process);
	
	/**
	 * Creates the initial events for the resource.
	 */
	public void start();
	
	/**
	 * Creates the resume events for the resource.
	 */
	public void resume();
	
	/**
	 * Creates the terminate events for the resource.
	 */
	public void stop();
}
