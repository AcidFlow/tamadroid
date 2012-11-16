package info.acidflow.tamadroid;

import info.acidflow.tamadroid.helper.SpriteCreator;
import info.acidflow.tamadroid.menu.Menu;
import info.acidflow.tamadroid.menu.MenuElement;
import info.acidflow.tamadroid.menu.callback.QuitMenuCallback;
import info.acidflow.tamadroid.menu.callback.ResetMenuCallback;
import info.acidflow.tamadroid.model.SimpleTamagochi;
import info.acidflow.tamadroid.model.Tamagochi;
import info.acidflow.tamadroid.tamadroidService.ActivityIncomingMessageHandler;
import info.acidflow.tamadroid.tamadroidService.TamadroidService;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.KeyEvent;

public class PlayActivity extends SimpleBaseGameActivity{

	private static final String LOG_TAG = PlayActivity.class.getSimpleName();

	private static int CAMERA_WIDTH = 960;
	private static int CAMERA_HEIGHT = 1600;
	private static boolean FULLSCREEN = true;
	private static ScreenOrientation ORIENTATION = ScreenOrientation.PORTRAIT_FIXED;

	/**
	 * 
	 */
	private Camera _camera;
	private Scene _mainScene;
	private MenuScene _menuScene;
	private Menu _menu;
	private TiledTextureRegion _tamagochiTiledTextureRegion;

	/**
	 * Service connection part
	 */
	private boolean _isServiceBound = false;
	private Messenger _serviceMessenger;
	private final Messenger _activityMessenger = new Messenger(new ActivityIncomingMessageHandler());


	private ServiceConnection _serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			_serviceMessenger = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// Save the service messenger 
			_serviceMessenger = new Messenger(service);
			try {
				// Send a message for registration
				Message msg = Message.obtain(null, TamadroidService.MSG_REGISTER_CLIENT);
				msg.replyTo = _activityMessenger;
				_serviceMessenger.send(msg);
			} catch (RemoteException e) {}
		}
	};

	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
		startService(new Intent(this, TamadroidService.class));
		doBindService();
	}

	@Override
	protected void onPause() {
		doUnbindService();
		super.onPause();
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		_camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(FULLSCREEN, ORIENTATION, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), _camera);
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlas menuTexture = new BitmapTextureAtlas(getTextureManager(), 256, 128);
		_menu = new Menu(menuTexture, getVertexBufferObjectManager(), getAssets());
		_menu.addItem(MenuElement.RESET, "gfx/menu_reset.png", 0, 0);
		_menu.addItem(MenuElement.QUIT, "gfx/menu_quit.png", 0, 50);
		_menu.load();
		SpriteCreator.createTiledTextureRegionForAnimatedSprite("gfx/sprite_egg.png", getTextureManager(), this, 2, 2);
		SpriteCreator.getTexture().load();
		_tamagochiTiledTextureRegion = SpriteCreator.getTiledTexture();
	}

	@Override
	protected Scene onCreateScene() {
		_menuScene = _menu.createMenuScene(_camera);
		_mainScene = new Scene();
		_menu.getMenuItem(MenuElement.RESET).setMenuCallback(new ResetMenuCallback(_mainScene, _menuScene));
		_menu.getMenuItem(MenuElement.QUIT).setMenuCallback(new QuitMenuCallback(this));
		_mainScene.setBackground(new Background(Color.GREEN));
		Tamagochi tamagochi = new SimpleTamagochi((CAMERA_WIDTH - 128)/2, (CAMERA_HEIGHT - 128)/2, _tamagochiTiledTextureRegion, getVertexBufferObjectManager());
		tamagochi.animate(250);
		_mainScene.attachChild(tamagochi);
		return _mainScene;
	}

	private void doBindService() {
		bindService(new Intent(PlayActivity.this, TamadroidService.class), _serviceConnection, Context.BIND_AUTO_CREATE);
		_isServiceBound = true;
	}

	private void doUnbindService() {
		if (_isServiceBound) {
			if (_serviceConnection != null) {
				Message msg = Message.obtain(null, TamadroidService.MSG_REGISTER_CLIENT);
				try {
					_serviceMessenger.send(msg);
				} catch (RemoteException e) {}
			}
		}
	}
	
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if(pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if(_mainScene.hasChildScene()) {
				/* Remove the menu and reset it. */
				_menuScene.back();
			} else {
				/* Attach the menu. */
				_mainScene.setChildScene(_menuScene, false, true, true);
			}
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}
}
