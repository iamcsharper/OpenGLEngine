package ru.xupoh.base.engine.rendering;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWvidmode;

import ru.xupoh.base.engine.core.Input;
import ru.xupoh.base.engine.core.Vector2f;

public class Window {
	private static long windowId;
	private static int width, height;
	private static String title;

	private static GLFWKeyCallback dummyKeyListener = new GLFWKeyCallback() {
		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
		}
	};
	
	private static GLFWWindowSizeCallback dummyResizer = new GLFWWindowSizeCallback() {
		
		@Override
		public void invoke(long window, int w, int h) {
			glViewport(0, 0, w, h);
		}
	};

	public static boolean isCloseRequested() {
		if (windowId == NULL)
			return false;

		return glfwWindowShouldClose(windowId) == GL_TRUE;
	}

	public static long getWindowID() {
		return windowId;
	}

	public static void createWindow(int w, int h, String title)
			throws Exception {
		if (glfwInit() != GL_TRUE) {
			throw new Exception("Error: Coulnd't initialize the GLFW lib!");
		}

		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		windowId = glfwCreateWindow(w, h, title, NULL, NULL);

		if (windowId == NULL) {
			throw new Exception("Error: Coulnd't initialize the window!");
		}
		
		Window.setTitle(title);

		width = w;
		height = h;

		int x = (getScreenWidth() - w) / 2;
		int y = (getScreenHeight() - h) / 2;

		setPosition(x, y);

		glfwMakeContextCurrent(windowId);
		glfwShowWindow(windowId);
		glfwSetKeyCallback(windowId, dummyKeyListener);
		glfwSetMouseButtonCallback(windowId, Input.mouseListener);
		glfwSetCursorPosCallback(windowId, Input.mousePositionListener);
		glfwSetWindowSizeCallback(windowId, dummyResizer);
	}

	public static int getScreenWidth() {
		return GLFWvidmode.width(glfwGetVideoMode(glfwGetPrimaryMonitor()));
	}

	public static int getScreenHeight() {
		return GLFWvidmode.height(glfwGetVideoMode(glfwGetPrimaryMonitor()));
	}

	public static void setPosition(int x, int y) {
		glfwSetWindowPos(windowId, x, y);
	}

	public static void render() {
		if (windowId == NULL)
			return;

		glfwSwapBuffers(windowId);
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static void closeWindow() {
		glfwSetWindowShouldClose(windowId, GL_TRUE);
	}

	public static String getTitle() {
		return title;
	}

	public static void setTitle(String title) {
		Window.title = title;
		
		glfwSetWindowTitle(windowId, title);
	}

	public static Vector2f getCenterPoint() {
		return new Vector2f(getWidth() / 2, getHeight() / 2);
	}
}