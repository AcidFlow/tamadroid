/**
 * 
 */
package info.acidflow.tamadroid.model.food;

import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

/**
 * @author acidflow
 *
 */
public final class Fries extends Food {

	
	private static double INCREASE_WEIGHT_FACTOR = 0.15;
	private static double REDUCE_HUNGER_FACTOR = 0.1;
	
	/**
	 * @param pX
	 * @param pY
	 * @param pTiledTextureRegion
	 * @param pTiledSpriteVertexBufferObject
	 */
	public Fries(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
		super(pX, pY, pTiledTextureRegion, pTiledSpriteVertexBufferObject);
		_increaseWeightFactor = INCREASE_WEIGHT_FACTOR;
		_reduceHungerFactor = REDUCE_HUNGER_FACTOR;
	}

}
