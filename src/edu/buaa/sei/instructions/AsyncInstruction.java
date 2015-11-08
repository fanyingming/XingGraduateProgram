package edu.buaa.sei.instructions;

import java.util.Queue;

public class AsyncInstruction extends AbstractInstruction {

	@Override
	public boolean isAtomic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadNextInstruction() {
		// TODO Auto-generated method stub
		
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
