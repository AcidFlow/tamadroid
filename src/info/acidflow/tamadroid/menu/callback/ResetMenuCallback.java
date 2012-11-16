package info.acidflow.tamadroid.menu.callback;

import info.acidflow.tamadroid.menu.MenuCallback;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;

public class ResetMenuCallback implements MenuCallback {

	private Scene _mainScene;
	private MenuScene _menuScene;
	
	public ResetMenuCallback(Scene mainScene, MenuScene menuScene){
		_mainScene = mainScene;
		_menuScene = menuScene;
	}
	
	@Override
	public void callback() {
		_mainScene.reset();
		_mainScene.clearChildScene();
		_menuScene.reset();
	}

}
