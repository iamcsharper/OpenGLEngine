package ru.xupoh.base.game;

import ru.xupoh.base.engine.core.Color;
import ru.xupoh.base.engine.core.Game;
import ru.xupoh.base.engine.core.GameObject;
import ru.xupoh.base.engine.core.Vector2f;
import ru.xupoh.base.engine.core.Vector3f;
import ru.xupoh.base.engine.rendering.Material;
import ru.xupoh.base.engine.rendering.Mesh;
import ru.xupoh.base.engine.rendering.Texture;
import ru.xupoh.base.engine.rendering.Vertex;

public class TestGame extends Game {
	public void init() {
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;

		Vertex[] vertices = new Vertex[] {
				new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
				new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
				new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
				new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f)) };

		int indices[] = { 0, 1, 2, 2, 1, 3 };

		Material mat = new Material(new Texture("sas.png"), Color.WHITE, 1, 8);
		Mesh mesh = new Mesh(vertices, indices, true);

		MeshRenderer meshRenderer = new MeshRenderer(mesh, mat);
		
		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().setTranslation(0, -1, 5);
		
		getRootObject().addChild(planeObject);
	}
}