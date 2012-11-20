package info.acidflow.tamadroid.menu.callback;

import info.acidflow.tamadroid.menu.MenuCallback;
import info.acidflow.tamadroid.tamadroidService.TamadroidService;

import org.andengine.entity.scene.menu.MenuScene;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class SwitchHeatingOnCallback implements MenuCallback {

	private Messenger _messenger;
	private MenuScene _menu;
	
	public SwitchHeatingOnCallback(Messenger m, MenuScene ms){
		_messenger = m;
		_menu = ms;
	}
	
	@Override
	public void callback() {
		Message m = new Message();
		m.what = TamadroidService.MSG_RADIATOR_SET;
		try {
			_messenger.send(m);
			_menu.back();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
