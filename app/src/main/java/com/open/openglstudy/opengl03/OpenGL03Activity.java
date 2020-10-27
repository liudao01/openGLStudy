package com.open.openglstudy.opengl03;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.open.openglstudy.R;

/**
 * opengl绘制四边形
 */
public class OpenGL03Activity extends AppCompatActivity {
    private XYGLSurfaceView xysurfaceview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl_quadrilateral);
        xysurfaceview = findViewById(R.id.xysurfaceview);

    }


//    注意GLSurfaceView的生成周期
    @Override
    protected void onPause() {
        super.onPause();
        xysurfaceview.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        xysurfaceview.onResume();
    }
}