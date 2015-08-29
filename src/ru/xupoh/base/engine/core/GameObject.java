package ru.xupoh.base.engine.core;

import java.util.ArrayList;

import ru.xupoh.base.engine.rendering.Shader;

public class GameObject {
	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private Transform transform;
	
	public GameObject() {
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public void addComponent(GameComponent component) {
		component.setGameObject(this);
		components.add(component);
	}
	
	public void addChild(GameObject child) {
		children.add(child);
	}
	
	public void fixedUpdate () {
		for (GameComponent component : components)
			component.fixedUpdate();
		
		for (GameObject child : children)
			child.fixedUpdate();
	}
	
	public void update() {
		for (GameComponent component : components)
			component.update();
		
		for (GameObject child : children)
			child.update();
	}
	
	public void render (Shader shader) {
		for (GameComponent component : components)
			component.render(shader);
		
		for (GameObject child : children)
			child.render(shader);
	}
}