package info.acidflow.tamadroid.tamadroidService;

import android.os.Handler;
import android.os.Message;

/**
 * Check msg.getData() != null ? 
 * 
 *
 */

/**
 * 
 * Sort the different message types receivable by the service and call the 
 * appropriate method on the service with the datas found in the message if available
 *
 */
public class ServiceIncomingMessageHandler extends Handler {

	private TamadroidService _service; 
	
	public ServiceIncomingMessageHandler(TamadroidService service) {
		super();
		_service = service;
	}
	
	@Override
	public void handleMessage(Message msg) {
		switch(msg.what) {
		case TamadroidService.MSG_REGISTER_CLIENT: {
			_service.registerClient(msg.replyTo);
		} break;
		case TamadroidService.MSG_UNREGISTER_CLIENT: {
			_service.unregisterClient();
		} break;
		case TamadroidService.MSG_FEED_PET: {
			_service.feedPet(msg.getData().getDouble(TamadroidService.FOOD_AMOUNT));
		}
		break;
		case TamadroidService.MSG_HEAL_PET: {
			_service.healPet(msg.getData().getDouble(TamadroidService.HEAL_AMOUNT));
		}
		break;
		case TamadroidService.MSG_PLAY_WITH_PET: {
			_service.playWithPet(msg.getData().getDouble(TamadroidService.PLAY_AMOUNT));
		}
		break;
		case TamadroidService.MSG_SWITCH_LIGHT: {
			_service.switchLight(msg.getData().getBoolean(TamadroidService.SWITCH_STATE));
		}
		break;
		case TamadroidService.MSG_EGG_STARTED: {
			_service.startEggTimer();
		}
		case TamadroidService.MSG_RADIATOR_SET: {
			_service.setRadiator();
		}
		default: super.handleMessage(msg);
		}
	}
	
}
