package util;

import java.math.BigInteger;
import java.util.Random;

public class RandomGen {

	private int s ;
	private int x;
	
	public RandomGen(int seed) {
		this.s = this.getNextS(seed);
		for(int i = 0; i < 3; i += 1) {
			this.s = this.getNextS(this.s);
		}
	}
	
	public int randomint(int n){
		this.x = (this.s / 65536) % 16384;
		this.s = this.getNextS(this.s);
		return this.x % n;
	}
	
	private int getNextS(int s) {
		int next = ((s * 22695477) + 1);
		if (next < 0){
			//next-=Integer.MAX_VALUE;
			next = Math.abs(Integer.MIN_VALUE + next);
		}
		return next;
	}
}
