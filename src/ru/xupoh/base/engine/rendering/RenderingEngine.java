package ru.xupoh.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GLContext;

import ru.xupoh.base.engine.core.Camera;
import ru.xupoh.base.engine.core.Color;
import ru.xupoh.base.engine.core.GameObject;

public class RenderingEngine {
	private Camera mainCamera;
	
	public RenderingEngine() {
		GLContext.createFromCurrent();

		glClearColor(0.9f, 0.8f, 0.7f, 0);

		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);

		glEnable(GL32.GL_DEPTH_CLAMP);

		glEnable(GL_TEXTURE_2D);
		
		mainCamera = new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getWidth(), 0.01f, 1000.0f);
	}
	
	public void fixedUpdate() {
		mainCamera.input();
	}

	public void render(GameObject object) {
		clearScreen();
		
		Shader shader = BasicShader.getInstance();
		shader.setRenderingEngine(this);
		
		object.render(BasicShader.getInstance());
	}

	public void clearScreen() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public static String getOpenGLVersion() {
		return glGetString(GL_VERSION);
	}

//	private void setTextures(boolean enabled) {
//		if (enabled)
//			glEnable(GL_TEXTURE_2D);
//		else
//			glDisable(GL_TEXTURE_2D);
//	}

	public void setClearColor(Color color) {
		color = color.clampAll(0f, 1f);
		glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public Camera getMainCamera() {
		return mainCamera;
	}

	public void setMainCamera(Camera mainCamera) {
		this.mainCamera = mainCamera;
	}
}