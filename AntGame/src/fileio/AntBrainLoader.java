/**
 * 
 */
package fileio;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import parsers.brain.BrainParser;
import parsers.brain.Lexer;
import parsers.brain.Token;
import ai.AntBrain;
import exceptions.InvalidInputException;

/**
 * AntBrainLoader.java
 * 
 * @date 29 Mar 2013
 * @version 1.0
 */
public class AntBrainLoader {

	/**
	 * Given the path to a brain file
	 * 
	 * @param filepath
	 * @return
	 * @throws InvalidInputException
	 */
	public static AntBrain load(String filepath) throws InvalidInputException {
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
				
				System.out.println("lexing");
				
				// lexing
				while (!lexer.finished()) {
					Token t = lexer.yylex();
					if (t != null) {
						tokens.add(t);
					}
				}
				
				// close the input stream				
				lexer.yyclose();
				
				System.out.println("parsing");
				
				// parsing
				BrainParser parser = new BrainParser();
				try {
					brain = parser.check(tokens);
					//System.out.println(brain);
				}
				catch (InvalidInputException e) {
					e.printStackTrace();
					throw new InvalidInputException("The input is not syntactically valid.");
				}

			}
			catch (InvalidInputException e) {
				// close stream here to avoid resource leak when exception is caught
				lexer.yyclose();
				
				// This happens when the input is not in the language
				throw new InvalidInputException("The input is not lexically valid.");
			}
			catch (IOException e) {
				// close stream here to avoid resource leak when exception is caught
				lexer.yyclose();
				
				// the file cannot be read
				throw new InvalidInputException("The file cannot be read.");
			}
		}
		catch (Exception e) {
			// this can happen if:
			//  - The file is not found
			//  - The character encoding is not supported
			//  - There is an IO error
			throw new InvalidInputException("The file cannot be read.");
		}
		
		// success. return the constructed brain
		return brain;
	}

}