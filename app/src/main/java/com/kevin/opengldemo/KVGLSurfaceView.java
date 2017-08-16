package com.kevin.opengldemo;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by kevin on 2017/8/15.
 */

public class KVGLSurfaceView extends GLSurfaceView{
    private KVGLRenderer mRender;

    public KVGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);

        mRender = new KVGLRenderer();
        setRenderer(mRender);

    }
}
