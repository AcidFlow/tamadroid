package info.acidflow.tamadroid.database;

public interface DatabaseInterface {

	public double getEggTime();
	public void updateEggTime(double time);
	public boolean getRadiatorState();
	public void setRadiatorState(boolean state);
	public boolean isEggOpen();
	public void setEggOpen();	
}
