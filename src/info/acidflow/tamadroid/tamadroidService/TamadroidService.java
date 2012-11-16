package info.acidflow.tamadroid.tamadroidService;

import info.acidflow.tamadroid.config.EggConfig;
import info.acidflow.tamadroid.database.DatabaseInterface;
import info.acidflow.tamadroid.egg.EggUpdater;

import java.util.Timer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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
	public static final int MSG_EGG_STARTED = 7;
	public static final int MSG_RADIATOR_SET = 8;
	
	/**
	 * Argument type constant for data transmission through messages 
	 */
	public static final String FOOD_AMOUNT = "foodAmount";
	public static final String HEAL_AMOUNT = "healAmount";
	public static final String PLAY_AMOUNT = "playAmount";
	public static final String SWITCH_STATE = "switchState";
	
	
	/** 
	 * Messengers for communication with the activity
	 */
	private Messenger _activityMessenger; 
	private final Messenger _serviceMessenger = new Messenger(new ServiceIncomingMessageHandler(this)); 

	/**
	 * Database interface for communication with the DB
	 */
	DatabaseInterface _db;
	
	/**
	 * Timers for regular updates
	 *
	 */
	Timer _eggTimer;
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		return _serviceMessenger.getBinder();
	}

	@Override
	public void onCreate() {
		Log.i(LOG_TAG, "Creating service");
		Toast.makeText(getApplicationContext(), "Creating service", Toast.LENGTH_LONG).show();
		//_db = new ???? @TODO 
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Log.i(LOG_TAG, "Destroying service");
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

	public void startEggTimer() {
		_eggTimer = new Timer();
		_eggTimer.schedule(new EggUpdater(this), 0, 30000);
		Log.i(LOG_TAG, "Egg timer started");
	}
	
	public void setRadiator() {
		_db.setRadiatorState(true);
	}

	public void updateEgg() {
		// If the egg is being heated up, update breed timer
		if (_db.getRadiatorState()) {
			_db.updateEggTime(_db.getEggTime() + 1.0);
		}
		// If the egg has reached the require breed time, open it
		if (_db.getEggTime() >= EggConfig.EGG_REQUIRED_BREED_TIME) {
			_db.setEggOpen();
			// Notify the activity that the egg state has changed
			Message msg = Message.obtain(null, MSG_EGG_BROKEN);
			sendMessage(msg);
		}
	}
	
	private boolean sendMessage(Message msg) {
		if (_activityMessenger != null) {
			try {
				_activityMessenger.send(msg);
				return true;
			} catch (RemoteException e) {
				return false;
			}
		}
		return false;
	}
}
