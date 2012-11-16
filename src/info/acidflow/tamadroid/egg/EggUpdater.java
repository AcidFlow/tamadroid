package info.acidflow.tamadroid.egg;

import info.acidflow.tamadroid.tamadroidService.TamadroidService;

import java.util.TimerTask;

import android.util.Log;

public class EggUpdater extends TimerTask {

	TamadroidService _service;
	
	public EggUpdater(TamadroidService service) {
		_service = service;
	}
	
	@Override
	public void run() {
		_service.updateEgg();
		Log.i("EggUpdater", "Egg updated");
	}

}
