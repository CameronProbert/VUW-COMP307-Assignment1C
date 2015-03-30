package main;

import java.util.Random;

public class Feature {

	private static final int SEED = 2015;
	private static final double WEIGHT_MODIFIER = 0.5;

	private boolean dummy;

	private int numFeatures;
	private int[] row;
	private int[] col;
	private boolean[] sign;
	private double weight;
	private double[] featureWeights;

	public Feature(int width, int height, int numFeatures, boolean dummy,
			int seedModifier) {
		Random random = new Random(SEED + seedModifier);
		this.dummy = dummy;
		this.numFeatures = numFeatures;
		this.weight = (random.nextDouble()*2) - 1;
		populate(width, height, numFeatures, random);
	}

	private void populate(int width, int height, int numFeatures, Random random) {
		row = new int[numFeatures];
		col = new int[numFeatures];
		sign = new boolean[numFeatures];
		for (int index = 0; index < numFeatures; index++) {
			row[index] = random.nextInt(height);
			col[index] = random.nextInt(width);
			sign[index] = random.nextBoolean();
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
				sum++;
			}
		}
		return sum + weight >= 3;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("Feature:");
		for (int index = 0; index < numFeatures; index++) {
			sb.append("\nPixel " + index + ": " + col[index] + "," + row[index]
					+ " = " + sign[index]);
		}
		return sb.toString();
	}
}
