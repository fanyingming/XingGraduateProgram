package edu.buaa.sei.processes;

import java.util.Queue;

import edu.buaa.sei.instructions.CommuEntity;
import edu.buaa.sei.instructions.EmbeddedInstruction;
import edu.buaa.sei.instructions.Instruction;
import edu.buaa.sei.instructions.LogicalEntity;
import edu.buaa.sei.resource.AbstractResource;
import edu.buaa.sei.resource.active.ActiveResourceProcessor;

// Default process class simulating behavior of process.
public class SchedulableProcess extends AbstractResource implements ISchedulableProcess {

	private PROCESS_STATE state = PROCESS_STATE.READY;
	
	private boolean finished = false;
	
	// required process time.
	private int processorDemand = 0;
	
	// required storage size.
	private int storageDemand = 0;
	
	// required communication resource.
	private CommuEntity commuDemand = null;
	
	// required logical resource.
	private LogicalEntity logicalDemand = null;
	
	// resource type.
	private RESOURCE_TYPE resourceType = RESOURCE_TYPE.NONE;
		
	// the instruction processed currently.
	private EmbeddedInstruction instructions = null;
	
	// Processor processing this process.
	private ActiveResourceProcessor processor = null;
	
	// Switch time for process.
	private int switchTime;
		
	/**
	 * Constructor for this class.
	 * @param name
	 * @param id
	 * @param capacity
	 */
	public SchedulableProcess(String name, String id, int capacity) {
		super(name, id, capacity);
		instructions = new EmbeddedInstruction();
	}
	
	public SchedulableProcess(String name, String id, int capacity,
			Queue<Instruction> instructions) {
		super(name, id, capacity);
		this.instructions = new EmbeddedInstruction(instructions);
	}
	
	/**
	 * Start execution of this process.
	 */
	@Override
	public void start() {
		instructions.loadNextInstruction();
		recordAndLoadResourceDemand();
	}

	@Override
	public void active() {
		state = PROCESS_STATE.READY;
		throw new UnsupportedOperationException("Active not implemented.");
	}

	@Override
	public void passivate() {
		state = PROCESS_STATE.READY;
		throw new UnsupportedOperationException("Passivate not implemented.");
	}
	
	private void recordAndLoadResourceDemand() {
		while (resourceType == RESOURCE_TYPE.NONE) {
			processorDemand = instructions.getProcessorDemand();
			storageDemand = instructions.getStorageDemand();
			logicalDemand = instructions.getLogicDemand();
			commuDemand = instructions.getCommuDemand();
			resourceType = instructions.getResourceType();
			if (instructions.hasNext()) instructions.loadNextInstruction();
			else break;
		}
	}

	/**
	 * Proceed this process with passed processor time resource.
	 */
	@Override
	public int proceed(int time) {
		while ( time > 0 ) {
			// If allocated time is not enough.
			if (time < processorDemand) {
				processorDemand -= time;
				return -1;
			}
			
			time -= processorDemand;
			if (getResourceType() == RESOURCE_TYPE.PROCESSOR)
				recordAndLoadResourceDemand();
			else break;
		}
		return time;
	}
	
	@Override
	public void proceed() {
		// Before proceed, processorDemand must be zero.
		if (processorDemand > 0) throw new RuntimeException("invalide program");
		recordAndLoadResourceDemand();
	}

	/**
	 * Manipulate nested instructions.
	 */
	public Queue<Instruction> getInstructions() {
		return instructions.getNestedInstructions();
	}

	public void setInstructions(Queue<Instruction> instructions) {
		this.instructions.setNestedInstructions(instructions);
	}

	/**
	 * Test if this process has finished.
	 */
	@Override
	public boolean isFinished() {
		return finished;
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

	@Override
	public PROCESS_STATE getProcessState()  {
		return state;
	}

	@Override
	public void setProcessState(PROCESS_STATE state) {
		this.state = state;
	}

	@Override
	public ActiveResourceProcessor getProcessor() {
		return processor;
	}

	@Override
	public void setProcessor(ActiveResourceProcessor processor) {
		this.processor = processor;
	}

	public int getSwitchTime() {
		return switchTime;
	}

	public void setSwitchTime(int switchTime) {
		this.switchTime = switchTime;
	}
}
