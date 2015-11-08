package edu.buaa.sei.instructions;

import java.util.Queue;

import edu.buaa.sei.processes.RESOURCE_TYPE;

public interface Instruction {
	
	/**
	 * Test if this instruction is nested.
	 * @return
	 */
	public boolean isAtomic();
	
	/**
	 * Test if this instruction is asynchronous message. 
	 * @return
	 */
	public boolean isAsync();
	
	/**
	 * Manipulate instruction command name.
	 * @return
	 */
	public String getName();

	public void setName(String name);
	
	/**
	 * Manipulate processor resource demand.
	 */
	public int getProcessorDemand();
	
	public void setProcessorDemand(int processorDemand);
	
	/**
	 * Manipulate storage resource demand.
	 * @return
	 */
	public int getStorageDemand();
	
	public void setStorageDemand(int storageDemand);
	
	/**
	 * Manipulate logical resource demand.
	 */
	public LogicalEntity getLogicDemand();
	
	public void setLogicalDemand(LogicalEntity logicalDemand);
	
	/**
	 * Manipulate communication demand.
	 * @return
	 */
	public CommuEntity getCommuDemand();
	
	public void setCommuDemand(CommuEntity commuDemand);
	
	/**
	 * manipulate resource type.
	 */
	public RESOURCE_TYPE getResourceType();
	
	public void setResourceType(RESOURCE_TYPE resourceType);
	
	/**
	 * Load next instruction before get any resource demand. This 
	 * is for embedded instruction.
	 */
	public void loadNextInstruction();
	
	public boolean hasNext();
	
	/**
	 * Manipulate list of instructions nested within this instruction. This
	 * is for embedded instruction.
	 * @return
	 */
	public Queue<Instruction> getNestedInstructions();
	
	public void setNestedInstructions(Queue<Instruction> instructions);
	
	/**
	 * Reset status of this instruction.
	 * @return
	 */
	public boolean reset();
}
