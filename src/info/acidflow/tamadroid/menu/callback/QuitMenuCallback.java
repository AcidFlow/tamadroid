package info.acidflow.tamadroid.menu.callback;

import android.app.Activity;
import info.acidflow.tamadroid.menu.MenuCallback;

public class QuitMenuCallback implements MenuCallback {

	private Activity _activity;
	
	public QuitMenuCallback(Activity a){
		_activity = a;
	}
	
	@Override
	public void callback() {
		_activity.finish();
	}

}
