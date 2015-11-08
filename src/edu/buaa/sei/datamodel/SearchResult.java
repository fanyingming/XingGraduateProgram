package edu.buaa.sei.datamodel;

public class SearchResult {

	double fitness;
	double bestSR;
	double bestCost;
	int[] v;

	public SearchResult(double fitness, double bestSR, double bestCost, int[] v) {
		super();
		this.fitness = fitness;
		this.bestSR = bestSR;
		this.bestCost = bestCost;
		this.v = v;
	}

	public String getFitness() {
		return "" + fitness;
	}

	public String getBestSRAndCost() {
		return bestSR + "," + bestCost;
	}

	public String getV() {
		String temp = "" + this.v[0];
		for (int i = 1; i < this.v.length; i++) {
			temp = temp + "," + this.v[i];
		}
		return temp;
	}

}
