package main;

import java.util.Scanner;

public class PerceptronImage {

	private boolean[][] values;
	private boolean isX;

	public PerceptronImage(boolean[][] values, boolean isX) {
		this.values = values;
		this.isX = isX;
	}

	public PerceptronImage(Scanner scan) {
		if (!scan.hasNext("#Yes") && !scan.hasNext("#other")) {
			try {
				throw new Exception("Image should state 'X' or 'O'");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String imageType = scan.nextLine();
		System.err.println(imageType);
		if (imageType.equals("#Yes")) {
			isX = true;
		} else {
			isX = false;
		}
		int width = 10;
		int height = 10;
		String wid = scan.next();
		String hi = scan.next();
		System.err.println(wid);
		System.err.println(hi);
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
			height = scan.nextInt();
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
				if (col == width){
					col = 0;
					row++;
				}
			}
			lineScan.close();
		}
		System.err.println("Is X: " + isX);
		System.err.println(values.toString());
	}
	
	public boolean getValue(int x, int y){
		return values[y][x];
	}
	
	public boolean isX(){
		return isX;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder("PerceptronImage:");
		sb.append("\nIs a picture of x: " + isX);
		for (boolean[] row : values){
			sb.append("\n");
			for (boolean item : row){
				sb.append(item + "\t");
			}
		}
		sb.append("\n");
		return sb.toString();
	}
}
