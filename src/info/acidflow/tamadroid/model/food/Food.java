/**
 * 
 */
package info.acidflow.tamadroid.model.food;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

/**
 * @author acidflow
 *
 */
public abstract class Food extends AnimatedSprite{

	protected double _reduceHungerFactor;
	protected double _increaseWeightFactor;
	
	protected Food(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			ITiledSpriteVertexBufferObject pTiledSpriteVertexBufferObject) {
		super(pX, pY, pTiledTextureRegion, pTiledSpriteVertexBufferObject);
	}

	public double getReduceHungerFactor() {
		return _reduceHungerFactor;
	}

	public double getIncreaseWeightFactor() {
		return _increaseWeightFactor;
	}

}
