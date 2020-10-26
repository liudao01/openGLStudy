package com.open.openglstudy.opengl02;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by liuml on 2020/10/22 14:28
 */
public  class XYGLSurfaceView  extends GLSurfaceView {
    Context mContext;
    public XYGLSurfaceView(Context context) {
        super(context);
        mContext = context;
    }

//    注意GLSurfaceView的生成周期
    public XYGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        //设置openGL版本 2.0版本
        setEGLContextClientVersion(2);
        //设置Render
        setRenderer(new MyRender(context));
    }
}
