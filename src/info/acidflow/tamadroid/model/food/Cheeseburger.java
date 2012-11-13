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
public final class Cheeseburger extends Food {

	private static double INCREASE_WEIGHT_FACTOR = 0.3;
	private static double REDUCE_HUNGER_FACTOR = 0.3;
	
	/**
	 * @param pX
	 * @param pY
	 * @param pTiledTextureRegion
	 * @param pTiledSpriteVertexBufferObject
	 */
	public Cheeseburger(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
		super(pX, pY, pTiledTextureRegion, pTiledSpriteVertexBufferObject);
		_increaseWeightFactor = INCREASE_WEIGHT_FACTOR;
		_reduceHungerFactor = REDUCE_HUNGER_FACTOR;
	}

}
