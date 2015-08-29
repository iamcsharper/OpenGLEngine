package ru.xupoh.base.engine.core;

public abstract class Game {
	private GameObject root = new GameObject();

	public void init() {}

	public void fixedUpdate() {
		getRootObject().fixedUpdate();
	}

	public void update() {
		getRootObject().update();
	}

	public GameObject getRootObject() {
		return root;
	}
}