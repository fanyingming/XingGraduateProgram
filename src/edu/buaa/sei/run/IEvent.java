package edu.buaa.sei.run;

import java.util.UUID;

public interface IEvent {
	/**
	 * Schedules this event to occur in delay simulated time units.
	 * @param process
	 * @param delay the period of simulated time to wait before this event is executed.
	 */
	public void schedule();
	
	/**
	 * cancel this event
	 */
	public void cancelEvent();
	
	/**
	 * return the time this event is about to occur.
	 * @return
	 */
	public int scheduledAtTime();
	
	/**
	 * Executes the simulation logic associated with this event.
	 * Notice, that this method is not intended to be called by clients. Instead,
	 * the event scheduler of the respective simulation library invokes this method
	 * as soon as the simulation is reached at which the event has bean scheduled.
	 * 
	 * @param process the process associated with this event.
	 */
	public void eventRoutine();
	
	/**
	 * Unique identifier of this event.
	 */
	public UUID getId();
}
