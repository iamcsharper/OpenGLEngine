package ru.xupoh.base.engine.rendering;

import ru.xupoh.base.engine.core.Color;
import ru.xupoh.base.engine.core.Vector3f;

public class BaseLight {
	private Color color;
	private float intensity;

	public BaseLight(Color color, float intensity) {
		this.color = color;
		this.intensity = intensity;
	}
	
	public Vector3f getVectorColor() {
		return color.toVector3f();
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
}
