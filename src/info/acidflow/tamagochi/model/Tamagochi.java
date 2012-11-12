package info.acidflow.tamagochi.model;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class Tamagochi extends AnimatedSprite {
	
	private static double MIN_WEIGHT = 2;
	
	private double _age;
	private double _weight;
	private double _hunger;
	private double _thirst;
	private double _energy;
	private double _cleanliness;
	private double _health;
	private double _morale;
	
	protected Tamagochi(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		_age = 0;
		_weight = Math.random() + MIN_WEIGHT;
		_hunger = 100;
		_thirst = 100;
		_energy = 100;
		_cleanliness = 100;
		_health = 100;
		_morale = 100;
	}
	
	public double getAge() {
		return _age;
	}

	public void setAge(double _age) {
		this._age = _age;
	}

	public double getWeight() {
		return _weight;
	}

	public void setWeight(double _weight) {
		this._weight = _weight;
	}

	public double getHunger() {
		return _hunger;
	}

	public void setHunger(double _hunger) {
		this._hunger = _hunger;
	}

	public double getThirst() {
		return _thirst;
	}

	public void setThirst(double _thirst) {
		this._thirst = _thirst;
	}

	public double getEnergy() {
		return _energy;
	}

	public void setEnergy(double _energy) {
		this._energy = _energy;
	}

	public double getCleanliness() {
		return _cleanliness;
	}

	public void setCleanliness(double _cleanliness) {
		this._cleanliness = _cleanliness;
	}

	public double getHealth() {
		return _health;
	}

	public void setHealth(double _health) {
		this._health = _health;
	}

	public double getMorale() {
		return _morale;
	}

	public void setMorale(double _morale) {
		this._morale = _morale;
	}

}
