package edu.buaa.sei.loadBalancer;

import java.util.List;

import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.active.ActiveResourceProcessor;

/**
 * Load balancer for new created process only. Process is executed
 * in the same processor until finished. Currently, the load balancer
 * does not consume time.
 * @author sei
 *
 */
public interface ILoadBalancer {	
	/**
	 * Add a processor to this load balancer. If this processor has been
	 * added before, return false. Otherwise return true.
	 * @param processor
	 * @return
	 */
	public boolean addProcessor(ActiveResourceProcessor processor);
	
	/**
	 * 
	 * @param process
	 * @return
	 */
	public void addProcess(ISchedulableProcess process);
}
