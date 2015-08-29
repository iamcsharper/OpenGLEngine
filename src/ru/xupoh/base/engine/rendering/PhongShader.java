package ru.xupoh.base.engine.rendering;

import ru.xupoh.base.engine.core.Color;
import ru.xupoh.base.engine.core.Matrix4f;
import ru.xupoh.base.engine.core.Transform;
import ru.xupoh.base.engine.core.Utils;
import ru.xupoh.base.engine.core.Vector3f;

public class PhongShader extends Shader {
	private static final int MAX_POINT_LIGHTS = 4;
	private static final int MAX_SPOT_LIGHTS = 4;
	
	public static PhongShader instance = new PhongShader();
	
	private static Color ambientLight = new Color();
	private static DirectionalLight directionalLight = new DirectionalLight(new BaseLight(Color.WHITE, 0f), new Vector3f());
	private static PointLight[] pointLights = new PointLight[]{};
	private static SpotLight[] spotLights = new SpotLight[]{};
	
	public PhongShader() {
		super();
		
		addVertexShaderFromFile("phongVertex.vs");
		addFragmentShaderFromFile("phongFragment.fs");
		this.compileShader();
		
		this.addUniform("transform");
		this.addUniform("transformProjected");
		this.addUniform("baseColor");
		this.addUniform("ambientLight");
		this.addUniform("hasTexture");
		
		this.addUniform("specularIntensity");
		this.addUniform("specularPower");
		this.addUniform("eyePos");

		this.addUniform("directionalLight.base.color");
		this.addUniform("directionalLight.base.intensity");
		this.addUniform("directionalLight.direction");

		for (int i = 0; i < MAX_POINT_LIGHTS; ++i) {
			this.addUniform("pointLights[" + i + "].base.color");
			this.addUniform("pointLights[" + i + "].base.intensity");
			this.addUniform("pointLights[" + i + "].atten.constant");
			this.addUniform("pointLights[" + i + "].atten.linear");
			this.addUniform("pointLights[" + i + "].atten.exponent");
			this.addUniform("pointLights[" + i + "].position");
			this.addUniform("pointLights[" + i + "].range");
		}
		
		for (int i = 0; i < MAX_SPOT_LIGHTS; ++i) {
			this.addUniform("spotLights[" + i + "].pointLight.base.color");
			this.addUniform("spotLights[" + i + "].pointLight.base.intensity");
			this.addUniform("spotLights[" + i + "].pointLight.atten.constant");
			this.addUniform("spotLights[" + i + "].pointLight.atten.linear");
			this.addUniform("spotLights[" + i + "].pointLight.atten.exponent");
			this.addUniform("spotLights[" + i + "].pointLight.position");
			this.addUniform("spotLights[" + i + "].pointLight.range");
			
			this.addUniform("spotLights[" + i + "].direction");
			this.addUniform("spotLights[" + i + "].cutoff");
		}
	}

	@Override
	public void updateUniforms(Transform transform, Material material) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
		
		material.getTexture().bind();
		
		setUniform("transform", worldMatrix);
		setUniform("transformProjected", projectedMatrix);
		setUniform("baseColor", material.getColor().toVector3f());
		setUniform("ambientLight", PhongShader.getAmbientLight().toVector3f());
		setUniform("directionalLight", directionalLight);
		
		for (int i = 0; i < pointLights.length; ++i)
			setUniform("pointLights[" + i + "]", pointLights[i]);
		
		for (int i = 0; i < spotLights.length; ++i)
			setUniform("spotLights[" + i + "]", spotLights[i]);
		
		setUniform("eyePos", getRenderingEngine().getMainCamera().getPos());
		setUniformf("specularIntensity", material.getSpecularIntensity());
		setUniformf("specularPower", material.getSpecularExponent());
	}

	public void setUniform(String uniformName, PointLight pointLight) {
		setUniform(uniformName + ".base", pointLight.getBaseLight());
		setUniformf(uniformName + ".atten.constant", pointLight.getAttenuation().getConstant());
		setUniformf(uniformName + ".atten.linear", pointLight.getAttenuation().getLinear());
		setUniformf(uniformName + ".atten.exponent", pointLight.getAttenuation().getExponent());
		setUniform(uniformName + ".position", pointLight.getPosition());
		setUniformf(uniformName + ".range", pointLight.getRange());
	}
	
	public void setUniform(String uniformName, SpotLight spotLight) {
		setUniform(uniformName + ".pointLight", spotLight.getPointLight());
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniformf(uniformName + ".cutoff", spotLight.getCutoff());
	}

	public static Color getAmbientLight() {
		return ambientLight;
	}

	public static void setAmbientLight(Color ambientLight) {
		PhongShader.ambientLight = ambientLight;
	}

	public static PhongShader getInstance() {
		
		return instance;
	}
	
	public void setUniform(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getVectorColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniform(String uniformName, DirectionalLight dirLight)
	{
		setUniform(uniformName + ".base", dirLight.getBase());
		setUniform(uniformName + ".direction", dirLight.getDirection());
	}

	public static DirectionalLight getDirectionalLight() {
		return directionalLight;
	}

	public static void setDirectionalLight(DirectionalLight directionalLight) {
		PhongShader.directionalLight = directionalLight;
	}

	public static void setPointLights(PointLight[] pointLights) {
		if (pointLights.length > MAX_POINT_LIGHTS) {
			System.err.println("Too much point lights!");
			
			pointLights = Utils.<PointLight>shrinkArray(pointLights, MAX_POINT_LIGHTS);
		}
		
		PhongShader.pointLights = pointLights;
	}
	
	public static void setSpotLights(SpotLight[] spotLights) {
		if (pointLights.length > MAX_SPOT_LIGHTS) {
			System.err.println("Too much spot lights!");
			
			PhongShader.spotLights = Utils.<SpotLight>shrinkArray(spotLights, MAX_SPOT_LIGHTS);
		}
		
		PhongShader.spotLights = spotLights;
	}
}