/**
 * 
 */
package controllers;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.WorldInfo;
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
public class WorldInfoController extends InputListener {

	private WorldInfo worldInfo;
	
	/**
	 * @param game
	 */
	public WorldInfoController(WorldInfo worldInfo) {
		super();
		this.worldInfo = worldInfo;
	}

	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {		
		return true;
	}

	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		
		File file = openWorldDialogue();
		if(file != null) {
			worldInfo.loadWorld(file.getPath());
		}
	}
	
	private File openWorldDialogue() {
		File file = null;
		
		JFileChooser chooser = new JFileChooser(Config.currentWorldPath);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(Config.WORLD_FILE_DESCRIPTION, Config.WORLD_FILE_EXTENSION);
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	file = chooser.getSelectedFile();
	    }
		Config.currentWorldPath = chooser.getCurrentDirectory().getPath();
		return file;
	}
}