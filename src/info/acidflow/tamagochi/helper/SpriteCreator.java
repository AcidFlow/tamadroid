/**
 * 
 */
package info.acidflow.tamagochi.helper;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;

/**
 * @author acidflow
 *
 */
public final class SpriteCreator {
	
	private BitmapTextureAtlas _texture;
	private TiledTextureRegion _tiledTexture;
	
	public BitmapTextureAtlas getTexture() {
		return _texture;
	}

	public TiledTextureRegion getTiledTexture() {
		return _tiledTexture;
	}

	public SpriteCreator(){
		
	}
	
	public void createTiledTextureRegionForAnimatedSprite(String file, TextureManager tm, int pow2width, int pow2height, Context c, int pX, int pY, int col, int row){
		_texture = new BitmapTextureAtlas(tm ,pow2width, pow2height);
		_tiledTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(_texture, c, file, pX,pY,col,row);
	}
}
