package ru.xupoh.base.game;

import ru.xupoh.base.engine.core.GameComponent;
import ru.xupoh.base.engine.rendering.Material;
import ru.xupoh.base.engine.rendering.Mesh;
import ru.xupoh.base.engine.rendering.Shader;

public class MeshRenderer extends GameComponent {
	private Mesh mesh;
	private Material material;

	public MeshRenderer(Mesh mesh, Material mat) {
		this.mesh = mesh;
		this.material = mat;
	}

	@Override
	public void render(Shader shader) {
		shader.bind();
		shader.updateUniforms(this.gameObject.getTransform(), this.material);
		mesh.draw();
	}
}