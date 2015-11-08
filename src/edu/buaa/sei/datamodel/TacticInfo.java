package edu.buaa.sei.datamodel;

public class TacticInfo {
	int tacticType;
	int minRedNum, maxRedNum;

	public TacticInfo(int tacticType, int minRedNum, int maxRedNum) {
		super();
		this.tacticType = tacticType;
		this.minRedNum = minRedNum;
		this.maxRedNum = maxRedNum;
	}

	public int getTacticType() {
		return tacticType;
	}

	public int getMinRedNum() {
		return minRedNum;
	}

	public int getMaxRedNum() {
		return maxRedNum;
	}

}
