package com.kevin.opengldemo;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by kevin on 2017/8/15.
 */

public class KVGLUtil {
    public static int SIZE_OF_FLOAT = 4;

    public static FloatBuffer createFB(float[] array){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(array.length * SIZE_OF_FLOAT);
        byteBuffer.order(ByteOrder.nativeOrder());

        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(array);
        floatBuffer.position(0);
        return floatBuffer;
    }

    public static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}

