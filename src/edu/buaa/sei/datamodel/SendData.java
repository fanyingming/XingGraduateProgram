package edu.buaa.sei.datamodel;

public class SendData {
	private int DataNum;
	
	private int DataSize;//kb
	
	private String dependencyDstId;

	public String getDependencyDstId() {
		return dependencyDstId;
	}

	public void setDependencyDstId(String dependencyDstId) {
		this.dependencyDstId = dependencyDstId;
	}

	public int getDataNum() {
		return DataNum;
	}

	public void setDataNum(int dataNum) {
		DataNum = dataNum;
	}

	public int getDataSize() {
		return DataSize;
	}

	public void setDataSize(int dataSize) {
		DataSize = dataSize;
	}
	
	
}
