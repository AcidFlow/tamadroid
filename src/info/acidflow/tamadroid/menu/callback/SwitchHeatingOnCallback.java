package info.acidflow.tamadroid.menu.callback;

import info.acidflow.tamadroid.menu.MenuCallback;
import info.acidflow.tamadroid.tamadroidService.TamadroidService;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class SwitchHeatingOnCallback implements MenuCallback {

	private Messenger _messenger;
	
	public SwitchHeatingOnCallback(Messenger m){
		_messenger = m;
	}
	
	@Override
	public void callback() {
		Message m = new Message();
		m.what = TamadroidService.MSG_RADIATOR_SET;
		try {
			_messenger.send(m);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
