package info.acidflow.tamagochi.model;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class SimpleTamagochi extends Tamagochi {

	private static String LOG_TAG = SimpleTamagochi.class.getSimpleName();
	
	
	public SimpleTamagochi(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}

}
