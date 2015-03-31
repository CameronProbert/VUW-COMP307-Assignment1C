package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Perceptron {

	// Constants
	private static final int NUM_FEATURES = 50;
	private static final int TIMES_TO_REPEAT = 100;
	private static final double THRESHOLD = 0.75;
	private static final double INITIAL_LEARNING_RATE = 1;
	private static final double LEARNING_RATE_MODIFIER = 0.95;
	private static final double LEARNING_RATE_MINIMUM = 0.01;
	private static final int DEFAULT_WIDTH = 10;
	private static final int DEFAULT_HEIGHT = 10;

	private Feature[] features;
	private List<PerceptronImage> images;

	private int width;
	private int height;
	private double learningRate;

	/**
	 * Creates the Perceptron and trains it on the given set
	 * 
	 * @param filename
	 */
	public Perceptron(String filename) {
		width = DEFAULT_WIDTH;
		height = DEFAULT_HEIGHT;
		learningRate = INITIAL_LEARNING_RATE;
		File file = new File(filename);
		loadImages(file);
		createFeatures();
		trainPerceptron();
	}

	/**
	 * Iterates through the list of images to train the perceptron
	 */
	private void trainPerceptron() {
		// Creates a log of that contains the percentage each epoch got correct
		int numWrong = images.size();
		int numReps = 0;

		// Repeat the training process TIMES_TO_REPEAT number of times or until
		// there are none wrong
		for (int repetition = 0; repetition < TIMES_TO_REPEAT && numWrong != 0; repetition++) {
			int numCorrect = 0;

			// Iterate through each image in the set of image
			for (PerceptronImage image : images) {
				double netInput = 0;

				// Work out the net input by evaluating each feature on the
				// image
				for (Feature feature : features) {
					netInput += feature.evaluate(image) * feature.getWeight();
				}

				// If the image is evaluated to be an X
				if (netInput <= 0) {
					// If it actually is an X then record it
					if (image.isX()) {
						numCorrect++;
					} else {
						// If it is not then re-weight features
						for (Feature feature : features) {
							double newWeight = feature.getWeight()
									+ feature.evaluate(image) * learningRate;
							feature.setWeight(newWeight);
						}
					}
				} else {
					// If the image is evaluated to be not an X
					// If it actually not an X then record it
					if (!image.isX()) {
						numCorrect++;
					} else {
						// If it is not then re-weight features
						for (Feature feature : features) {
							double newWeight = feature.getWeight()
									- feature.evaluate(image) * learningRate;
							feature.setWeight(newWeight);
						}
					}
				}
			}

			// Update the number of images wrong for this epoch
			numWrong = images.size() - numCorrect;

			// Modify the learning rate
			if (learningRate > LEARNING_RATE_MINIMUM) {
				learningRate *= LEARNING_RATE_MODIFIER;
			}

			numReps++;
		}

		try {
			PrintWriter writer = new PrintWriter("output.txt");
			
			// Print out the number of repetitions if the perceptron converged,
			// otherwise print out the number of images that are still classified
			// incorrectly
			if (numWrong == 0) {
				writer.println("Perceptron converged");
				writer.println("Number of repetitions: " + numReps);
				System.out.println("Perceptron converged");
				System.out.println("Number of repetitions: " + numReps);
			} else {
				writer.println("Perceptron did not converge");
				writer.println("Number of images still incorrect: " + numWrong);
				System.out.println("Perceptron did not converge");
				System.out.println("Number of images still incorrect: " + numWrong);
			}

			writer.println("\nEnding learning Rate: " + learningRate + "\n");
			System.out.println("\nEnding learning Rate: " + learningRate + "\n");
			
			for (Feature feature : features){
				System.out.println(feature.toString());
				writer.println(feature.toString());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialises and populates the images array
	 * 
	 * @param file
	 */
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

	/**
	 * Loads a PerceptronImage from a file. Adapted from sample code.
	 * 
	 * @param f
	 * @return
	 */
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

	/**
	 * Initialises and populates the features array
	 */
	private void createFeatures() {
		features = new Feature[NUM_FEATURES];

		// Create dummy feature
		features[0] = new DummyFeature(width, height, 0, 0 - THRESHOLD);

		// Create the rest of the features
		for (int i = 1; i < NUM_FEATURES; i++) {
			features[i] = new Feature(width, height, i);
		}
	}

	// Main
	public static void main(String[] args) {
		new Perceptron(args[0]);
	}

}
