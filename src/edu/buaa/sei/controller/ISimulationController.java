package edu.buaa.sei.controller;

import edu.buaa.sei.event.IEvent;
import edu.buaa.sei.resource.ResourceContainer;

/**
 * Interface for controlling the simulation.
 * @author sei
 *
 */
public interface ISimulationController {
	
	boolean addContainer(ResourceContainer container);
	
	void addEvent(IEvent event);
	
	int currentTime();
	
	void pause();
	
	void resume();
	
	void start();
	
	void stop();
}
