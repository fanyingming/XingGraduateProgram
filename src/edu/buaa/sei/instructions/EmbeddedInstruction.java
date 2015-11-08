package edu.buaa.sei.instructions;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import edu.buaa.sei.processes.RESOURCE_TYPE;

/**
 * Class manages list of instructions.
 * @author sei
 */
public class EmbeddedInstruction extends AbstractInstruction {
	// list of instructions stored in this class.
	private Queue<Instruction> instructions = null;
	
	// Current instruction.
	private Instruction current = null;
	
	// Iterator for the internal queue.
	private Iterator<Instruction> iter = null;
	
	/**
	 * Constructor for this class.
	 */
	public EmbeddedInstruction() {
		instructions = new ArrayDeque<Instruction>();
		iter = instructions.iterator();
	}
	
	public EmbeddedInstruction(Queue<Instruction> instructions) {
		this.instructions = instructions;
		iter = instructions.iterator();
	}

	/**
	 * Test if this is atomic instruction.
	 */
	@Override
	public boolean isAtomic() {
		return false;
	}
	
	private void initResourceDemand() {
		this.setProcessorDemand(0);
		this.setStorageDemand(0);
		this.setCommuDemand(null);
		this.setLogicalDemand(null);
		this.setResourceType(RESOURCE_TYPE.NONE);
	}
	
	private void initResourceDemand(Instruction instr) {
		// Initialize resource type first.
		this.setResourceType(instr.getResourceType());
		
		// Set resource demand accordingly.
		this.setProcessorDemand(instr.getProcessorDemand());
		switch(instr.getResourceType()) {
			case PROCESSOR:
				// does nothing.
				break;
			case STORAGE:
				this.setStorageDemand(instr.getStorageDemand());
				break;
			case COMMU:
				this.setCommuDemand(instr.getCommuDemand());
				break;
			case LOGIC:
				this.setLogicalDemand(instr.getLogicDemand());
				break;
			default:
				throw new UnsupportedOperationException("Unsupported case occured.");
		}
	}
	
	/**
	 *	Test if this instruction have more. 
	 */
	public boolean hasNext() {
		if (current != null) return true;
		if (iter.hasNext()) return true;
		return false;
	}

	/**
	 * This instruction must be called before get resource demand.
	 */
	@Override
	public void loadNextInstruction() {
		// Initialize resource demand first.
		initResourceDemand();
		
		// If there's no more instruction, just return.
		if (null == current) {
			if (iter.hasNext()) current = iter.next();
			else return;
		}
		
		// If current instruction is not atomic, and there's no
		// more instruction in the embedded instruction. load next
		// instruction.
		if (!current.isAtomic() && !current.hasNext()) {
			if (!iter.hasNext()) return;
			current = iter.next();
			if (!current.isAtomic()) current.loadNextInstruction();
		}
		
		// Initialize resource demand.
		initResourceDemand(current);
		
		// Set next instruction accordingly.
		if (current.isAtomic()) {
			if (!iter.hasNext()) current = null;
			else current = iter.next();
		} else {
			if (current.hasNext()) current.loadNextInstruction();
			else current = null;
		}
	}

	/**
	 * Manipulate nested instructions.
	 */
	@Override
	public Queue<Instruction> getNestedInstructions() {
		return instructions;
	}

	@Override
	public void setNestedInstructions(Queue<Instruction> instructions) {
		this.instructions = instructions;
		iter = this.instructions.iterator();
	}

	@Override
	public boolean reset() {
		return false;
	}
}
