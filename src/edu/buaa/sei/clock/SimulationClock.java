package edu.buaa.sei.clock;

public class SimulationClock {
	private int currentTime;
	
	public int getCurrentTime() {
		return currentTime;
	}
	
	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}
	
	public void updateCurrentTime(int plus) {
		this.currentTime += plus;
	}
}