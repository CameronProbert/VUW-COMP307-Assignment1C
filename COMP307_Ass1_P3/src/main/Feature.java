package main;

import java.util.Random;

public class Feature {

	private boolean dummy;

	private int numFeatures;
	private int[] row;
	private int[] col;
	private boolean[] sign;

	public Feature(int width, int height, int numFeatures, boolean dummy) {
		this.dummy = dummy;
		this.numFeatures = numFeatures;
		populate(width, height, numFeatures);
	}

	private void populate(int width, int height, int numFeatures) {
		Random random = new Random();
		row = new int[numFeatures];
		col = new int[numFeatures];
		sign = new boolean[numFeatures];
		for (int index = 0; index < numFeatures; index++) {
			row[index] = random.nextInt(height);
			col[index] = random.nextInt(width);
			sign[index] = random.nextBoolean();
		}
	}

	public boolean evaluate(PerceptronImage image) {
		if (dummy){
			return true;
		}
		int sum = 0;
		for (int i = 0; i < 4; i++) {
			if (image.getValue(col[i], row[i]) == sign[i]) {
				sum++;
			}
		}
		return sum >= 3;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("Feature:");
		for (int index = 0; index < numFeatures; index++) {
			sb.append("\nPixel " + numFeatures + ": " + col[index] + ","
					+ row[index]);
		}
		return sb.toString();
	}
}
