/**
 * 
 */
package info.acidflow.tamadroid.helper;

import java.io.IOException;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * @author acidflow
 *
 */
public final class SpriteCreator {
	
	private static final String LOG_TAG = SpriteCreator.class.getSimpleName();
	
	private static BitmapTextureAtlas _texture;
	private static TiledTextureRegion _tiledTexture;
	
	private SpriteCreator(){}
	
	public static BitmapTextureAtlas getTexture() {
		return _texture;
	}

	public static TiledTextureRegion getTiledTexture() {
		return _tiledTexture;
	}

	public static void createTiledTextureRegionForAnimatedSprite(String file, TextureManager tm, Context c, int col, int row){
		AssetManager am = c.getAssets();
		BitmapFactory.Options bOptions = new BitmapFactory.Options();
		bOptions.inJustDecodeBounds = true;
		try {
			BitmapFactory.decodeStream(am.open(file), null, bOptions);
			int pow2width = getSuperiorPowerOfTwo(bOptions.outWidth);
			int pow2height = getSuperiorPowerOfTwo(bOptions.outHeight);
			_texture = new BitmapTextureAtlas(tm ,pow2width, pow2height);
			_tiledTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(_texture, c, file, 0 , 0,col,row);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int getSuperiorPowerOfTwo(int dimension){
		int w = 1;
		while(w < dimension){
			w = w << 1;
		}
		return w;
	}
}
