package edu.buaa.sei.processes;

import java.util.Queue;

import edu.buaa.sei.instructions.CommuEntity;
import edu.buaa.sei.instructions.Instruction;
import edu.buaa.sei.instructions.LogicalEntity;
import edu.buaa.sei.resource.IResource;
import edu.buaa.sei.resource.active.ActiveResourceProcessor;

public interface ISchedulableProcess extends IResource {
	/**
	 * Notifies the process to resume its execution. Only called if
	 * has been passivated.
	 */
	public void active();
	
	/**
	 * Notifies the process to stop its execution. 
	 */
	public void passivate();
	
	/**
	 * Notifies the process to start its execution.
	 */
	public void start();
	
	/**
	 * Test if this process has finished.
	 * @return
	 */
	public boolean isFinished();

	/**
	 * Proceed this process with passed processor time resource.
	 * If the passed time resource is all consumed, return -1,
	 * else return the left time resource not used.
	 * @param processed
	 * @return
	 */
	public int proceed(int time);
	
	/**
	 * Proceed this process, satisfying all its request. And forward
	 * with next resource require.
	 */
	public void proceed();
	
	/**
	 * Manipulate process state.
	 */
	public PROCESS_STATE getProcessState();
	
	public void setProcessState(PROCESS_STATE state);
	
	/**
	 * Manipulate Embedded instructions.
	 */
	public Queue<Instruction> getInstructions();
	
	public void setInstructions(Queue<Instruction> instr);
	
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
	 * Manipulate resource type.
	 */
	public RESOURCE_TYPE getResourceType();
	
	public void setResourceType(RESOURCE_TYPE resourceType);
	
	/**
	 * Manipulate processor.
	 */
	public ActiveResourceProcessor getProcessor();
	
	public void setProcessor(ActiveResourceProcessor processor);
}
