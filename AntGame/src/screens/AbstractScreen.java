package screens;

import antgame.AntGame;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class AbstractScreen implements Screen  {

	protected Stage stage;
	protected AntGame game;

	public AbstractScreen (AntGame game) {
		this.game = game;
		this.stage = new Stage();
	}
	
	@Override
	public abstract void render (float delta);

	@Override
	public abstract void resize (int width, int height);

	@Override
	public void dispose () {
		stage.dispose();
	}
	
	@Override
	public void show () {
		
	}
	
	@Override
	public void pause () {
		// method used for android support
	}
	
	@Override
	public void resume () {
		// method used for android support
	}
	
	@Override
	public void hide () {
		// method used for android support
	}
	
}