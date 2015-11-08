package edu.buaa.sei.instructions;

import edu.buaa.sei.resource.ResourceContainer;

/**
 * Entity stores communication resource requires.
 * @author sei
 *
 */
public class CommuEntity {
	private ResourceContainer from;
	private ResourceContainer to;
	private int size;
	
	public CommuEntity(ResourceContainer from, ResourceContainer to, int size) {
		this.from = from;
		this.to = to;
		this.size = size;
	}

	public ResourceContainer getFrom() {
		return from;
	}

	public void setFrom(ResourceContainer from) {
		this.from = from;
	}

	public ResourceContainer getTo() {
		return to;
	}

	public void setTo(ResourceContainer to) {
		this.to = to;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
