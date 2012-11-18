package info.acidflow.tamadroid.model;

import info.acidflow.tamadroid.menu.MenuCallback;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Heating extends Sprite {

	private MenuCallback _callback;

	public Heating(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
	}
	
	public void setCallback(MenuCallback c){
		_callback = c;
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if(pSceneTouchEvent.isActionDown()){
			_callback.callback();
			return true;
		}
		return false;
	}

}
