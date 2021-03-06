/**
 * 
 */
package views;

import models.AntWorld;
import antgame.Assets;
import antgame.Match;
import cells.BlackAntHill;
import cells.Cell;
import cells.ClearCell;
import cells.RedAntHill;
import cells.RockyCell;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * AntWorldView.java
 *
 * @author 	Chris B
 * @date	23 Mar 2013
 * @version	1.0
 */
public class AntWorldTextView implements View {

	private Cell[][] cells;
	private Stage stage;
	private Label text;
	
	/**
	 * @param world
	 */
	public AntWorldTextView(Cell[][] world, Stage stage) {
		super();
		this.cells = world;
		this.stage = stage;
		this.initialise();
	}

	private void initialise() {
		// create a table layout to add ui items to the stage
		Skin skin = Assets.skin;
		Table root = new Table(skin);
		root.setFillParent(true);
		
		text = new Label("", skin);
		
		root.add(text);
		stage.addActor(root);
	}
	
	/* (non-Javadoc)
	 * @see views.View#draw()
	 */
	@Override
	public synchronized void draw() {

		StringBuffer s = new StringBuffer();
		
		for(int x = 0; x < this.cells.length; x += 1) {
			if(x % 2 == 0) {
				s.append(" ");
			}
			for(int y = 0; y < this.cells[0].length; y += 1) {
				Cell c = this.cells[x][y];
				
				if(c instanceof BlackAntHill) {
					s.append((c.isOccupied()) ? "A" : "-");
				}
				else if(c instanceof RedAntHill) {
					s.append((c.isOccupied()) ? "A" : "+");
				}
				else if(c instanceof RockyCell) {
					s.append("#");
				}
				else if(c instanceof ClearCell) {
					ClearCell clear = (ClearCell) c;
					if(clear.isOccupied()) {
						s.append("A");
					}
					else if(clear.numFood() > 0) {
						s.append(clear.numFood());
					}
					else {
						s.append(" . ");
					}
				}
			}
			s.append("\n");
		}
		text.setText(s.toString());
		
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