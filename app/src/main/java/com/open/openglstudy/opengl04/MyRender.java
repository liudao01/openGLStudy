package com.open.openglstudy.opengl04;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import com.open.openglstudy.R;
import com.open.openglstudy.util.XYShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * render 实现类.绘制图片
 *
 * @author liuml
 * @time 2020/10/22 3:34 PM
 */

public class MyRender implements GLSurfaceView.Renderer {

    private Context context;
    //顶点坐标
    private final float[] vertexData = {
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    };

    //纹理坐标  正常
    private final float[] textureData = {
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
    };
    //纹理坐标 倒立
//    private final float[] textureData={
//            1f,0f,
//            0f,0f,
//            1f, 1f,
//            0f, 1f
//    };


    private FloatBuffer vertexBuffer;//顶点缓冲 顶点buffer
    private FloatBuffer textureBuffer;//纹理缓存 纹理buffer
    private int program;
    private int avPosition;//顶点坐标
    private int afPosition;//纹理坐标
    private int textureId;//纹理id保存


    public MyRender(Context context) {
        this.context = context;
        /**
         * 分析:
         * 1. vertexData.length * 4 是内存的长度 因为是float 类型 4字节 所以 乘以4
         * 2. order(ByteOrder.nativeOrder()) 设置字节顺序,对齐方式.在内存当中 是以大段对其还是小端对齐
         * 3. asFloatBuffer() 转成floatBuffer,转换为Float型缓冲
         * 4. put(vertexData); 向缓冲区中放入顶点坐标数据  ,把顶点的值映射给他 put 进去,
         * 5. vertexBuffer.position(0);  最后把指针的变量指向开头 就是0,设置缓冲区起始位置
         *
         * order(ByteOrder.nativeOrder()) 为什么数据需要转换格式呢？主要是因为Java的缓冲区数据存储结构为大端字节序(BigEdian)，
         * 而OpenGl的数据为小端字节序（LittleEdian）,因为数据存储结构的差异，所以，
         * 在Android中使用OpenGl的时候必须要进行下转换。当然，
         * 一般我们在使用的时候都会做个简单的工具类。这里提供几个简单的封装。（占几个字节就初始化ByteBuffer长度的时候*几）
         *
         * allocateDirect 分配内存空间
         */

        vertexBuffer = floatBufferUtil(vertexData);

        textureBuffer = floatBufferUtil(textureData);


    }

    private FloatBuffer floatBufferUtil(float[] arr) {
        FloatBuffer mBuffer;
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个float占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        // 数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asFloatBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        //找到顶点着色器,纹理着色器数据
        //加载顶点着色器和片段着色器用来修改图形的颜色，纹理，坐标等属性
        String vertexSource = XYShaderUtil.readRawText(context, R.raw.image_vertex_shader);
        String fragmentSource = XYShaderUtil.readRawText(context, R.raw.image_fragment_shader);
        program = XYShaderUtil.createProgram(vertexSource, fragmentSource);
        if (program > 0) {
            // 获取顶点着色器的位置的句柄,查询由program指定的先前链接的程序对象，用于name指定的属性变量，并返回绑定到该属性变量的通用顶点属性的索引
            avPosition = GLES20.glGetAttribLocation(program, "av_Position");
            // 获取片段着色器的颜色的句柄
            afPosition = GLES20.glGetAttribLocation(program, "af_Position");

            //创建纹理
            int[] textureIds = new int[1];
            GLES20.glGenTextures(1, textureIds, 0);
            if (textureIds[0] == 0) {
                return;
            }

            textureId = textureIds[0];

            //绑定
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

            //设置参数 环绕（超出纹理坐标范围）：
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

            //过滤（纹理像素映射到坐标点
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.woailuo);
            if (bitmap == null) {
                return;
            }
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();
            bitmap = null;

        }

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


    //    设置视口的大小
    @Override
    public void onDrawFrame(GL10 gl10) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        //利用颜色清屏
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);


        //设置颜色 用了float 所以用4 f
//        GLES20.glUniform4f(afColor,0f,0f,1f,1f);

        //让program可用
        // 将程序添加到OpenGL ES环境
        GLES20.glUseProgram(program);

        //顶点坐标
        GLES20.glEnableVertexAttribArray(avPosition);//顶点位置的句柄
        GLES20.glVertexAttribPointer(avPosition, 2, GLES20.GL_FLOAT, false, 8, vertexBuffer);//准备图片顶点坐标数据
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);//绘制
        //纹理坐标
        GLES20.glEnableVertexAttribArray(afPosition);
        GLES20.glVertexAttribPointer(afPosition, 2, GLES20.GL_FLOAT, false, 8, textureBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
}
