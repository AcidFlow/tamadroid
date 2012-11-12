package info.acidflow.tamadroid.opener;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.util.adt.io.in.IInputStreamOpener;

import android.content.res.AssetManager;

public class InputStreamAssetOpener implements IInputStreamOpener {

	private AssetManager _assetManager;
	private String _file;
	
	public InputStreamAssetOpener(AssetManager am, String file){
		_assetManager = am;
		_file = file;
	}
	@Override
	public InputStream open() throws IOException {
		return _assetManager.open(_file);
	}

}
