package ru.xupoh.base.engine.core;

public class Vector3f extends Object {
	public static final Vector3f forward = new Vector3f( 0,  0,  1);
	public static final Vector3f back    = new Vector3f( 0,  0, -1);
	public static final Vector3f up      = new Vector3f( 0,  1,  0);
	public static final Vector3f down    = new Vector3f( 0, -1,  0);
	public static final Vector3f left    = new Vector3f(-1,  0,  0);
	public static final Vector3f right   = new Vector3f( 1,  0,  0);
	
	private float x, y, z;

	public Vector3f() {
		this(0, 0, 0);
	}
	
	public Vector3f clone() {
		return new Vector3f(x,y,z);
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f(Vector2f vec) {
		this();
		this.x = vec.getX();
		this.y = vec.getY();
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	public Vector3f cross(Vector3f r) {
		float _x = y * r.getZ() - z * r.getY();
		float _y = z * r.getX() - x * r.getZ();
		float _z = x * r.getY() - y * r.getX();

		return new Vector3f(_x, _y, _z);
	}

	public Vector3f rotate(float angle, Vector3f axis) {
		float sinHalfAngle = (float)Math.sin(Math.toRadians(angle / 2));
		float cosHalfAngle = (float)Math.cos(Math.toRadians(angle / 2));
		
		float rX = axis.getX() * sinHalfAngle;
		float rY = axis.getY() * sinHalfAngle;
		float rZ = axis.getZ() * sinHalfAngle;
		float rW = cosHalfAngle;
		
		Quaternion rotation = new Quaternion(rX, rY, rZ, rW);
		Quaternion conjugate = rotation.conjugate();
		
		Quaternion w = rotation.mul(this).mul(conjugate);
		
		x = w.getX();
		y = w.getY();
		z = w.getZ();
		
		return this;
	}

	public void normalize() {
		float length = length();

		x /= length;
		y /= length;
		z /= length;
	}
	
	public Vector3f normalized() {
		Vector3f vec = this.clone();
		vec.normalize();
		
		return vec;
	}
	
	public float distance(Vector3f to) {
		float xDif = this.x - to.x; 
		float yDif = this.y - to.y;
		float zDif = this.z - to.z;
		
		return (float)Math.sqrt(xDif * xDif + yDif * yDif + zDif * zDif);
	}

	public Vector3f sub(Vector3f from) {
		return new Vector3f(this.x - from.getX(), this.y - from.getY(), this.z
				- from.getZ());
	}

	public Vector3f sub(float r) {
		return new Vector3f(this.x - r, this.y - r, this.z - r);
	}

	public Vector3f mul(Vector3f from) {
		return new Vector3f(this.x * from.getX(), this.y * from.getY(), this.z
				* from.getZ());
	}

	public Vector3f mul(float r) {
		return new Vector3f(this.x * r, this.y * r, this.z * r);
	}

	public Vector3f div(Vector3f from) {
		return new Vector3f(this.x / from.getX(), this.y / from.getY(), this.z
				/ from.getZ());
	}

	public Vector3f div(float r) {
		return new Vector3f(this.x / r, this.y / r, this.z / r);
	}

	public Vector3f add(Vector3f from) {
		return new Vector3f(this.x + from.getX(), this.y + from.getY(), this.z
				+ from.getZ());
	}

	public Vector3f add(float r) {
		return new Vector3f(this.x + r, this.y + r, this.z + r);
	}

	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
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

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	public Vector3f clampAll(float f, float g) {
		return new Vector3f(Mathf.clamp(f, g, x), Mathf.clamp(f, g, y), Mathf.clamp(f, g, z));
	}
	
	public static Vector3f lerp(Vector3f a, Vector3f b, float t) {
		float x = Mathf.smoothLerp(a.getX(), b.getX(), t);
		float y = Mathf.smoothLerp(a.getY(), b.getY(), t);
		float z = Mathf.smoothLerp(a.getZ(), b.getZ(), t);
		
		return new Vector3f(x, y, z);
	}

	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}
}