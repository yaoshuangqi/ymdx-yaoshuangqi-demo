package cx.examination.enroll;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/5/11 14:19
 * @Version 1.0
 */
public class MainTest {

    public static void main(String[] args) {
        //水印字体,Font.PLAIN可以自定义设置
        Font font = new Font("微软雅黑", Font.PLAIN, 15);
        String srcImgPath="F:/image/准考证模板.png"; //源图片地址
        String headImg="F:/image/头像.jpg"; //头像地址
        String tarImgPath="F:/image/结果图.jpeg"; //待存储的地址
        String name="余晶";  //水印内容
        String age="女";  //水印内容
        String idcard="420921199202034234";  //水印内容
        String posi="沌阳街道办文化中心";  //水印内容
        String examNo="HY20220605425";  //水印内容
        String subject="心理学";  //水印内容
        String time="2022-05-30 14:00";  //水印内容
        Color color=new Color(0,25,25,225);//水印图片色彩以及透明度（黑色）
        //MainTest.addWaterMark(srcImgPath,headImg, tarImgPath, color,
                //font,name,age,idcard,posi,subject,time,examNo);
        //readExcelData("C:\\Users\\Dell\\Desktop\\考试\\测试.xls");
    }


    /**图片处理方法
     * @param srcImgPath 源图片路径
     * @param headImg 头像路径
     * @param tarImgPath 保存的图片路径
     * @param markContentColor 水印颜色
     * @param font 水印字体
     * @param name 考生信息（姓名）
     * @param age 考生信息（性别）
     * @param idcard 考生信息（证件号）
     * @param posi 考生信息（考点）
     * @param subject 考生信息（科目）
     * @param time 考生信息（考试时间）
     * @param examNo 考生信息（准考证号）
     */
    public static void addWaterMark(String srcImgPath, String headImg, String tarImgPath,
                                    Color markContentColor, Font font, String name, String age,
                                    String idcard, String posi, String subject, String time, String examNo) {

        try {
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);//得到文件(底图)
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片(底图)
            File headFile = new File(headImg);//得到文件（头像）
            //Image headSrcImg = ImageIO.read(headFile);//文件转化为图片(头像)
            Image headSrcImg = ImageIO.read(new URL("https://docimg10.docs.qq.com/image/rBJDz4FKzxpLE9ySiJmfMw.jpeg?w=1656&h=2427&_type=jpeg"));//文件转化为图片(头像)
            //获取图片宽高
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 创建在内存里的图像缓冲区对象
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();//在缓冲区创建图形
            //底图生成+位置设置（设置参数图片的原尺寸）
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);// 绘制底图模板
            //考生信息字体设置
            g.setColor(markContentColor); //根据图片的背景设置水印颜色
            g.setFont(font);              //设置字体
            //头像生成+位置设置     //水平 //图片大小（水平=在底图的某个位置）
            g.drawImage(headSrcImg,485,31,108,125,null);// 绘制头像

            //设置水印的坐标（考生信息水平位置设置）
//            int x = srcImgWidth - 2*getWatermarkLength(waterMarkContent, g);
//            int y = srcImgHeight - 2*getWatermarkLength(waterMarkContent, g);
            g.drawString(name, 94, 117);  //画出水印(姓名)
            g.drawString(age, 366, 117);  //画出水印(性别)
            g.drawString(idcard, 94, 153);  //画出水印(身份证号码)
            g.drawString(posi, 94, 188);  //画出水印(考试地点)
            g.drawString(subject, 366, 188);  //画出水印(科目)
            String[] times = time.split(" ");
            g.drawString(times[0], 523, 188);  //画出水印(考试时间)
            g.drawString(times[1], 523, 210);  //画出水印(考试时间)
            g.drawString(examNo, 366, 151);  //画出水印(准考证号码)
            g.dispose();//对bufImg对象设置的参数进行提交！·
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            System.out.println("添加水印完成");
            outImgStream.flush();
            outImgStream.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}
