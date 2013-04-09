/**
 * 
 */
package controllers;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.PlayerInfo;
import antgame.Config;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * MainMenuController.java
 *
 * @author 	Chris B
 * @date	26 Mar 2013
 * @version	1.0
 */
public class PlayerInfoController extends InputListener {

	private PlayerInfo playerInfo;
	
	/**
	 * @param game
	 */
	public PlayerInfoController(PlayerInfo playerInfo) {
		super();
		this.playerInfo = playerInfo;
	}

	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {		
		return true;
	}

	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		
		File file = openBrainDialogue();
		if(file != null) {
			playerInfo.loadBrain(file.getPath());
		}
	}
	
	private File openBrainDialogue() {
		File file = null;
		
		JFileChooser chooser = new JFileChooser(Config.currentBrainPath);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(Config.BRAIN_FILE_DESCRIPTION, Config.BRAIN_FILE_EXTENSION);
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	file = chooser.getSelectedFile();
	    }
		Config.currentBrainPath = chooser.getCurrentDirectory().getPath();
		return file;
	}
}