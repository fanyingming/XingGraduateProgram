package edu.buaa.sei.utils;

import java.util.Random;

public class RandomGenerator {
	public static double getARandomNum(double min, double max) {
		Random random = new Random();
		
		double s = (random.nextDouble()*max)%(max-min+1) + min;
		
		return s;
	}
}
