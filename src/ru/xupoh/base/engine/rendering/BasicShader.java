package ru.xupoh.base.engine.rendering;

import ru.xupoh.base.engine.core.Transform;

public class BasicShader extends Shader {
	private static BasicShader shader = new BasicShader();

	public static BasicShader getInstance() {
		return shader;
	}
	
	public BasicShader() {
		super();
		
		addVertexShaderFromFile("basicVertex.vs");
		addFragmentShaderFromFile("basicFragment.fs");
		this.compileShader();
		
		this.addUniform("transform");
		this.addUniform("color");
	}

	@Override
	public void updateUniforms(Transform transform, Material material) {
		material.getTexture().bind();
		
		setUniform("transform", transform.getTransformation());
		setUniform("color", material.getColor().toVector3f());
	}
}