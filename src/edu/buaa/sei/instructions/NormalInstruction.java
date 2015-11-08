package edu.buaa.sei.instructions;

import java.util.Queue;

public class NormalInstruction extends AbstractInstruction {
	/**
	 * Method test if this is atomic instruction.
	 */
	@Override
	public boolean isAtomic() {
		return true;
	}
	
	/**
	 * Get nested instructions.
	 */
	@Override
	public Queue<Instruction> getNestedInstructions() {
		throw new UnsupportedOperationException("getNestedInstructions operation unsupported");
	}

	@Override
	public void loadNextInstruction() {
		throw new UnsupportedOperationException("loadNextInstruction operation unsupported");
	}

	@Override
	public void setNestedInstructions(Queue<Instruction> instructions) {
		throw new UnsupportedOperationException("setNestedInstructions operation unsupported");
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public boolean reset() {
		return true;
	}
}
