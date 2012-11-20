package info.acidflow.tamadroid.database;

public interface DatabaseInterface {

	/*
	 * Egg management
	 */
	public double getEggTime();
	public void updateEggTime(double time);
	public boolean getRadiatorState();
	public void setRadiatorState(boolean state);
	public boolean isEggOpen();
	public void setEggOpen();
	
	/*
	 * Pet management
	 
	public double getPetTime();
	public void updatePetTime(double time);
	public double getFoodLevel();
	public void setFoodLevel(double food);
	public double getHealthLevel();
	public void setHealthLevel(double health);
	public double getHappinessLevel();
	public void setHappinessLevel(double happiness);
	public int getPetRank();
	public void setPetRank(int rank); */
}
