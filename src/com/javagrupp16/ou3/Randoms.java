package com.javagrupp16.ou3;

import java.util.List;
import java.util.Random;

/**
 * A utility class for Random methods.
 * Created by Mirrepirre on 2016-05-18.
 */
public class Randoms {

	private static final Random random = new Random();


	public static boolean chanceOf(double procent) {
		double rng = Math.random();
		return rng < (procent / 100);
	}


	public static <T> T randomItem(List<T> list) {
		int randomInt = random.nextInt(list.size());
		return list.get(randomInt);
	}

}
