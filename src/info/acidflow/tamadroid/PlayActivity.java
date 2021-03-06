package info.acidflow.tamadroid;

import info.acidflow.tamadroid.helper.SpriteCreator;
import info.acidflow.tamadroid.menu.Menu;
import info.acidflow.tamadroid.menu.MenuElement;
import info.acidflow.tamadroid.menu.callback.DisplayMenuCallback;
import info.acidflow.tamadroid.menu.callback.SwitchHeatingOnCallback;
import info.acidflow.tamadroid.model.Heating;
import info.acidflow.tamadroid.model.SimpleTamagochi;
import info.acidflow.tamadroid.model.Tamagochi;
import info.acidflow.tamadroid.opener.InputStreamAssetOpener;
import info.acidflow.tamadroid.tamadroidService.ActivityIncomingMessageHandler;
import info.acidflow.tamadroid.tamadroidService.TamadroidService;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
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
	private MenuScene _menuThermometerScene;
	private Menu _menuThermometer;
	private ITextureRegion _thermometerTexture;
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
		try {
			BitmapTextureAtlas menuTexture = new BitmapTextureAtlas(getTextureManager(), 256, 256);
			_menuThermometer = new Menu(menuTexture, getVertexBufferObjectManager(), getAssets());
			_menuThermometer.addItem(MenuElement.THERMOMETER_ON, "gfx/btn_on.png", 0, 0);
			_menuThermometer.load();

			ITexture thermometerTexture = new BitmapTexture(getTextureManager(), new InputStreamAssetOpener(getAssets(), "gfx/thermometer.png"));
			thermometerTexture.load();
			_thermometerTexture = TextureRegionFactory.extractFromTexture(thermometerTexture);
			SpriteCreator.createTiledTextureRegionForAnimatedSprite("gfx/sprite_egg.png", getTextureManager(), this, 2, 2);
			SpriteCreator.getTexture().load();
			_tamagochiTiledTextureRegion = SpriteCreator.getTiledTexture();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Scene onCreateScene() {
		_menuThermometerScene = _menuThermometer.createMenuScene(_camera);
		_menuThermometer.getMenuItem(MenuElement.THERMOMETER_ON).setMenuCallback(new SwitchHeatingOnCallback(_serviceMessenger,  _menuThermometerScene));
		_mainScene = new Scene();
		_mainScene.setBackground(new Background(Color.GREEN));
		Heating thermometer = new Heating(10, 400, _thermometerTexture, getVertexBufferObjectManager());
		thermometer.setCallback(new DisplayMenuCallback(_mainScene, _menuThermometerScene));
		_mainScene.registerTouchArea(thermometer);
		_mainScene.attachChild(thermometer);
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
		if(pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if(_mainScene.hasChildScene()) {
				/* Remove the menu and reset it. */
				_menuThermometerScene.back();
			} else {
				/* Attach the menu. */
				finish();
			}
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}
}
