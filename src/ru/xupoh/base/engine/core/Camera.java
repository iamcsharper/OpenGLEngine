package ru.xupoh.base.engine.core;

import org.lwjgl.glfw.GLFW;

import ru.xupoh.base.engine.rendering.Window;


public class Camera {
	public static final Vector3f yAxis = new Vector3f(0, 1, 0);

	private Vector3f pos;
	private Vector3f forward;
	private Vector3f up;
	private Matrix4f projection;

	public Camera(float fov, float aspect, float zNear, float zFar) {
		this.pos = new Vector3f(0, 0, 0);
		this.forward = new Vector3f(0, 0, 1).normalized();
		this.up = new Vector3f(0, 1, 0).normalized();
		this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
	}

	public Matrix4f getViewProjection() {
		Matrix4f cameraRotation = new Matrix4f().initRotation(forward, up);
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(-pos.getX(), -pos.getY(), -pos.getZ());

		return projection.mul(cameraRotation.mul(cameraTranslation));
	}

	boolean mouseLocked;
	
	public void input() {
		float sensitivity = 0.5f;
		float movAmt = (float)(10 * Time.getDelta());
//		float rotAmt = (float)(100 * Time.getDelta());
		
		if(Input.getKey(GLFW.GLFW_KEY_ESCAPE))
		{
			Input.showCursor(true);
			mouseLocked = false;
		}
		if(Input.getMouseDown(0))
		{
			Input.setMousePosition(Window.getCenterPoint());
			Input.showCursor(false);
			mouseLocked = true;
		}
		
		if(Input.getKey(GLFW.GLFW_KEY_W))
			move(getForward(), movAmt);
		if(Input.getKey(GLFW.GLFW_KEY_S))
			move(getForward(), -movAmt);
		if(Input.getKey(GLFW.GLFW_KEY_A))
			move(getLeft(), movAmt);
		if(Input.getKey(GLFW.GLFW_KEY_D))
			move(getRight(), movAmt);
		
		if(mouseLocked)
		{
			Vector2f deltaPos = Input.getMousePosition().sub(Window.getCenterPoint());
			
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;
			
			if(rotY)
				rotateY(deltaPos.getX() * sensitivity);
			if(rotX)
				rotateX(-deltaPos.getY() * sensitivity);
				
			if(rotY || rotX)
				Input.setMousePosition(new Vector2f(Window.getWidth()/2, Window.getHeight()/2));
		}
	}

	public void move(Vector3f dir, float amt) {
		pos = pos.add(dir.mul(amt));
		
		System.out.println(pos);
	}

	public void rotateY(float angle) {
		Vector3f Haxis = yAxis.cross(forward).normalized();

		forward = forward.rotate(angle, yAxis).normalized();

		up = forward.cross(Haxis).normalized();
	}

	public void rotateX(float angle) {
		Vector3f Haxis = yAxis.cross(forward).normalized();

		forward = forward.rotate(angle, Haxis).normalized();

		up = forward.cross(Haxis).normalized();
	}

	public Vector3f getLeft() {
		return forward.cross(up).normalized();
	}

	public Vector3f getRight() {
		return up.cross(forward).normalized();
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector3f getForward() {
		return forward;
	}

	public void setForward(Vector3f forward) {
		this.forward = forward;
	}

	public Vector3f getUp() {
		return up;
	}

	public void setUp(Vector3f up) {
		this.up = up;
	}
}