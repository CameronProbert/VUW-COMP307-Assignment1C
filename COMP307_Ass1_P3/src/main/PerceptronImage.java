package main;

import java.util.Scanner;

public class PerceptronImage {

	private boolean[][] values;
	private boolean isX;

	/**
	 * Creates a perceptron image from a 2D boolean array and a boolean that
	 * states whether or not the image is an X
	 * 
	 * @param values
	 * @param isX
	 */
	public PerceptronImage(boolean[][] values, boolean isX) {
		this.values = values;
		this.isX = isX;
	}

	/**
	 * Alternative constructor for the PerceptronImage DOES NOT WORK
	 * 
	 * @param scan
	 */
	public PerceptronImage(Scanner scan) {
		if (!scan.hasNext("#Yes") && !scan.hasNext("#other")) {
			try {
				throw new Exception("Image should state 'X' or 'O'");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String imageType = scan.nextLine();
		if (imageType.equals("#Yes")) {
			isX = true;
		} else {
			isX = false;
		}
		int width = 10;
		scan.nextLine();
		if (scan.hasNextInt()) {
			width = scan.nextInt();
		} else {
			try {
				throw new Exception("int width expected");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (scan.hasNextInt()) {
			scan.nextInt();
		} else {
			try {
				throw new Exception("int height expected");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		int row = 0;
		int col = 0;
		while (scan.hasNext() && !scan.hasNext("P1")) {
			String line = scan.nextLine();
			Scanner lineScan = new Scanner(line);
			lineScan.useDelimiter("");
			while (lineScan.hasNext()) {
				String token = lineScan.next();
				if (token.equals("0")) {
					values[row][col] = true;
				} else {
					values[row][col] = false;
				}
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
			lineScan.close();
		}
		System.err.println("Is X: " + isX);
		System.err.println(values.toString());
	}

	/**
	 * Gets the value of a particular value inside the values array. Not very
	 * robust as it does not check if the values are inside the bounds of the
	 * array.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean getValue(int x, int y) {
		return values[y][x];
	}

	/**
	 * Returns whether or not the image is of an X
	 * 
	 * @return
	 */
	public boolean isX() {
		return isX;
	}

	/**
	 * Returns a string that represents the PerceptronImage
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("PerceptronImage:");
		sb.append("\nIs a picture of x: " + isX);
		for (boolean[] row : values) {
			sb.append("\n");
			for (boolean item : row) {
				sb.append(item + "\t");
			}
		}
		sb.append("\n");
		return sb.toString();
	}
}
