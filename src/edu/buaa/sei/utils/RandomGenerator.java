package edu.buaa.sei.utils;

import java.util.Random;

public class RandomGenerator {
	public static double getARandomNum(double min, double max) {
		Random random = new Random();
		
		double s = (random.nextDouble()*max)%(max-min+1) + min;
		
		return s;
	}
	
	public static double getARandomNumD(double min, double max) {
		Random random = new Random();
		
		double s = random.nextDouble()*max;
		while (s < min)
			s = random.nextDouble()*max;
		
		return s;
	}
}
