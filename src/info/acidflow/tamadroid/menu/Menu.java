package info.acidflow.tamadroid.menu;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.res.AssetManager;

public class Menu implements IOnMenuItemClickListener{

	private static final String LOG_TAG = Menu.class.getSimpleName();

	private BitmapTextureAtlas _menuTexture;
	private HashMap<Integer, ItemMenu> _menuItems;
	private VertexBufferObjectManager _vertexBufferObjectManager;
	private AssetManager _assetManager; 

	public Menu(BitmapTextureAtlas b, VertexBufferObjectManager v, AssetManager am){
		_menuItems = new HashMap<Integer, ItemMenu>();
		_menuTexture = b;
		_vertexBufferObjectManager = v;
		_assetManager = am;
	}

	public void addItem(int menuElement, MenuCallback callback, String file, int pX, int pY){
		_menuItems.put(menuElement, new ItemMenu(menuElement, callback, _menuTexture, _assetManager, file, pX, pY, _vertexBufferObjectManager));
	}

	public void addItem(int menuElement, String file, int pX, int pY){
		_menuItems.put(menuElement, new ItemMenu(menuElement, null, _menuTexture, _assetManager, file, pX, pY, _vertexBufferObjectManager));
	}



	public void load(){
		_menuTexture.load();
	}

	public MenuScene createMenuScene(Camera c){
		MenuScene scene = new MenuScene(c);
		for(ItemMenu i : _menuItems.values()){
			scene.addMenuItem(i.getSpriteMenuItem());
		}
		scene.buildAnimations();
		scene.setBackgroundEnabled(false);
		scene.setOnMenuItemClickListener(this);
		return scene;
	}
	
	public ItemMenu getMenuItem(int code){
		return _menuItems.get(code);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		if(_menuItems.containsKey(pMenuItem.getID())){
			_menuItems.get(pMenuItem.getID()).callback();
			return true;
		}
		return false;
	}

}
