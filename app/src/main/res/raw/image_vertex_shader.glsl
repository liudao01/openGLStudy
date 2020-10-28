attribute vec4 av_Position;//顶点坐标
attribute vec2 af_Position;//纹理坐标
varying vec2 v_texPo;//用于把纹理坐标传到fragment里面
void main(){
    v_texPo = af_Position;
    gl_Position = av_Position;
}
//varying 用于vertex和fragment之间传递值
//
//attribute vec4 av_Position; 这个是之前写过的 顶点坐标系 vec4 四个向量 x,y,z,w
//attribute vec2 af_Position; 这个是纹理坐标系 vec2 图片是2d的 就用两个向量x,y
//varying vec2 v_texPo; varying在顶点坐标和片元坐标之间传递数据 ,v_texPo 这个值需要在顶点坐标和片源坐标的文件中定义一直.