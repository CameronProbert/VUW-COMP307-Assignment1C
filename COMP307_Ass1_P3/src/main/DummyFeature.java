package main;

public class DummyFeature extends Feature {

	public DummyFeature(int width, int height, int seedModifier) {
		super(width, height, seedModifier);
		// TODO Auto-generated constructor stub
	}
	

	public boolean evaluate(PerceptronImage image) {
		return true;
	}

}
