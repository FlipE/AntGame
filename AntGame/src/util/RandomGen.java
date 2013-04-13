package util;

/**
 * RandomGen.java
 * 
 * Given a seed value RandomGen will generate a series of pseudo random integers
 * in a deterministic manor based on the seed value. This means that two RandomGen
 * instances with the same seed will produce the same series of numbers.
 *
 * @date	13 Apr 2013
 * @version	1.1
 */
public class RandomGen {

	/** holds the current seed, this is updated every time a new random number is generated */
	private int s ;
	
	/**
	 * Given a seed value RandomGen will generate a series of pseudo random integers
	 * in a deterministic manor based on the seed value.
	 * 
	 * @param seed 	the seed value for this random generator. Random generators with 
	 * 				the same seed will produce the same series of numbers
	 */
	public RandomGen(int seed) {
		// as per the spec the seed should be iterated 5 times.
		this.s = this.getNextS(seed);
		for(int i = 0; i < 3; i += 1) {
			this.s = this.getNextS(this.s);
		}
	}
	
	/**
	 * Returns the next integer in the series in the range 0 - n-1.
	 * 
	 * @param n the range cardinality. The number returned will be in the range 0 - n-1.
	 * @return the next integer in the series in the range 0 - n-1.
	 */
	public int randomint(int n){
		int x = (this.s / 65536) % 16384;
		this.s = this.getNextS(this.s);
		return x % n;
	}
	
	/**
	 * S is the seed for the next random number. The series s is deterministic.
	 * When an integer overflow occurs and the seed becomes negative (two complement integers)
	 * the seed value becomes the overflow amount. Thus the seed is always kept in the bounds
	 * of the positive integer range.
	 * 
	 * @param s a seed value
	 * @return the value for the next seed
	 */
	private int getNextS(int s) {
		int next = ((s * 22695477) + 1);
		if (next < 0){
			next = Math.abs(Integer.MIN_VALUE + next);
		}
		return next;
	}
}
