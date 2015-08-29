package ru.xupoh.base.engine.rendering;

import ru.xupoh.base.engine.core.Vector3f;

public class PointLight {
	private BaseLight baseLight;
	private Attenuation attenuation;
	private Vector3f position;
	private float range;
	
	public PointLight(BaseLight baseLight, Attenuation attenuation, Vector3f position, float range) {
		this.baseLight = baseLight;
		this.attenuation = attenuation;
		this.position = position;
		this.setRange(range);
	}
	public BaseLight getBaseLight() {
		return baseLight;
	}
	public void setBaseLight(BaseLight baseLight) {
		this.baseLight = baseLight;
	}
	public Attenuation getAttenuation() {
		return attenuation;
	}
	public void setAttenuation(Attenuation attenuation) {
		this.attenuation = attenuation;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public float getRange() {
		return range;
	}
	public void setRange(float range) {
		this.range = range;
	}
	
	@Override
	public String toString() {
		return "PointLight [baseLight=" + baseLight + ", attenuation=" + attenuation + ", position=" + position + "]";
	}
}