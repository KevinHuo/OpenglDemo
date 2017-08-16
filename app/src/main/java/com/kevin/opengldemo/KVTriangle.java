package com.kevin.opengldemo;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.util.Calendar;

/**
 * Created by kevin on 2017/8/15.
 */

public class KVTriangle {
    private static final String V_POSITION = "vPosition";
    private static final String V_COLOR = "vColor";

    private FloatBuffer mFloatBuffer;
    private int mProgram;
    private static final int VERTEX_SIZE = 3;
    private static final int GL_FLOAT_SIZE = 4;

    static float coordinates[] = {
      -0.5f, -0.5f, 0f,
       0.5f, -0.5f, 0f,
         0f,  0.5f, 0f,
    };

    static float color[] = {0.7f,0.2f,0.4f,1f};

    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;"+
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";


    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";


    public KVTriangle(){
        mFloatBuffer = KVGLUtil.createFB(coordinates);

        int vertexShader = KVGLUtil.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = KVGLUtil.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram,vertexShader);
        GLES20.glAttachShader(mProgram,fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    public void Draw(float[] matrix){
        GLES20.glUseProgram(mProgram);

        int positionHandle = GLES20.glGetAttribLocation(mProgram,V_POSITION);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle,VERTEX_SIZE,GLES20.GL_FLOAT,false,VERTEX_SIZE*GL_FLOAT_SIZE,mFloatBuffer);

        int uMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(uMVPMatrixHandle,1,false,matrix,0);

        int colorHandle = GLES20.glGetUniformLocation(mProgram,V_COLOR);

        GLES20.glUniform4fv(colorHandle,1,color,0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,3);
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
