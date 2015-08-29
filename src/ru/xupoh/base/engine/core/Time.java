package ru.xupoh.base.engine.core;

public class Time {
	public static final long SECOND = 1000000000L; 
	private static float delta;
	
	public static long getTime() {
		return System.nanoTime();
	}
	
	public static float getDelta() {
		return delta;
	}
	
	public static void setDelta(float delta) {
		Time.delta = delta;
	}
}
