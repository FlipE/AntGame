/**
 * 
 */
package screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import antgame.AntGame;
import antgame.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

/**
 * PlayScreen.java
 * 
 * @author Chris B
 * @date 25 Mar 2013
 * @version 1.0
 * @param <AntGame>
 */
public class SplashScreen extends AbstractScreen {

	private Image splashImage;

	/**
	 * @param game
	 */
	public SplashScreen(AntGame game) {
		super(game);
		this.initialise();
	}

	private void initialise() {
		// start playing the menu music
		//game.getMusicManager().play(TyrianMusic.MENU);

		// retrieve the splash image's region from the atlas
		AtlasRegion splashRegion = Assets.textures.findRegion("splash");
		Drawable splashDrawable = new TextureRegionDrawable(splashRegion);

		// here we create the splash image actor; its size is set when the
		// resize() method gets called
		splashImage = new Image(splashDrawable, Scaling.stretch);
		splashImage.setFillParent(true);

		// this is needed for the fade-in effect to work correctly; we're just 
		// making the image completely transparent
		splashImage.getColor().a = 0f;

		// and finally we add the actor to the stage
		stage.addActor(splashImage);
	}
	
	@Override
	public void show() {

		// configure the fade-in/out effect on the splash image
		splashImage.addAction(sequence(fadeIn(1.75f), delay(2f), fadeOut(1.75f), new Action() {
			@Override
			public boolean act(float delta) {
				// the last action will move to the next screen
				game.setMainMenuScreen();
				return true;
			}
		}));

	}

	@Override
	public void render(float delta) {

		// update the actors
		stage.act(delta);

		// clear the screen with the given RGB color (black)
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// draw the actors
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

}