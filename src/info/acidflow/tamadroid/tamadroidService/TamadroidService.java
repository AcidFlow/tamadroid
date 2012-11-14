package info.acidflow.tamadroid.tamadroidService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

public class TamadroidService extends Service {

	private static final String LOG_TAG = "TamadroidService";
	
	/**
	 * Message type constants for dialog between the service and the activity
	 */
	public static final int MSG_REGISTER_CLIENT = 0;
	public static final int MSG_UNREGISTER_CLIENT = 1;
	public static final int MSG_FEED_PET = 2;
	public static final int MSG_HEAL_PET = 3;
	public static final int MSG_PLAY_WITH_PET = 4;
	public static final int MSG_SWITCH_LIGHT = 5;
	public static final int MSG_EGG_BROKEN = 6;

	/**
	 * Argument type constant for data transmission through messages 
	 */
	public static final String FOOD_AMOUNT = "foodAmount";
	public static final String HEAL_AMOUNT = "healAmount";
	public static final String PLAY_AMOUNT = "playAmount";
	public static final String SWITCH_STATE = "switchState";
	
	
	private Messenger _activityMessenger; 
	private final Messenger _serviceMessenger = new Messenger(new ServiceIncomingMessageHandler(this)); 


	@Override
	public IBinder onBind(Intent arg0) {
		return _serviceMessenger.getBinder();
	}

	@Override
	public void onCreate() {
		Toast.makeText(getApplicationContext(), "Creating service", Toast.LENGTH_LONG).show();
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Toast.makeText(getApplicationContext(), "Destroying service", Toast.LENGTH_LONG).show();
		super.onDestroy();
	}

	public void registerClient(Messenger replyTo) {
		_activityMessenger = replyTo;
		Log.i(LOG_TAG, "Registering client");
	}
	
	public void unregisterClient() {
		_activityMessenger = null;
		Log.i(LOG_TAG, "Unregistering client");
	}
	
	public void feedPet(double amount) {
		Log.i(LOG_TAG, "Feeding pet : " + amount);
	}
	
	public void healPet(double amount) {
		Log.i(LOG_TAG, "Healing pet : " + amount);
	}
	
	public void playWithPet(double amount) {
		Log.i(LOG_TAG, "Playing with pet : " + amount);
	}
	
	public void switchLight(boolean state) {
		Log.i(LOG_TAG, "Switching light, light is now : " + state);
	}
}
