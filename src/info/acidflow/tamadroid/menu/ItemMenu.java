package info.acidflow.tamadroid.menu;

import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.res.AssetManager;

public class ItemMenu {
	

	private ITextureRegion _itemTexture;
	private SpriteMenuItem _spriteMenuItem;
	private int _menuElement;
	private MenuCallback _callback;
	
	public ItemMenu(int menuElement, MenuCallback callback, BitmapTextureAtlas menuTexture, AssetManager am, String file, int pX, int pY, VertexBufferObjectManager v){
		_menuElement = menuElement;
		_itemTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTexture, am, file, pX, pY);
		_spriteMenuItem = new SpriteMenuItem(_menuElement, _itemTexture, v);
		_callback = callback;
	}

	public int getMenuElement() {
		return _menuElement;
	}

	
	public ITextureRegion getItemTexture() {
		return _itemTexture;
	}

	public SpriteMenuItem getSpriteMenuItem() {
		return _spriteMenuItem;
	}
	
	public void setMenuCallback(MenuCallback c){
		_callback = c;
	}
	
	public void callback(){
		_callback.callback();
	}
	
}
