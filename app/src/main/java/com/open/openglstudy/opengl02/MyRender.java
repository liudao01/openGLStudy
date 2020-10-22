package com.open.openglstudy.opengl02;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * render 实现类.
 *
 * @author liuml
 * @time 2020/10/22 3:34 PM
 */

public class MyRender implements GLSurfaceView.Renderer {

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {


        /**
         * x,y 以像素为单位，指定了窗口的左下角位置。
         *
         * width,height表示视口矩形的宽度和高度，根据窗口的实时变化重绘窗口。
         */
        //设置区域，x, y, width, height
        GLES20.glViewport(0, 0, width, height);

        //此函数可以实现分屏功能
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        //清空屏幕，擦除屏幕上所有的颜色,清屏缓冲区
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //用glClearColor定义的颜色填充
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
    }
}
