precision mediump float;
varying vec2 v_texPo;
uniform sampler2D sTexture;

void main(){
    gl_FragColor = texture2D(sTexture, v_texPo);

}

//uniform 用于在application中向vertex和fragment中传递值。
//
//precision mediump float; 精度
//varying vec2 v_texPo; 从顶点Shader 获取到的值
//uniform sampler2D sTexture; 纹理的属性 因为是在片元里面 所以用uniform修饰 传值用
//texture2D 把颜色取出来的方法 这里实际上就是做的两个坐标的转换

