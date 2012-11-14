/**
 * 
 */
package info.acidflow.tamadroid.helper;

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
	
	private static BitmapTextureAtlas _texture;
	private static TiledTextureRegion _tiledTexture;
	
	private SpriteCreator(){}
	
	public static BitmapTextureAtlas getTexture() {
		return _texture;
	}

	public static TiledTextureRegion getTiledTexture() {
		return _tiledTexture;
	}

	public static void createTiledTextureRegionForAnimatedSprite(String file, TextureManager tm, int pow2width, int pow2height, Context c, int pX, int pY, int col, int row){
		_texture = new BitmapTextureAtlas(tm ,pow2width, pow2height);
		_tiledTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(_texture, c, file, pX,pY,col,row);
	}
}
