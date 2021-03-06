/**
 * 
 */
package antgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Assets.java
 *
 * @author 	Chris B
 * @date	25 Mar 2013
 * @version	1.0
 */
public class Assets {

	public static TextureAtlas textures;
	public static Skin skin;
	
	public static void load() {
		
		// load textures
		textures = new TextureAtlas(Gdx.files.internal("data/antgame.pack"));
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		// load sounds
		
	}

	public static void dispose() {
		textures.dispose();
		skin.dispose();
	}
	
}