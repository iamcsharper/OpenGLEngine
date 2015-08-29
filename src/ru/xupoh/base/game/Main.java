package ru.xupoh.base.game;

import ru.xupoh.base.engine.core.CoreEngine;

public class Main {
	public static void main(String[] args) {
		CoreEngine engine = new CoreEngine(800, 600, 60, "3D Game Engine", new TestGame());
		engine.start();
	}
}