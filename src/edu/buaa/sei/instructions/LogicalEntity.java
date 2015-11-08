package edu.buaa.sei.instructions;

/**
 * Entity stores logical resource requirement.
 * @author sei
 *
 */
public class LogicalEntity {
	private int size;
	private String resourceId;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public LogicalEntity(String resourceId, int size) {
		this.size = size;
		this.resourceId = resourceId;
	}
}
