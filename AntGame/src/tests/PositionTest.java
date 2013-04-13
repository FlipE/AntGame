package tests;

import util.Position;


public class PositionTest {

	public static void main(String[] args) {
		PositionTest t = new PositionTest();
		t.run();
	}
	
	public PositionTest() {
		
	}
	
	public void run() {
		
		Position p = new Position(1, 1);
		Position dir0 = p.getPositionInDirection(0);
		Position dir1 = p.getPositionInDirection(1);
		Position dir2 = p.getPositionInDirection(2);
		Position dir3 = p.getPositionInDirection(3);
		Position dir4 = p.getPositionInDirection(4);
		Position dir5 = p.getPositionInDirection(5);
		
		System.out.println(dir0);
		System.out.println(dir1);
		System.out.println(dir2);
		System.out.println(dir3);
		System.out.println(dir4);
		System.out.println(dir5);
	}
	
}