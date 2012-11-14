package info.acidflow.tamadroid.tamadroidService;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ActivityIncomingMessageHandler extends Handler {

	private final static String LOG_TAG = "ActivityIncomingMessageHandler";
	
	@Override
	public void handleMessage(Message msg) {
		switch(msg.what) {
		case TamadroidService.MSG_EGG_BROKEN: {
			Log.i(LOG_TAG, "Egg is broken ! ");
		}
		break;
		default: {
			super.handleMessage(msg);
		}
		}

	}

}
