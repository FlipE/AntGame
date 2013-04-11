package antgame;

import javax.swing.UIManager;

import screens.HelpScreen;
import screens.MainMenuScreen;
import screens.PlayScreen;
import screens.SplashScreen;
import screens.TestMapRenderScreen;
import screens.TwoPlayerMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class AntGame extends Game {

	Screen splashScreen;
	Screen mainMenuScreen;
	Screen tournamentMenuScreen;
	Screen twoPlayerMenuScreen;
	Screen creditsScreen;
	Screen helpScreen;
	Screen playScreen;
	Screen resultsScreen;
	Screen testRender;
	
	@Override
	public void create() {
		
		// set look and feel used by the Swing file chooser
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
			// don't care about this not working
		}
		/*
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}*/
		
		// load the assets
		Assets.load();
		
		// create all the screens
		this.splashScreen = new SplashScreen(this);
		this.mainMenuScreen = new MainMenuScreen(this);
		this.twoPlayerMenuScreen = new TwoPlayerMenuScreen(this);
		this.helpScreen = new HelpScreen(this);
		this.playScreen = new PlayScreen(this);
		this.testRender = new TestMapRenderScreen(this);
		
		// start off on the splash screen
		//this.setScreen(this.splashScreen);
		this.setScreen(this.mainMenuScreen);
		//this.setScreen(this.playScreen);
		//this.setScreen(this.testRender);
	}

	/**
	 * Set the current screen to the splash screen.
	 */
	public void setSplashScreen() {
		this.setScreen(this.splashScreen);
	}
	
	/**
	 * Set the current screen to the main menu screen.
	 */
	public void setMainMenuScreen() {
		this.setScreen(this.mainMenuScreen);
	}
	
	/**
	 * 
	 */
	public void setTwoPlayerMenuScreen() {
		this.setScreen(this.twoPlayerMenuScreen);
	}
	
	/**
	 * Set the current screen to the tournament menu screen.
	 */
	public void setTournamentMenuScreen() {
		this.setScreen(this.tournamentMenuScreen);
	}

	/**
	 * 
	 */
	public void setHelpScreen() {
		this.setScreen(this.helpScreen);
	}
	
	/**
	 * Set the current screen to the play screen.
	 */
	public void setPlayScreen() {
		this.setScreen(this.playScreen);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.splashScreen.dispose();
		this.mainMenuScreen.dispose();
		//this.tournamentMenuScreen.dispose();
		this.twoPlayerMenuScreen.dispose();
		//this.creditsScreen.dispose();
		this.helpScreen.dispose();
		this.playScreen.dispose();
		//this.resultsScreen.dispose();
		Assets.dispose();
		Gdx.app.exit();
		System.exit(0);
	}

}