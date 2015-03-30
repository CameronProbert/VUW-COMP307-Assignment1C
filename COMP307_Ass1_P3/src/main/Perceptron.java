package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Perceptron {

	private static final int NUM_FEATURES = 500;
	private static final int TIMES_TO_REPEAT = 1000;

	private Feature[] features;
	private List<PerceptronImage> images;

	private int width;
	private int height;

	public Perceptron(String[] filename) {
		width = 10;
		height = 10;
		File file = new File(filename[0]);
		loadImages(file);
		createFeatures();
		classifyImages();
	}	

	private void classifyImages() {
		double[] repPercentage = new double[TIMES_TO_REPEAT];
		for (int repetition = 0; repetition < TIMES_TO_REPEAT; repetition++) {
			int numCorrect = 0;
			int total = 0;
			double previousPercentage = 0;
			for (PerceptronImage image : images) {
				int numFeaturesCorrect = 0;
				for (Feature feature : features) {
					if (feature.evaluate(image)) {
						if (image.isX()) {
							numFeaturesCorrect++;
						} else {
							feature.increaseWeight();
						}
					} else {
						if (!image.isX()) {
							numFeaturesCorrect++;
						} else {
							feature.decreaseWeight();
						}
					}
				}
				if (numFeaturesCorrect >= NUM_FEATURES * 75.0 / 100.0) {
					numCorrect++;
				}
				total++;
				double percentage = numCorrect / (double) total;
//				System.out.println("Total images: " + total);
//				System.out
//						.println("Number classified correctly: " + numCorrect);
//				System.out.println("Percentage correct: " + percentage);
//				System.out.println("Percentage change: " + (percentage - previousPercentage));
				previousPercentage = percentage;
			}
			// System.out.println("Repetition: " + repetition);
			// System.out.println("Total images: " + images.size());
			// System.out.println("Number classified correctly: " + numCorrect);
			// System.out.println("Percentage correct: " + numCorrect
			// / (double) images.size());
			repPercentage[repetition] = numCorrect / (double) images.size() * 100.0;
		}
		System.out.println("Percentages: ");
		for (int i = 0; i < TIMES_TO_REPEAT; i++) {
			System.out.println(i + ": " + repPercentage[i] + "%");
		}
	}

	private void loadImages(File file) {
		images = new ArrayList<PerceptronImage>();
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNext()) {
				images.add(load(scan));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public PerceptronImage load(Scanner f) {
		boolean[][] newImage = null;
		boolean isX = false;
		java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");
		if (!f.next().equals("P1"))
			System.out.println("Not a P1 PBM file");
		String category = f.next().substring(1);
		if (!category.equals("other"))
			isX = true;
		int rows = f.nextInt();
		int cols = f.nextInt();

		newImage = new boolean[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				newImage[r][c] = (f.findWithinHorizon(bit, 0).equals("1"));
			}
		}

		return new PerceptronImage(newImage, isX);
	}

	private void createFeatures() {
		features = new Feature[NUM_FEATURES];

		// Create dummy feature
		features[0] = new Feature(width, height, true, 0);

		// Create the rest of the features
		for (int i = 1; i < NUM_FEATURES; i++) {
			features[i] = new Feature(width, height, false, i);
		}
	}

	// Main
	public static void main(String[] args) {
		new Perceptron(args);
	}

}
