package ru.xupoh.base.engine.rendering;

import ru.xupoh.base.engine.core.Color;

public class Material {
	private Texture texture;
	private Color color;
	private float specularIntensity;
	private float specularExponent;
	
	public Material(Texture texture) {
		this(texture, Color.WHITE);
	}
	
	public Material(Texture tex, Color color) {
		this(tex, color, 2, 32);
	}
	
	public Material(Texture tex, Color color, float specularIntensity, float specularExponent) {
		this.texture = tex;
		this.color = color;
		this.specularExponent = specularExponent;
		this.specularIntensity = specularIntensity;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getSpecularIntensity() {
		return specularIntensity;
	}

	public void setSpecularIntensity(float specularIntensity) {
		this.specularIntensity = specularIntensity;
	}

	public float getSpecularExponent() {
		return specularExponent;
	}

	public void setSpecularExponent(float specularExponent) {
		this.specularExponent = specularExponent;
	}
}