package tests;

import ai.AntBrain;
import exceptions.SyntacticallyInvalidInputException;
import fileio.AntBrainLoader;


public class BrainDebugTest {

	public static void main(String[] args) {
		BrainDebugTest t = new BrainDebugTest();
		t.run();
	}
	
	public BrainDebugTest() {
		
	}
	
	public void run() {
		
		try {
			AntBrain testBrain = AntBrainLoader.load("SimpleBrain.brain");
		} catch (SyntacticallyInvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}