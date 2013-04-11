/**
 * 
 */
package screens;

import antgame.AntGame;
import antgame.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * PlayScreen.java
 * 
 * @author Chris B
 * @date 25 Mar 2013
 * @version 1.0
 */
public class TestMapRenderScreen extends AbstractScreen {
	
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private AtlasRegion cell;
	private float cellWidth;
	private float cellHeight;
	private float hudHeight;
	private float paddingLeft;
	private float paddingTop;
	private float worldWidth;
	private float worldHeight;
	private float xCoord;
	private float yCoord;
	private Label redNameLbl;
	
	/**
	 * @param game
	 */
	public TestMapRenderScreen(AntGame game) {
		super(game);
		this.initialise();
	}

	private void initialise() {
		
		// creat a new cam and switch the coordinate system so that 0, 0 is top left
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// get the sprite batch
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		// cell graphics
		cell = Assets.textures.findRegion("clear-cell");
		
		// hud stuff
		Table hud = new Table();
		hud.setFillParent(true);
		hud.top().left();
		redNameLbl = new Label("Black Team", Assets.skin);
		hud.add(redNameLbl);
		stage.addActor(hud);
		
		// the various world dimensions
		this.hudHeight = 64;
		this.cellWidth = 6;
		this.cellHeight = 6;
		this.worldWidth = 150;
		this.worldHeight = 150;
		this.paddingLeft = (Gdx.graphics.getWidth() - ((this.worldWidth * this.cellWidth) + (this.cellWidth / 2))) / 2;
		this.paddingTop = ((Gdx.graphics.getHeight() + hudHeight) - ((this.worldHeight * ((this.cellHeight / 3) * 2)))) / 2;
	}

	@Override
	public void show() {
		// set the stage as the input processor
		Gdx.input.setInputProcessor(stage);		
	}

	@Override
	public void render(float delta) {
		// update the actors
		redNameLbl.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
		stage.act(delta);
		
		// clear the screen with the given RGB color (black)
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		
		for(int y = 0 ; y < this.worldHeight ; y += 1) {
			for(int x = 0 ; x < this.worldWidth ; x += 1) {
				xCoord = this.cellWidth * x + this.paddingLeft;
				yCoord = y * ((this.cellHeight / 3) * 2) + this.paddingTop;
				
				if(y % 2 == 0) {
					xCoord += (this.cellWidth / 2);
				}
				
				batch.draw(cell, xCoord, yCoord);
			}
		}
		batch.end();
		//batch.setProjectionMatrix(stage.getCamera().combined);
		// draw the actors
		stage.draw();
		
		// debug draw table
		//Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
	}

}