package info.acidflow.tamadroid.tamadroidService;

import java.util.TimerTask;

public class PetUpdater extends TimerTask {

	private TamadroidService _service;
	
	public PetUpdater( TamadroidService service ) {
		_service = service;
	}
	
	@Override
	public void run() {
		_service.updatePet();
	}

}
