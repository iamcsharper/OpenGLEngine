package ru.xupoh.base.engine.core;

import ru.xupoh.base.engine.rendering.Shader;

public abstract class GameComponent {
	protected GameObject gameObject;

	public GameComponent(GameObject go) {
		this.gameObject = go;
	}

	public GameComponent() {
	}

	public void setGameObject(GameObject go) {
		this.gameObject = go;
	}

	/**
	 * Called on start
	 */
	public void init() {
	}

	/**
	 * Called every frame
	 */
	public void update() {
	}

	/**
	 * Called every FIXED frame (framerate is defined in engine constructor)
	 */
	public void fixedUpdate() {
	}

	/**
	 * Called every render-frame
	 */
	public void render(Shader shader) {
	}
}