package ru.xupoh.base.engine.core;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import ru.xupoh.base.engine.rendering.Window;

public class Input {
	public static final int KEYS_COUNT = 257;
	public static final int MOUSE_COUNT = 5;
	
	// In ms
	private static final long doubleClickCooldown = 250 * 1000 * 1000;
	
	private static boolean[] lastKeys = new boolean[KEYS_COUNT];
	private static boolean[] lastMouse = new boolean[MOUSE_COUNT];
	
	private static ClickInfo[] mouseClicks = new ClickInfo[MOUSE_COUNT];
	
	static class ClickInfo {
		public int clicks;
		public long time;
		
		public ClickInfo() {
			clicks = 1;
			time = 0;
		}
		
		public ClickInfo(int clicks, long time) {
			this.clicks = clicks;
			this.time = time;
		}
		
		public void increaseClicks() {
			++this.clicks;
			
			if (this.clicks > 2) {
				this.clicks = 2;
			}
		}
		
		public void cleanClicks() {
			clicks = 1;
		}
		
		@Override
		public String toString() {
			return String.format("(lastTime=%d, clicks=%d)", time, clicks);
		}
	}
	

	public static GLFWCursorPosCallback mousePositionListener = new GLFWCursorPosCallback() {
		@Override
		public void invoke(long window, double xpos, double ypos) {
		}
	};
	
	public static final GLFWMouseButtonCallback mouseListener = new GLFWMouseButtonCallback() {
		@Override
		public void invoke(long window, int button, int action, int mods) {
			if (action == 0) {
				ClickInfo info = mouseClicks[button];
				
				if (info == null)
					return;
				
				long dif = Time.getTime() - info.time;
				
				if (dif <= doubleClickCooldown) {
					info.increaseClicks();
				} else {
					info.cleanClicks();
				}
				
				info.time = Time.getTime();
				mouseClicks[button] = info; // Save changes
				
				lastMouse[button] = true;
			}
		}
	};
	
	public static void update() {
		for(int i = 0; i < KEYS_COUNT; i++)
			lastKeys[i] = getKey(i);
		
		for(int i = 0; i < MOUSE_COUNT; i++) {
			lastMouse[i] = getMouse(i);
			mouseClicks[i] = new ClickInfo();
		}
	}

	public static boolean getKey(int i)
	{
		return glfwGetKey(Window.getWindowID(), i) == GLFW_PRESS;
	}
	
	public static boolean getKeyDown(int keyCode)
	{
		return getKey(keyCode) && !lastKeys[keyCode];
	}
	
	public static boolean getKeyUp(int keyCode)
	{
		return !getKey(keyCode) && lastKeys[keyCode];
	}
	
	public static boolean getMouse(int i)
	{
		return glfwGetMouseButton(Window.getWindowID(), i) == GLFW_PRESS;
	}
	
	public static boolean getMouseDown(int mouseButton)
	{
		return getMouse(mouseButton) && !lastMouse[mouseButton];
	}
	
	public static boolean getMouseUp(int mouseButton)
	{
		return !getMouse(mouseButton) && lastMouse[mouseButton];
	}
	
	public static int getMouseClicks(int i) {
		return mouseClicks[i].clicks;
	}
	
	public static Vector2f getMousePosition() {
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
		
		glfwGetCursorPos(Window.getWindowID(), x, y);
		
		return new Vector2f((float)x.get(), (float)y.get());
	}
	
	public static void setMousePosition(Vector2f pos) {
		glfwSetCursorPos(Window.getWindowID(), pos.getX(), pos.getY());
	}
	
	public static boolean isCursorDisabled () {
		return glfwGetInputMode(Window.getWindowID(), GLFW_CURSOR) != GLFW_CURSOR_NORMAL; 
	}
	
	public static void showCursor(boolean show) {
		if (!show)
			glfwSetInputMode(Window.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		else
			glfwSetInputMode(Window.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
	}
}