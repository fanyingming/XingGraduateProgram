package edu.buaa.sei.event;

import java.util.UUID;

import edu.buaa.sei.controller.SimulationController;
import edu.buaa.sei.utils.util;

public abstract class AbstractEvent implements IEvent {
	// Unique identifier of this event.
	private UUID id;
	
	// time this event is about to finished.
	private int time;
	
	public AbstractEvent(int time) {
		this.id = util.generateId();
		this.time = time;
	}
	
	// Get identifier of this event.
	@Override
	public UUID getId() {
		return id;
	}
	
	/**
	 * Test the equal of two events.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IEvent)
			return ((IEvent) obj).getId() == getId();
		return false;
	}
	
	/**
	 * Put this event to event handler.
	 */
	@Override
	public void schedule() {
		SimulationController.instance.addEvent(this);
	}
	
	/**
	 * Time this event is about to vanish.
	 */
	@Override
	public int scheduledAtTime() {
		return time;
	}
	
	@Override
	public void cancelEvent() {
		// left empty.		
	}
}
