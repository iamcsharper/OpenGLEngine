package ru.xupoh.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import ru.xupoh.base.engine.core.Utils;
import ru.xupoh.base.engine.core.Vector3f;

public class Mesh {
	private int vbo;
	private int ibo;
	private int size;
	
	public Mesh(String fileName) {
		initMeshData();
		loadMesh(fileName);
	}
	
	public Mesh(Vertex[] vertices, int[] indices) {
		this(vertices, indices, false);
	}
	
	private void initMeshData() {
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		size = 0;
	}

	public Mesh(Vertex[] vertices, int[] indices, boolean b) {
		initMeshData();
		addVertices(vertices, indices, b);
	}

	public int getSize() {
		return size;
	}
	
	private void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals)
	{
		if (calcNormals)
			calcNormals(vertices, indices);
		
		size = indices.length;
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, Utils.createFlippedBuffer(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.createFlippedBuffer(indices), GL_STATIC_DRAW);
	}
	
	private void calcNormals (Vertex[] vertices, int[] indices) {
		for (int i = 0; i < indices.length; i += 3) {
			int f = indices[i];
			int s = indices[i + 1];
			int t = indices[i + 2];
			
			Vector3f v1 = vertices[s].getPos().sub(vertices[f].getPos());
			Vector3f v2 = vertices[t].getPos().sub(vertices[f].getPos());
			
			Vector3f normal = v1.cross(v2).normalized();
			
			vertices[f].setNormal(vertices[f].getNormal().add(normal));
			vertices[s].setNormal(vertices[s].getNormal().add(normal));
			vertices[t].setNormal(vertices[t].getNormal().add(normal));
		}
		
		for (int i = 0; i < vertices.length; ++i) {
			vertices[i].setNormal(vertices[i].getNormal().normalized());
		}
	}
	
	public void draw()
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}
	
	private void loadMesh(String fileName)
	{
		String[] splitArray = fileName.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		
		if(!ext.equals("obj"))
		{
			System.err.println("Error: File format not supported for mesh data: " + ext);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		BufferedReader meshReader = null;
		
		try
		{
			meshReader = new BufferedReader(new FileReader("./res/models/" + fileName));
			String line;
			
			while((line = meshReader.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = Utils.removeEmptyStrings(tokens);
				
				if(tokens.length == 0 || tokens[0].equals("#"))
					continue;
				else if(tokens[0].equals("v"))
				{
					vertices.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]),
														 Float.valueOf(tokens[2]),
														 Float.valueOf(tokens[3]))));
				}
				else if(tokens[0].equals("f"))
				{
					indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
					indices.add(Integer.parseInt(tokens[2].split("/")[0]) - 1);
					indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
					
					if(tokens.length > 4)
					{
						indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
						indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
						indices.add(Integer.parseInt(tokens[4].split("/")[0]) - 1);
					}
				}
			}
			
			meshReader.close();
			
			Vertex[] vertexData = new Vertex[vertices.size()];
			vertices.toArray(vertexData);
			
			Integer[] indexData = new Integer[indices.size()];
			indices.toArray(indexData);
			
			addVertices(vertexData, Utils.toIntArray(indexData), true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}