package info.acidflow.tamadroid.menu.callback;

import info.acidflow.tamadroid.menu.MenuCallback;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;

public class DisplayMenuCallback implements MenuCallback {

	private Scene _mainScene;
	private MenuScene _menuScene;
	
	public DisplayMenuCallback(Scene s, MenuScene ms){
		_mainScene = s;
		_menuScene = ms;
	}
	
	@Override
	public void callback() {
		if(_mainScene.hasChildScene()) {
			/* Remove the menu and reset it. */
			_menuScene.back();
		} else {
			/* Attach the menu. */
			_mainScene.setChildScene(_menuScene, false, true, true);
		}
	}

}
