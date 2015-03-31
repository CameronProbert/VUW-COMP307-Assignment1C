package main;

import java.util.Random;

public class Feature {

	// Constants
	private static final int SEED = 2015; // A random seed - this year
	private static final int NUM_FEATURE_POINTS = 4;

	private int[] row;
	private int[] col;
	private boolean[] sign;
	protected double weight;

	/**
	 * Create a Feature with values between the given with an height and adding
	 * the seed modifier to the random
	 * 
	 * @param width
	 * @param height
	 * @param seedModifier
	 */
	public Feature(int width, int height, int seedModifier) {
		Random random = new Random(SEED + seedModifier);
		this.weight = (random.nextDouble());
		populate(width, height, random);
	}

	/**
	 * Populates the fields
	 * @param width
	 * @param height
	 * @param random
	 */
	private void populate(int width, int height, Random random) {
		row = new int[NUM_FEATURE_POINTS];
		col = new int[NUM_FEATURE_POINTS];
		sign = new boolean[NUM_FEATURE_POINTS];
		for (int index = 0; index < NUM_FEATURE_POINTS; index++) {
			row[index] = random.nextInt(height);
			col[index] = random.nextInt(width);
			sign[index] = random.nextBoolean();
		}
	}

	/**
	 * Returns the weight
	 * @return
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Sets the weight
	 * @param newWeight
	 */
	public void setWeight(double newWeight) {
		weight = newWeight;
	}

	/**
	 * Evaluates the image
	 * @param image
	 * @return
	 */
	public double evaluate(PerceptronImage image) {
		int sum = 0;
		for (int i = 0; i < 4; i++) {
			if (image.getValue(col[i], row[i]) == sign[i]) {
				sum++;
			}
		}
		return (sum >= 3) ? 1 : 0;
	}

	/**
	 * Returns a string that represents the feature
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("Feature:");
		for (int index = 0; index < NUM_FEATURE_POINTS; index++) {
			sb.append("\nPixel " + index + ": " + col[index] + "," + row[index]
					+ " = " + sign[index]);
		}
		if (String.valueOf(weight).length() > 5) {
			sb.append("\nWeight: " + String.valueOf(weight).substring(0, 5));
		} else {
			sb.append("\nWeight: " + weight);
		}
		sb.append("\n");
		return sb.toString();
	}
}
