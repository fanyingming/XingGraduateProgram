package edu.buaa.sei.instructions;

import java.util.Queue;

public class BranchInstruction extends AbstractInstruction {
	private Queue<Instruction> branches = null;
	
	private Queue<Double> probability = null;
	
	Instruction chosenBranch = null;
	
	public Queue<Instruction> getBranches() {
		return branches;
	}

	public void setBranches(Queue<Instruction> branches) {
		this.branches = branches;
	}

	public Queue<Double> getProbability() {
		return probability;
	}

	public void setProbability(Queue<Double> probability) {
		this.probability = probability;
	}

	public BranchInstruction(Queue<Instruction> branches, Queue<Double> probability) {
		this.branches = branches;
		this.probability = probability;		
	}

	@Override
	public boolean isAtomic() {
		return false;
	}

	@Override
	public void loadNextInstruction() {
		if (chosenBranch == null) {
			// chose one temporally.
			chosenBranch = branches.peek();
		}
		
		if (chosenBranch.isAtomic()) {
			
		} else {
			
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
