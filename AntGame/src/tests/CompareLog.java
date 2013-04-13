package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CompareLog {

	public static void main(String[] args) {
		CompareLog c = new CompareLog("log.txt", "ourLog.txt");
		c.compare();
	}
	
	
	/**
	 * @param args
	 */
	private File log;
	private File ourLog;
	
	public CompareLog(String log, String ourLog){
		this.log = new File(log);
		this.ourLog = new File(ourLog);
	}
	
	public void compare(){
		try {
			BufferedReader logBuff = new BufferedReader(new FileReader(log));
			BufferedReader ourLogBuff = new BufferedReader(new FileReader(ourLog));
			
			String ours = "";
			String theirs = "";
			int lineNum = 0;
			
			while ((ours = ourLogBuff.readLine()) != null 
					&& (theirs = logBuff.readLine()) != null
					&& ours.equals(theirs)) {
				
				/*if(!ours.equals(theirs)) {
					System.out.println("Line Num: " + lineNum);
					System.out.println("Ours:     " + ours);
					System.out.println("Theirs:   " + theirs);
				}*/
				lineNum += 1;
			}
			System.out.println("Line Num: " + lineNum);
			System.out.println("Ours:     " + ours);
			System.out.println("Theirs:   " + theirs);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
