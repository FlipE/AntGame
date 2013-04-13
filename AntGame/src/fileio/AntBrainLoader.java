/**
 * 
 */
package fileio;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import listeners.AntBrainLoaderListener;
import parsers.brain.BrainParser;
import parsers.brain.Lexer;
import parsers.brain.Token;
import ai.AntBrain;
import exceptions.LexicallyInvalidInputException;
import exceptions.SyntacticallyInvalidInputException;

/**
 * AntBrainLoader.java
 * 
 * @date 29 Mar 2013
 * @version 1.0
 */
public class AntBrainLoader implements Runnable {
	
	private List<AntBrainLoaderListener> listeners;
	private String filepath;
	
	public AntBrainLoader(String filepath) {
		this.filepath = filepath;
		this.listeners = new ArrayList<AntBrainLoaderListener>();
	}
	
	/**
	 * Given the path to a brain file
	 * 
	 * @param filepath
	 * @return
	 * @throws SyntacticallyInvalidInputException
	 */
	public static AntBrain load(String filepath) throws SyntacticallyInvalidInputException {
		List<Token> tokens = new LinkedList<Token>();
		AntBrain brain = null;
		
		try {

			//InputStream inputStream = Gdx.files.internal(filepath).reader();
			//InputStream inputStream = Gdx.files.external("C:/University/Dropbox/Git2/AntGame/AntGame-android/assets/brains/sampleant.brain").read();
			//Reader reader = Gdx.files.internal(filepath).reader();

			InputStream inputStream = new FileInputStream(filepath);
			Reader reader = new InputStreamReader(inputStream, "Cp1252");
			Lexer lexer = new Lexer(reader);

			try {
								
				// lexing
				while (!lexer.finished()) {
					Token t = lexer.yylex();
					if (t != null) {
						tokens.add(t);
					}
				}
				
				// close the input stream				
				lexer.yyclose();
								
				// parsing
				BrainParser parser = new BrainParser();
				try {
					brain = parser.check(tokens);
					//System.out.println(brain);
				}
				catch (SyntacticallyInvalidInputException e) {
					throw new SyntacticallyInvalidInputException("The input is not syntactically valid; " + e.getMessage());
				}
			}
			catch (LexicallyInvalidInputException e) {
				// close stream here to avoid resource leak when exception is caught
				lexer.yyclose();
				
				// This happens when the input is not in the language
				throw new SyntacticallyInvalidInputException("The input is not lexically valid; " + e.getMessage());
			}
			catch (IOException e) {
				// close stream here to avoid resource leak when exception is caught
				lexer.yyclose();
				
				// the file cannot be read
				throw new SyntacticallyInvalidInputException("The file cannot be read.");
			}
		}
		catch (IOException e) {
			// this can happen if:
			//  - The file is not found
			//  - The character encoding is not supported
			//  - There is an IO error
			throw new SyntacticallyInvalidInputException("The file cannot be read.");
		}
		
		// success. return the constructed brain
		return brain;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		try {
			AntBrain brain = AntBrainLoader.load(this.filepath);
			this.notifyBrainLoadComplete(brain);
		}
		catch(SyntacticallyInvalidInputException e) {
			this.notifyBrainLoadFailed(e.getMessage());
		}		
	}
	
	private void notifyBrainLoadFailed(String message) {
		for(AntBrainLoaderListener l : this.listeners) {
			l.brainLoadFailed(message);
		}
	}

	private void notifyBrainLoadComplete(AntBrain brain) {
		for(AntBrainLoaderListener l : this.listeners) {
			l.brainLoadComplete(brain);
		}
	}
	
	public void addListener(AntBrainLoaderListener l) {
		this.listeners.add(l);
	}
	
	public void removeListener(AntBrainLoaderListener l) {
		this.listeners.remove(l);
	}

}