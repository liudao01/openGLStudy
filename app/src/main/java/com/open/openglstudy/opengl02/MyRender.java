package com.open.openglstudy.opengl02;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.open.openglstudy.R;
import com.open.openglstudy.util.XYShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * render 实现类.
 *
 * @author liuml
 * @time 2020/10/22 3:34 PM
 */

public class MyRender implements GLSurfaceView.Renderer {

    private Context context;

    private final float[] vertexData = {
            -1f, 0f,
            0f, 1f,
            1f, 0f
    };

    private FloatBuffer vertexBuffer;//顶点缓冲
    private int program;
    private int avPosition;
    private int afColor;


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
         */
        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexBuffer.position(0);

    }

    private IntBuffer intBufferUtil(int[] arr) {
        IntBuffer mBuffer;
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        // 数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
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

    private ShortBuffer shortBufferUtil(short[] arr) {
        ShortBuffer mBuffer;
        // 初始化ByteBuffer，长度为arr数组的长度*2，因为一个short占2个字节
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                arr.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        mBuffer = dlb.asShortBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        //找到顶点着色器,纹理着色器数据
        //加载顶点着色器和片段着色器用来修改图形的颜色，纹理，坐标等属性
        String vertexSource = XYShaderUtil.readRawText(context, R.raw.vertex_shader);
        String fragmentSource = XYShaderUtil.readRawText(context, R.raw.fragment_shader);
        program = XYShaderUtil.createProgram(vertexSource, fragmentSource);
        if (program > 0) {
            // 获取顶点着色器的位置的句柄,查询由program指定的先前链接的程序对象，用于name指定的属性变量，并返回绑定到该属性变量的通用顶点属性的索引
            avPosition = GLES20.glGetAttribLocation(program, "av_Position");
            // 获取片段着色器的颜色的句柄
            afColor = GLES20.glGetUniformLocation(program, "af_Color");
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
        // 将程序添加到OpenGL ES环境
        GLES20.glUseProgram(program);
        // 设置绘制三角形的颜色
        GLES20.glUniform4f(afColor, 1f, 0f, 0f, 1f);
        // 启用三角形顶点位置的句柄
        GLES20.glEnableVertexAttribArray(avPosition);
        //准备三角形坐标数据
        GLES20.glVertexAttribPointer(avPosition, 2, GLES20.GL_FLOAT, false, 8, vertexBuffer);
        // 绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }
}
