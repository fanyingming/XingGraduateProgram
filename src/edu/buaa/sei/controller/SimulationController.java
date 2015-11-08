package edu.buaa.sei.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import edu.buaa.sei.clock.SimulationClock;
import edu.buaa.sei.event.IEvent;
import edu.buaa.sei.resource.ResourceContainer;

/**
 * Class controls simulation.
 * @author sei
 *
 */
public class SimulationController implements ISimulationController {
	// containers in this simulation
	private List<ResourceContainer> containers;
	
	// event list
	private PriorityQueue<IEvent> eventList;
	
	// single instance in this simulation engine.
	public static SimulationController instance = new SimulationController();
	
	public SimulationClock clock;
	
	// creation of this class is forbidden.
	private SimulationController() {
		eventList = new PriorityQueue<IEvent>(1, new Comparator<IEvent>() {
			public int compare(IEvent first, IEvent second) {
				if (first.scheduledAtTime() - second.scheduledAtTime() < 0) return -1;
				if (first.scheduledAtTime() - second.scheduledAtTime() == 0) return 0;
				return 1;
			}
		});
		eventList.clear();
		
		clock = new SimulationClock();
		containers = new ArrayList<ResourceContainer>();
	}
	
	// get current time;
	@Override
	public int currentTime() {
		return clock.getCurrentTime();
	}
	
	// add container
	@Override
	public boolean addContainer(ResourceContainer container) {
		if (containers.contains(container)) return false;
		containers.add(container);
		return true;
	}
		
	// add event to event list.
	@Override
	public void addEvent(IEvent event) {
		eventList.add(event);
	}
	
	// start simulation
	@Override
	public void start() {
		// start all the container.
		Iterator<ResourceContainer> iter = containers.iterator();
		while (iter.hasNext()) {
			iter.next().start();
		}
		
		// Iterate all the event to process.
		while(!eventList.isEmpty()) {
			IEvent event = eventList.remove();
			clock.setCurrentTime(event.scheduledAtTime());
			event.eventRoutine();
		}
	}
	
	// stop simulation
	@Override
	public void stop() {
		// left empty.
	}
	
	// pause simulation
	@Override
	public void pause() {
		// left empty.
	}
	
	// resume simulation
	@Override
	public void resume() {
		// left empty.
	}
}
