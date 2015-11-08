package edu.buaa.sei.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.buaa.sei.instructions.CommuEntity;
import edu.buaa.sei.instructions.LogicalEntity;
import edu.buaa.sei.loadBalancer.ILoadBalancer;
import edu.buaa.sei.loadBalancer.LoadBalancer;
import edu.buaa.sei.processes.ISchedulableProcess;
import edu.buaa.sei.resource.active.ActiveResourceLinking;
import edu.buaa.sei.resource.active.ActiveResourceProcessor;
import edu.buaa.sei.resource.active.ActiveResourceStorage;
import edu.buaa.sei.resource.passive.IPassiveResource;

/**
 * ResourceContainer contains resources within a node.
 * Do not support nested container currently.
 * @author sei
 *
 */
public class ResourceContainer extends AbstractResource {
	// list of processes in this container.
	private List<ISchedulableProcess> processes;
	
	// list of processors.
	private List<ActiveResourceProcessor> processorResources;
	
	// storage resource, only one in container.
	private ActiveResourceStorage storageResource;
	
	// list of communication resource
	private List<ActiveResourceLinking> commuResources;
	
	// list of passive resources, i.e. logical resource.
	private List<IPassiveResource> passiveResources;
	
	// load balancer manager processors of this container.
	private ILoadBalancer loadBalancer;
	
	// Public constructor.
	public ResourceContainer(String name, String id, int capacity) {
		super(name, id, capacity);
		processes = new ArrayList<ISchedulableProcess>();
		processorResources = new ArrayList<ActiveResourceProcessor>();
		storageResource = null;
		commuResources = new ArrayList<ActiveResourceLinking>();
		passiveResources = new ArrayList<IPassiveResource>();
		loadBalancer = new LoadBalancer(processorResources);
	}
	
	/**
	 * Manipulate method for loadBalancer.
	 */
	public void setLoadBalancer(ILoadBalancer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}
	
	public ILoadBalancer getLoadBalancer() {
		return this.loadBalancer;
	}
	
	/**
	 * Add processor to this container.
	 * @param resource
	 * @return
	 */
	public boolean addProcessorResource(ActiveResourceProcessor processorResource) {
		if (processorResources.contains(processorResource)) return false;
		processorResources.add(processorResource);
		loadBalancer.addProcessor(processorResource);
		return true;
	}
	
	/**
	 * Set storage resource of this container.
	 */
	public void setStorageResource(ActiveResourceStorage storageResource) {
		this.storageResource = storageResource;
	}
	
	/**
	 * Add communication resource to this container.
	 */
	public boolean addCommuResource(ActiveResourceLinking commuResource) {
		if (commuResources.contains(commuResource)) return false;
		commuResources.add(commuResource);
		return true;
	}
	
	/**
	 * Add passive resource to this container.
	 */
	public boolean addPassiveResource(IPassiveResource resource) {
		if (passiveResources.contains(resource)) return false;
		passiveResources.add(resource);
		return true;
	}
	
	/**
	 * Add process into this container.
	 */
	public void addProcess(ISchedulableProcess process) {
		processes.add(process);
		//loadBalancer.addProcess(process);
	}
	
	/**
	 * Start the resources in this container.
	 */
	public void start() {
		// start all processors.
		Iterator<ActiveResourceProcessor> processorIter = processorResources.iterator();
		while (processorIter.hasNext()) {
			processorIter.next().start();
		}
		
		// start storage resource.
		
		// start communication resource.
		
		// Insert all processes into load balancer for process dispatch.
		Iterator<ISchedulableProcess> processIter = processes.iterator();
		while (processIter.hasNext()) {
			ISchedulableProcess process = processIter.next();
			process.start();
			loadBalancer.addProcess(process);
		}
	}
	
	/**
	 * Find the linking resource.
	 * @param container
	 * @return
	 */
	private ActiveResourceLinking getLinkingResource(ResourceContainer container) {
		Iterator<ActiveResourceLinking> iter = commuResources.iterator();
		while (iter.hasNext()) {
			ActiveResourceLinking resource = iter.next();
			if (resource.getTo().equals(container)) return resource;
			if (resource.getFrom().equals(container)) return resource;
		}
		return null;
	}
	
	/**
	 * Find the logical resource.
	 */
	private IPassiveResource getPassiveResource(String resourceId) {
		IPassiveResource resource = null;
		Iterator<IPassiveResource> iter = passiveResources.iterator();
		while (iter.hasNext()) {
			resource = iter.next();
			if (resource.getId() == resourceId) return resource;
		}
		return null;
	}
	
	
	/**
	 * Allocate ISchedulableProcess to its correspondence demanded resource
	 * waiting list.
	 * @param process
	 * @return
	 */
	public boolean rescheduleProcess(ISchedulableProcess process) {
		if (process.getProcessorDemand() > 0) {
			if (process.getProcessor() != null)
				process.getProcessor().process(process);
			else {
				process.start();
				loadBalancer.addProcess(process);
			}
		}
		switch (process.getResourceType()) {
			case PROCESSOR:
				throw new RuntimeException("Invalide resource required");
			case STORAGE:
				storageResource.process(process);
				break;
			case COMMU:
				CommuEntity demand = process.getCommuDemand();
				ResourceContainer container = demand.getTo();
				ActiveResourceLinking resource = getLinkingResource(container);
				if (resource == null) throw new RuntimeException("Invalide resource required");
				resource.process(process);
				break;
			case LOGIC:
				LogicalEntity logicalEntity = process.getLogicDemand();
				IPassiveResource logicalResource = 
						getPassiveResource(logicalEntity.getResourceId());
				if (logicalResource == null) throw new RuntimeException("Invalide resource required");
				logicalResource.acquire(process, logicalEntity.getSize(), -1);
				break;
			case NONE:
				// The termination of this process. Left empty.				
			default:
				// This should never happen.
				throw new RuntimeException("This should never happend");
		}
		return true;
	}
}
