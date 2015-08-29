package ru.xupoh.base.engine.core;

public class Vector2f {
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2f() {
		this(0, 0);
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}
	
	public Vector2f normalize() {
		float length = length();
		
		x /= length;
		y /= length;
		
		return this;
	}
	
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	public Vector2f sub(Vector2f from) {
		return new Vector2f(this.x - from.getX(), this.y - from.getY());
	}
	
	public Vector2f sub(float r) {
		return new Vector2f(this.x - r, this.y - r);
	}
	
	public Vector2f mul(Vector2f from) {
		return new Vector2f(this.x * from.getX(), this.y * from.getY());
	}
	
	public Vector2f mul(float r) {
		return new Vector2f(this.x * r, this.y * r);
	}
	
	public Vector2f div(Vector2f from) {
		return new Vector2f(this.x / from.getX(), this.y / from.getY());
	}
	
	public Vector2f div(float r) {
		return new Vector2f(this.x / r, this.y / r);
	}
	
	public Vector2f add(Vector2f from) {
		return new Vector2f(this.x + from.getX(), this.y + from.getY());
	}
	
	public Vector2f add(float r) {
		return new Vector2f(this.x + r, this.y + r);
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
