package info.acidflow.tamadroid;

import info.acidflow.tamadroid.helper.SpriteCreator;
import info.acidflow.tamadroid.model.SimpleTamagochi;
import info.acidflow.tamadroid.model.Tamagochi;
import info.acidflow.tamadroid.opener.InputStreamAssetOpener;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class PlayActivity extends SimpleBaseGameActivity {
	
	private static int CAMERA_WIDTH = 960;
	private static int CAMERA_HEIGHT = 1600;
	private static boolean FULLSCREEN = true;
	private static ScreenOrientation ORIENTATION = ScreenOrientation.PORTRAIT_FIXED;
	
	private TiledTextureRegion _tamagochiTiledTexture;
	private ITextureRegion _backgroundTextureRegion;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(FULLSCREEN, ORIENTATION, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	protected void onCreateResources() {
	    try {
	    	//Creating textures
			ITexture backgroundTexture = new BitmapTexture(this.getTextureManager(), new InputStreamAssetOpener(getAssets(), "gfx/background.png"));
			SpriteCreator.createTiledTextureRegionForAnimatedSprite("gfx/sprite_egg.png", getTextureManager(), 512, 512, this, 0, 0, 2, 2);
			SpriteCreator.getTexture().load();
			_tamagochiTiledTexture = SpriteCreator.getTiledTexture();
			// Loading textures in VRAM
			backgroundTexture.load();
			// Setting up texture regions
			_backgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Scene onCreateScene() {
		Scene scene = new Scene();
		Sprite backgroundSprite = new Sprite(0,0, _backgroundTextureRegion, getVertexBufferObjectManager());
		Tamagochi tamagochi = new SimpleTamagochi((CAMERA_WIDTH - 128)/2, (CAMERA_HEIGHT - 128)/2, _tamagochiTiledTexture, getVertexBufferObjectManager());
		scene.attachChild(backgroundSprite);
		tamagochi.animate(500);
		scene.attachChild(tamagochi);
		return scene;
	}

}
