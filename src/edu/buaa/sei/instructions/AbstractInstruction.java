package edu.buaa.sei.instructions;

import edu.buaa.sei.processes.RESOURCE_TYPE;

/**
 * Abstract instruction implements basic methods.
 * @author sei
 *
 */
public abstract class AbstractInstruction implements Instruction {
	// required process time.
	private int processorDemand;
	
	// required storage size.
	private int storageDemand;
	
	// required communication resource.
	private CommuEntity commuDemand;
	
	// required logical resource.
	private LogicalEntity logicalDemand;
	
	// name of this instruction.
	private String name;
	
	// Is asynchronous message or not
	private boolean isAsync;
	
	// resource type.
	private RESOURCE_TYPE resourceType;
	
	@Override
	public boolean isAsync() {
		return isAsync;
	}

	/**
	 * Get name of this instruction.
	 */
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getProcessorDemand() {
		return processorDemand;
	}

	@Override
	public int getStorageDemand() {
		return storageDemand;
	}

	@Override
	public CommuEntity getCommuDemand() {
		return commuDemand;
	}

	@Override
	public LogicalEntity getLogicDemand() {
		return logicalDemand;
	}

	@Override
	public void setProcessorDemand(int processorDemand) {
		this.processorDemand = processorDemand;
	}

	@Override
	public void setStorageDemand(int storageDemand) {
		this.storageDemand = storageDemand;
	}

	@Override
	public void setLogicalDemand(LogicalEntity logicalDemand) {
		this.logicalDemand = logicalDemand;	
	}

	@Override
	public void setCommuDemand(CommuEntity commuDemand) {
		this.commuDemand = commuDemand;
	}

	@Override
	public RESOURCE_TYPE getResourceType() {
		return resourceType;
	}

	@Override
	public void setResourceType(RESOURCE_TYPE resourceType) {
		this.resourceType = resourceType;		
	}
}
