package model.sudokuGenerator.generatorFunction;

import java.util.Random;

public abstract class RandomNumbers {
	private static Random random = new Random();

	/**
	 * @param Upper boundary minus one
	 * @return Random int between 0 and upper boundary minus one
	 */
	public static int randomIntFrom0ToParameterMinusOne(int para) {
		return random.nextInt(para);
	}

	/**
	 * @param Lower boundary
	 * @param Upper boundary
	 * @return Random int
	 */
	public static int randomIntBetween(int min, int max) {
		if (max < min) {
			int save = max;
			max = min;
			min = save;
		}

		return random.nextInt(max - min) + min + 1;
	}
}
