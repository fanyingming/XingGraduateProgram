package edu.buaa.sei.instructions;

import java.util.Queue;

public class LoopInstruction extends AbstractInstruction {
	private Instruction internal = null;
	
	private int cycles = -1;
	
	public LoopInstruction(Instruction internal) {
		this.internal = internal;
	}

	@Override
	public boolean isAtomic() {
		return false;
	}

	@Override
	public void loadNextInstruction() {
		// assign an loop temporally.
		if (cycles == -1) cycles = 10;
		
		if (internal.isAtomic()) {
			
		} else {
			if (!internal.hasNext()) {
				cycles--;
				internal.reset();
			}
			internal.loadNextInstruction();
		}
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Queue<Instruction> getNestedInstructions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNestedInstructions(Queue<Instruction> instructions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean reset() {
		// TODO Auto-generated method stub
		return false;
	}

}
