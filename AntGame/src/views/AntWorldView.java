/**
 * 
 */
package views;

import models.Ant;
import antgame.Assets;
import antgame.Config;
import cells.BlackAntHill;
import cells.Cell;
import cells.ClearCell;
import cells.RedAntHill;
import cells.RockyCell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * 
 * AntWorldView.java
 *
 * @date	11 Apr 2013
 * @version	1.0
 */
public class AntWorldView implements View {

	// the world model
	private Cell[][] cells;
	
	// stage, batch and camera
	private Stage stage;
	private SpriteBatch batch;
	private OrthographicCamera cam;
	
	// cell graphics
	private AtlasRegion clearCell;
	private AtlasRegion rockyCell;
	private AtlasRegion foodCell;
	private AtlasRegion redAntHill;
	private AtlasRegion redAnt;
	private AtlasRegion blackAntHill;
	private AtlasRegion blackAnt;
	private AtlasRegion trailCell;
	
	// various world dimensions
	private float cellWidth;
	private float cellHeight;
	private float hudHeight;
	private float paddingLeft;
	private float paddingTop;
	private float worldWidth;
	private float worldHeight;
	private float xCoord;
	private float yCoord;
	
	
	/**
	 * @param world
	 */
	public AntWorldView(Cell[][] world, Stage stage) {
		super();
		this.cells = world;
		this.stage = stage;
		this.initialise();
	}

	private void initialise() {
		
		// creat a new cam and switch the coordinate system so that 0, 0 is top left
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// get the sprite batch
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		// hud stuff
//		Table hud = new Table();
//		hud.setFillParent(true);
//		hud.top().left();
//		redNameLbl = new Label("Black Team", Assets.skin);
//		hud.add(redNameLbl);
//		stage.addActor(hud);
		
		// cell graphics
		clearCell = Assets.textures.findRegion("clear-cell");
		rockyCell = Assets.textures.findRegion("rocky-cell");
		foodCell = Assets.textures.findRegion("food-cell");
		redAntHill = Assets.textures.findRegion("red-ant-hill");
		redAnt = Assets.textures.findRegion("red-ant");
		blackAntHill = Assets.textures.findRegion("black-ant-hill");
		blackAnt = Assets.textures.findRegion("black-ant");
		trailCell = Assets.textures.findRegion("trail-cell");
		
		// the various world dimensions
		this.hudHeight = 64;
		this.cellWidth = 6;
		this.cellHeight = 6;
		this.worldWidth = this.cells.length;
		this.worldHeight = this.cells[0].length;
		this.paddingLeft = (Gdx.graphics.getWidth() - ((this.worldWidth * this.cellWidth) + (this.cellWidth / 2))) / 2;
		this.paddingTop = ((Gdx.graphics.getHeight() + hudHeight) - ((this.worldHeight * ((this.cellHeight / 3) * 2)))) / 2;
	}
	
	/* (non-Javadoc)
	 * @see views.View#draw()
	 */
	@Override
	public synchronized void draw() {
		
		batch.begin();
		for(int y = 0; y < this.worldHeight; y += 1) {
			for(int x = 0; x < this.worldWidth; x += 1) {
			
				// calculate the screen coordinates of the cell
				xCoord = this.cellWidth * x + this.paddingLeft;
				yCoord = y * ((this.cellHeight / 3) * 2) + this.paddingTop;
				
				if(y % 2 == 0) {
					xCoord += (this.cellWidth / 2);
				}
				
				// get the cell from the model and render the correct type
				Cell c = this.cells[x][y];
				
				if(c instanceof RockyCell) {
					batch.draw(rockyCell, xCoord, yCoord);
				}
				else if(c instanceof ClearCell) {
					ClearCell clear = (ClearCell) c;
					if(clear.isOccupied()) {
						Ant a = clear.getAnt();
						if(a.getColor() == Config.RED_ANT) {
							batch.draw(redAnt, xCoord, yCoord);
						}
						else {
							batch.draw(blackAnt, xCoord, yCoord);
						}
					}
					else if(clear.numFood() > 0) {
						batch.draw(foodCell, xCoord, yCoord);
					}
					else if(c instanceof BlackAntHill) {
						batch.draw(blackAntHill, xCoord, yCoord);
					}
					else if(c instanceof RedAntHill) {
						batch.draw(redAntHill, xCoord, yCoord);
					}
					else if(clear.senseFoeMarker(Config.BLACK_ANT) || clear.senseFoeMarker(Config.RED_ANT)) {
						batch.draw(trailCell, xCoord, yCoord);
					}
					else {
						batch.draw(clearCell, xCoord, yCoord);
					}
				}
			}
		}
		batch.end();
		
		stage.draw();
	}
	
	/**
	 * 
	 * @param world
	 */
	public void setWorld(Cell[][] world) {
		this.cells = world;
	}

}