package ru.xupoh.base.engine.core;

import java.util.Random;

public class Mathf {
	public static float clamp(float a, float b, float c) {
		if (c > b)
			return b;
		
		if (c < a)
			return a;
		
		return c;
	}
	
	public static float smoothLerp(float a, float b, float t) {
		t = Mathf.clamp(0, 1, t);
		
		return a * (1.0f - t) + b * t;
	}

	public static float abs(double red) {
		return (float) Math.abs(red);
	}
	
	public static float abs(float red) {
		return Math.abs(red);
	}

	public static float max(float... vals) {
		if (vals.length == 1)
			return vals[0];
		
		float assumed = -Float.MAX_VALUE;
		
		for (float v : vals)
			if (v > assumed)
				assumed = v;
		
		return assumed;
	}
	
	public static int getRandomi(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	public static float min(float... vals) {
		if (vals.length == 1)
			return vals[0];
		
		float assumed = Float.MAX_VALUE;
		
		for (float v : vals)
			if (v < assumed)
				assumed = v;
		
		return assumed;
	}
}