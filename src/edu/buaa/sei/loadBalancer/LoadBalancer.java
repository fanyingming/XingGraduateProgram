package edu.buaa.sei.loadBalancer;

import java.util.Iterator;
import java.util.List;

import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.active.ActiveResourceProcessor;

/**
 * Load balancer for new created process only. Process is executed
 * in the same processor until finished. In each computation node,
 * there could be many processors but only one storage resource.
 * There may be many communication resource, but it's not allocated
 * by balancer, but allocated by developer.
 * @author sei
 *
 */
public class LoadBalancer implements ILoadBalancer {
	
	List<ActiveResourceProcessor> processors;
	
	public LoadBalancer(List<ActiveResourceProcessor> processors) {
		this.processors = processors;
	}

	@Override
	public boolean addProcessor(ActiveResourceProcessor processor) {
		if (processors.contains(processor))
			return false;
		processors.add(processor);
		return true;
	}
	
	@Override
	public void addProcess(ISchedulableProcess process) {
		Iterator<ActiveResourceProcessor> iter = processors.iterator();
		int length = Integer.MAX_VALUE;
		ActiveResourceProcessor processor = null, tmp = null;
		while (iter.hasNext()) {
			tmp = iter.next();
			if (length > tmp.getWaitingLength()) {
				processor = tmp;
				length = tmp.getWaitingLength();
			}
		}
		process.setProcessor(processor);
		processor.process(process);
	}
}