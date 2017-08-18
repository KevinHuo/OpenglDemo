package com.kevin.opengldemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.FloatBuffer;

/**
 * Created by kevin on 2017/8/16.
 */

public class KVTextureTriangle {

    private FloatBuffer mFloatBuffer;
    private int mProgram;
    private static final int VERTEX_SIZE = 3;
    private static final int GL_FLOAT_SIZE = 4;
    private Context mContext;

    static float coordinates[] = {
            -0.5f, -0.5f, 0f,1.0f,0.0f,
            0.5f, -0.5f, 0f, 0.0f,0.0f,
            0f,  0.5f, 0f, 0.0f,0.5f,
    };

    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;"+
                    "attribute vec4 vPosition;" +
                    "attribute vec2 vTextureP;" +
                    "varying vec2 out_tp;" +
                    "void main() {" +
                    " out_tp = vTextureP;" +
                    " gl_Position = uMVPMatrix * vPosition;" +
                    "}";


    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec2 out_tp;" +
                    "uniform sampler2D u_TextureColor;" +
                    "void main() {" +
                    "  gl_FragColor = texture2D(u_TextureColor,out_tp);" +
                    "}";


    public KVTextureTriangle(Context context){
        mContext = context;
        mFloatBuffer = KVGLUtil.createFB(coordinates);

        int vertexShader = KVGLUtil.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = KVGLUtil.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram,vertexShader);
        GLES20.glAttachShader(mProgram,fragmentShader);
        GLES20.glLinkProgram(mProgram);

        int[] texure = new int[1];
        GLES20.glGenTextures(1,texure,0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texure[0]);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);

        Bitmap bitmap = KVGLUtil.createBitmap(mContext,R.drawable.timg);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        bitmap.recycle();
    }

    public void Draw(float[] matrix){
        GLES20.glUseProgram(mProgram);

        int positionHandle = GLES20.glGetAttribLocation(mProgram,"vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle,VERTEX_SIZE,GLES20.GL_FLOAT,false,5*GL_FLOAT_SIZE,mFloatBuffer);

        int positionHandle2= GLES20.glGetAttribLocation(mProgram,"vTextureP");
        GLES20.glEnableVertexAttribArray(positionHandle2);
        GLES20.glVertexAttribPointer(positionHandle2,2,GLES20.GL_FLOAT,false,5*GL_FLOAT_SIZE,mFloatBuffer);


        int uMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(uMVPMatrixHandle,1,false,matrix,0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,3);
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
