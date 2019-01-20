package com.example.demo.util.img;

import com.example.demo.util.SystemPropertyUtil;
import org.im4java.core.*;
import org.im4java.process.Pipe;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class GraphicsMagickUtil {

    /**
     *
     * @param input             需要拼接的数据
     * @param defaultFile       默认图片地址
     * @param imgBaseProperty   图片基础属性
     * @return
     * @throws Exception
     */
    public static byte[] fusionImg(byte[] input, String defaultFile, ImgBaseProperty imgBaseProperty) throws Exception {
        GMOperation op = new GMOperation();
        op.addImage("-");
        op.addImage(defaultFile);
        return commitGMOCmd(new Pipe(new ByteArrayInputStream(input),null), op , imgBaseProperty);
    }

    /**
     *
     * @param input             需要拼接的图片地址
     * @param defaultFile       默认的图片流信息
     * @param imgBaseProperty   图片基础属性
     * @return
     * @throws Exception
     */
    public static byte[] fusionImg(String input, byte[] defaultFile, ImgBaseProperty imgBaseProperty) throws Exception {
        GMOperation op = new GMOperation();
        op.addImage(input);
        op.addImage("-");
        return commitGMOCmd(new Pipe(new ByteArrayInputStream(defaultFile), null), op, imgBaseProperty);
    }

    /**
     *
     * @param input             需要拼接的图片地址
     * @param defaultFile       背景图片地址
     * @param imgBaseProperty   图片基础信息
     * @return
     * @throws Exception
     */
    public static byte[] fusionImg(String input, String defaultFile, ImgBaseProperty imgBaseProperty) throws Exception {
        GMOperation op = new GMOperation();
        op.addImage(input);
        op.addImage(defaultFile);
        return commitGMOCmd(null, op, imgBaseProperty);
    }

    /**
     * 图片拼接,并将拼接后的信息已byte数组的形式返回
     * @param pipe  需要拼接的图片的流信息
     * @param op    拼接命令
     * @return
     */
    private static byte[] commitGMOCmd(Pipe pipe, GMOperation op, ImgBaseProperty imgBaseProperty){
        // 图片质量
        op.quality(imgBaseProperty.getQuality());
        // 图片开始的坐标和规格
        op.geometry(imgBaseProperty.getWidth(), imgBaseProperty.getHeight(), imgBaseProperty.getX(), imgBaseProperty.getY());
        // 图片输出属性
        op.addImage("png:-");
        //IM4JAVA是同时支持ImageMagick和GraphicsMagick的，如果为true则使用GM，如果为false支持IM。
        CompositeCmd cmd = new CompositeCmd(true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 如果输入流不为空
        if (null != pipe){
            cmd.setInputProvider(pipe);
        }
        // 设置输出流信息
        cmd.setOutputConsumer(new Pipe(null, outputStream));
        commitCmd(op);
        return outputStream.toByteArray();
    }


    //绘制透明背景的文字图片
    public static byte[] writeTextToImg(FontUtil contents) throws Exception {
        BufferedImage image = new BufferedImage(contents.getWidth(), contents.getHeight(), BufferedImage.TYPE_INT_RGB);//构建画板
        Graphics2D g2d = image.createGraphics();

        //设置透明start
        image = g2d.getDeviceConfiguration().createCompatibleImage(contents.getWidth(), contents.getHeight(), Transparency.TRANSLUCENT);
        g2d = image.createGraphics();
        //设置透明end
        //消除锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(contents.getFont()); //设置字体
        g2d.setColor(contents.getColor());//在换成所需要的字体颜色

        //处理文字
        g2d.drawString(contents.getContext(), contents.getX(), contents.getY()); //输出文字
        g2d.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);//输出png图片
        return outputStream.toByteArray();
    }

    /**
     * 裁剪图片
     *
     * @param imagePath         源图片路径
     * @param newPath           处理后图片路径
     * @param imgBaseProperty   坐标规格
     *

     */
    public static void cutImage(String imagePath, String newPath,ImgBaseProperty imgBaseProperty){
        IMOperation op = new IMOperation();
        op.addImage(imagePath);
        op.crop(imgBaseProperty.getWidth(), imgBaseProperty.getHeight(), imgBaseProperty.getX(), imgBaseProperty.getY());
        op.addImage(newPath);
        commitCmd(op);
    }

    /**
     * 提交命令
     * @param op
     */
    private static void commitCmd(Operation op){
        ConvertCmd cmd = new ConvertCmd(true);

        //windows本地配置，  linux下不要设置此值，不然会报错
        if(SystemPropertyUtil.getOsName().indexOf("win") != -1) {
            cmd.setSearchPath("D:\\soft\\GraphicsMagick\\GraphicsMagick-1.3.30-Q16");
        }
        try {// 提交命令
            cmd.run(op);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片压缩
     * @param sourcePath        源图片地址
     * @param targetPath        压缩后图片地址
     * @param quality           质量
     * @param size              压缩后大小
     * @throws Exception
     */
    public static void compress(String sourcePath, String targetPath, double quality, Integer size) throws Exception{
        GMOperation op = new GMOperation();
        //待处理图片的绝对路径
        op.addImage(sourcePath);
        //图片压缩比，有效值范围是0.0-100.0，数值越大，缩略图越清晰  s
        op.quality(quality);
        //width 和height可以是原图的尺寸，也可以是按比例处理后的尺寸
        op.addRawArgs("-gravity", "center");
        op.resize(null, size);
        //处理后图片的绝对路径
        File smallFile = new File(targetPath);
        if (!smallFile.getParentFile().exists()) {
            smallFile.mkdir();
        }
        op.addImage(targetPath);

        commitCmd(op);
    }
}
