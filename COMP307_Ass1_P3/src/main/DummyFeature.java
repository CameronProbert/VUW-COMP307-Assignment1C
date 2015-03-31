package main;

/**
 * The DummyFeature always evaluates as one and has a weight of -T (T is the
 * threshold)
 * 
 * @author Cameron Probert
 *
 */
public class DummyFeature extends Feature {

	/**
	 * Explicitly given a weight, this should be equal to the negative threshold
	 * value
	 * 
	 * @param width
	 * @param height
	 * @param seedModifier
	 * @param weight
	 */
	public DummyFeature(int width, int height, int seedModifier, double weight) {
		super(width, height, seedModifier);
		this.weight = weight;
	}

	/**
	 * DummyFeature always returns 1 when evaluated
	 */
	public double evaluate(PerceptronImage image) {
		return 1;
	}

	/**
	 * Can't change the weight of the dummy feature therefore empty method
	 */
	@Override
	public void setWeight(double newWeight) {

	}

}
