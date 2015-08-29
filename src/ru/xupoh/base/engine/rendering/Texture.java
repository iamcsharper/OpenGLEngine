package ru.xupoh.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;

import ru.xupoh.base.engine.core.TextureLoader;

public class Texture {
	private int id;

	public Texture(String fileName) {
		this(loadTexture(fileName));
	}

	public Texture(int id) {
		this.id = id;
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public int getID() {
		return id;
	}

	private static int loadTexture(String fileName) {
		try {
			return TextureLoader.loadTexture(new FileInputStream(new File(
					"./res/textures/" + fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
}