package com.open.openglstudy.opengl04;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.open.openglstudy.R;

/**
 * opengl绘制图片
 */
public class OpenGL04Activity extends AppCompatActivity {
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