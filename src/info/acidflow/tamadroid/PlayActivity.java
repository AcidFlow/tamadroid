package info.acidflow.tamadroid;

import info.acidflow.tamadroid.helper.SpriteCreator;
import info.acidflow.tamadroid.model.SimpleTamagochi;
import info.acidflow.tamadroid.model.Tamagochi;
import info.acidflow.tamadroid.opener.InputStreamAssetOpener;
import info.acidflow.tamadroid.tamadroidService.ActivityIncomingMessageHandler;
import info.acidflow.tamadroid.tamadroidService.TamadroidService;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class PlayActivity extends SimpleBaseGameActivity {

	private static int CAMERA_WIDTH = 960;
	private static int CAMERA_HEIGHT = 1600;
	private static boolean FULLSCREEN = true;
	private static ScreenOrientation ORIENTATION = ScreenOrientation.PORTRAIT_FIXED;
	private Camera _camera;

	private TiledTextureRegion _tamagochiTiledTexture;
	private ITextureRegion _backgroundTextureRegion;
	private ITextureRegion _hudRegion;

	
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
			//Creating textures
			ITexture backgroundTexture = new BitmapTexture(this.getTextureManager(), new InputStreamAssetOpener(getAssets(), "gfx/background.png"));
			ITexture hudRegion = new BitmapTexture(this.getTextureManager(), new InputStreamAssetOpener(getAssets(), "gfx/sprite_adult_gg.png"));
			SpriteCreator.createTiledTextureRegionForAnimatedSprite("gfx/sprite_egg.png", getTextureManager(), this, 2, 2);
			SpriteCreator.getTexture().load();
			_tamagochiTiledTexture = SpriteCreator.getTiledTexture();
			// Loading textures in VRAM
			backgroundTexture.load();
			hudRegion.load();
			// Setting up texture regions
			_backgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
			_hudRegion = TextureRegionFactory.extractFromTexture(hudRegion);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Scene onCreateScene() {
		Scene scene = new Scene();
		HUD hud = new HUD();
		Sprite hudSprite = new Sprite(0,0, _hudRegion, getVertexBufferObjectManager()){

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				Log.i("TEST", "TOUCHED");
				return true;
			}
		};
		scene.registerTouchArea(hudSprite);
		hud.attachChild(hudSprite);
		_camera.setHUD(hud);
		Sprite backgroundSprite = new Sprite(0,0, _backgroundTextureRegion, getVertexBufferObjectManager());
		Tamagochi tamagochi = new SimpleTamagochi((CAMERA_WIDTH - 128)/2, (CAMERA_HEIGHT - 128)/2, _tamagochiTiledTexture, getVertexBufferObjectManager());
		scene.attachChild(backgroundSprite);
		tamagochi.animate(500);
		scene.attachChild(tamagochi);
		return scene;
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
	
}
