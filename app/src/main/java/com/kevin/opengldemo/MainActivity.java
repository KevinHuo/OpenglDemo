package com.kevin.opengldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KVGLSurfaceView kVGLSurfaceView = new KVGLSurfaceView(this);
        setContentView(kVGLSurfaceView);
    }
}
