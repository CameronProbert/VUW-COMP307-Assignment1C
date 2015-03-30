package main;

import java.util.Random;

public class Feature {

	private static final int SEED = 2015;
	private static final double WEIGHT_MODIFIER = 0.25;
	private static final int NUM_FEATURE_POINTS = 4;

	private boolean dummy;

	private int[] row;
	private int[] col;
	private boolean[] sign;
	private double weight;
	//private double[] featureWeights;

	public Feature(int width, int height, boolean dummy, int seedModifier) {
		Random random = new Random(SEED + seedModifier);
		this.dummy = dummy;
		this.weight = (random.nextDouble() * 2) - 1;
		populate(width, height, random);
	}

	private void populate(int width, int height, Random random) {
		row = new int[NUM_FEATURE_POINTS];
		col = new int[NUM_FEATURE_POINTS];
		sign = new boolean[NUM_FEATURE_POINTS];
		//featureWeights = new double[NUM_FEATURE_POINTS];
		for (int index = 0; index < NUM_FEATURE_POINTS; index++) {
			row[index] = random.nextInt(height);
			col[index] = random.nextInt(width);
			sign[index] = random.nextBoolean();
			//featureWeights[index] = 1;
		}
	}

	public void increaseWeight() {
		weight += WEIGHT_MODIFIER;
	}

	public void decreaseWeight() {
		weight -= WEIGHT_MODIFIER;
	}

	public boolean evaluate(PerceptronImage image) {
		if (dummy) {
			return true;
		}
		int sum = 0;
		for (int i = 0; i < 4; i++) {
			if (image.getValue(col[i], row[i]) == sign[i]) {
				int modifier = 1;
				if (!sign[i]) {
					modifier = modifier * -1;
				}
				sum += modifier * weight;
			}
		}
		return sum >= 3;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("Feature:");
		for (int index = 0; index < NUM_FEATURE_POINTS; index++) {
			sb.append("\nPixel " + index + ": " + col[index] + "," + row[index]
					+ " = " + sign[index]);
		}
		return sb.toString();
	}
}
