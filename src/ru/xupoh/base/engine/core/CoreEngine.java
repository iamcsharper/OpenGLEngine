package ru.xupoh.base.engine.core;

import static org.lwjgl.glfw.GLFW.*;
import ru.xupoh.base.engine.rendering.RenderingEngine;
import ru.xupoh.base.engine.rendering.Window;

public class CoreEngine implements Runnable {
	private Thread gameThread;

	private Game game;
	private RenderingEngine renderingEngine;

	private int width;
	private int height;
	private double frameTime;
	private String title;

	public CoreEngine(int width, int height, double framerate, String title, Game game) {
		this.game = game;
		this.width = width;
		this.height = height;
		this.frameTime = 1.0 / framerate;
		this.title = title;

		gameThread = new Thread(this, "Game");
	}

	@Override
	public void run() {
		init();

		int fps = 0;
		long frameCounter = 0;

		long lastTime = Time.getTime();
		float unproccessedTime = 0;

		while (!Window.isCloseRequested()) {
			boolean render = false;

			long startTime = Time.getTime();
			long passed = startTime - lastTime;
			lastTime = startTime;

			unproccessedTime += passed / (float) Time.SECOND;
			frameCounter += passed;

			while (unproccessedTime > frameTime) {
				render = true;

				unproccessedTime -= frameTime;

				Time.setDelta((float) frameTime);

				game.fixedUpdate();
				renderingEngine.fixedUpdate();

				if (frameCounter >= Time.SECOND) {
					System.out.println(fps);
					fps = 0;
					frameCounter = 0;
				}

				update();
			}

			if (render) {
				renderingEngine.render(game.getRootObject());
				Window.render();
				fps++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void update() {
		Input.update();
		game.update();

		glfwPollEvents();
	}

	private void init() {
		try {
			Window.createWindow(width, height, title);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.renderingEngine = new RenderingEngine();

		game.init();
	}

	public void start() {
		gameThread.start();
	}
}