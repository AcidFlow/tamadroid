package info.acidflow.tamadroid.model;



import info.acidflow.tamadroid.annotation.MaxValue;
import info.acidflow.tamadroid.model.food.Food;

import java.lang.reflect.Field;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class Tamagochi extends AnimatedSprite {
	
	private static double MIN_WEIGHT = 2;
	
	private double _age;
	private double _weight;
	@MaxValue(max=1)
	private double _hunger;
	@MaxValue(max=1)
	private double _thirst;
	@MaxValue(max=1)
	private double _energy;
	@MaxValue(max=1)
	private double _cleanliness;
	@MaxValue(max=1)
	private double _health;
	@MaxValue(max=1)
	private double _morale;
	
	protected Tamagochi(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		_age = 0;
		_weight = Math.random() + MIN_WEIGHT;
		_hunger = 1;
		_thirst = 1;
		_energy = 1;
		_cleanliness = 1;
		_health = 1;
		_morale = 1;
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
	
	public void eat(Food f){
		_hunger += f.getReduceHungerFactor();
		_weight += f.getIncreaseWeightFactor();
		cutAtMaximumValues();
		//TODO gout etc...
	}
	
	/**
	 * Set any attribute to its maximum value if annotated 
	 */
	private void cutAtMaximumValues(){
		for(Field f : getClass().getDeclaredFields()){
			if(f.isAnnotationPresent(MaxValue.class)){
				try {
					if(f.getDouble(this) > f.getAnnotation(MaxValue.class).max()){
						f.setDouble(this, f.getAnnotation(MaxValue.class).max());
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
