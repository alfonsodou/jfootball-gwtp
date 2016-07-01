/**
 * 
 */
package org.javahispano.jfootball.client.application.widget.viewmatch;

import static com.shc.webgl4j.client.WebGL10.GL_ARRAY_BUFFER;
import static com.shc.webgl4j.client.WebGL10.GL_COLOR_BUFFER_BIT;
import static com.shc.webgl4j.client.WebGL10.GL_DEPTH_BUFFER_BIT;
import static com.shc.webgl4j.client.WebGL10.GL_DEPTH_TEST;
import static com.shc.webgl4j.client.WebGL10.GL_FLOAT;
import static com.shc.webgl4j.client.WebGL10.GL_FRAGMENT_SHADER;
import static com.shc.webgl4j.client.WebGL10.GL_STATIC_DRAW;
import static com.shc.webgl4j.client.WebGL10.GL_TRIANGLES;
import static com.shc.webgl4j.client.WebGL10.GL_VERTEX_SHADER;
import static com.shc.webgl4j.client.WebGL10.glAttachShader;
import static com.shc.webgl4j.client.WebGL10.glBindBuffer;
import static com.shc.webgl4j.client.WebGL10.glBufferData;
import static com.shc.webgl4j.client.WebGL10.glClear;
import static com.shc.webgl4j.client.WebGL10.glClearColor;
import static com.shc.webgl4j.client.WebGL10.glCompileShader;
import static com.shc.webgl4j.client.WebGL10.glCreateBuffer;
import static com.shc.webgl4j.client.WebGL10.glCreateProgram;
import static com.shc.webgl4j.client.WebGL10.glCreateShader;
import static com.shc.webgl4j.client.WebGL10.glDrawArrays;
import static com.shc.webgl4j.client.WebGL10.glEnable;
import static com.shc.webgl4j.client.WebGL10.glEnableVertexAttribArray;
import static com.shc.webgl4j.client.WebGL10.glGetAttribLocation;
import static com.shc.webgl4j.client.WebGL10.glGetUniformLocation;
import static com.shc.webgl4j.client.WebGL10.glLinkProgram;
import static com.shc.webgl4j.client.WebGL10.glShaderSource;
import static com.shc.webgl4j.client.WebGL10.glUniformMatrix4fv;
import static com.shc.webgl4j.client.WebGL10.glUseProgram;
import static com.shc.webgl4j.client.WebGL10.glVertexAttribPointer;

import org.javahispano.jfootball.shared.joml.Matrix4f;

import com.google.gwt.canvas.client.Canvas;

/**
 * @author adou
 *
 */
/**
 * @author Sri Harsha Chilakapati
 */
public class ViewMatch extends Animation {
	private int programID;
	private Matrix4f projView;

	public ViewMatch(Canvas canvas) {
		super(canvas);
	}

	@Override
	public void init() {
		glClearColor(0, 0, 0, 1);

		glEnable(GL_DEPTH_TEST);

		// The vertex shader source
		String vsSource = "precision mediump float;                             \n"
				+ "                                                     \n"
				+ "uniform mat4 proj;                                   \n"
				+ "                                                     \n"
				+ "attribute vec3 position;                             \n"
				+ "attribute vec4 color;                                \n"
				+ "                                                     \n"
				+ "varying vec4 vColor;                                 \n"
				+ "                                                     \n"
				+ "void main()                                          \n"
				+ "{                                                    \n"
				+ "    vColor = color;                                  \n"
				+ "    gl_Position = proj * vec4(position, 1.0);        \n" + "}";

		// The fragment shader source
		String fsSource = "precision mediump float;                      \n"
				+ "varying vec4 vColor;                          \n"
				+ "                                              \n"
				+ "void main()                                   \n"
				+ "{                                             \n"
				+ "    gl_FragColor = vColor;                    \n" + "}";

		// Create the vertex shader
		int vsShaderID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vsShaderID, vsSource);
		glCompileShader(vsShaderID);

		// Create the fragment shader
		int fsShaderID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fsShaderID, fsSource);
		glCompileShader(fsShaderID);

		// Create the program
		programID = glCreateProgram();
		glAttachShader(programID, vsShaderID);
		glAttachShader(programID, fsShaderID);
		glLinkProgram(programID);
		glUseProgram(programID);

		// Create the positions VBO
		float[] vertices = {
				// Front face
				-1, +1, +1, -1, -1, +1, +1, +1, +1, +1, +1, +1, -1, -1, +1, +1, -1, +1,

				// Right face
				+1, +1, +1, +1, -1, +1, +1, +1, -1, +1, +1, -1, +1, -1, +1, +1, -1, -1,

				// Back face
				+1, +1, -1, +1, -1, -1, -1, -1, -1, -1, -1, -1, -1, +1, -1, +1, +1, -1,

				// Left face
				-1, +1, -1, -1, -1, -1, -1, -1, +1, -1, -1, +1, -1, +1, +1, -1, +1, -1,

				// Top face
				-1, +1, -1, -1, +1, +1, +1, +1, -1, +1, +1, -1, -1, +1, +1, +1, +1, +1,

				// Bottom face
				-1, -1, -1, -1, -1, +1, +1, -1, -1, +1, -1, -1, -1, -1, +1, +1, -1, +1 };

		int vboPosID = glCreateBuffer();
		glBindBuffer(GL_ARRAY_BUFFER, vboPosID);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		int positionLoc = glGetAttribLocation(programID, "position");
		glVertexAttribPointer(positionLoc, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(positionLoc);

		// Create the colors VBO
		float[] colors = {
				// Front face
				1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1,

				// Right face
				1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1,

				// Back face
				1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,

				// Left face
				1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,

				// Top face
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,

				// Bottom face
				0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1 };

		int vboColID = glCreateBuffer();
		glBindBuffer(GL_ARRAY_BUFFER, vboColID);
		glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);

		int colorLoc = glGetAttribLocation(programID, "color");
		glVertexAttribPointer(colorLoc, 4, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(colorLoc);

		projView = new Matrix4f();
	}

	private float angle = 0;

	@Override
	public void render() {
		angle++;

		projView.setPerspective((float) Math.toRadians(70), 640f / 480f, 0.1f, 100).translate(0, 0, -4)
				.rotateX((float) Math.toRadians(angle)).rotateY((float) Math.toRadians(angle))
				.rotateZ((float) Math.toRadians(angle));

		glUniformMatrix4fv(glGetUniformLocation(programID, "proj"), false, projView.get(new float[16]));

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glDrawArrays(GL_TRIANGLES, 0, 36);
	}
}
