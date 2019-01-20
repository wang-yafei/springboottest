package com.example.demo.util.img;

import lombok.Data;

@Data
public class ImgBaseProperty {

    /**
     * 图片宽度
     */
    protected int width;

    /**
     * 图片高度
     */
    protected int height;

    /**
     * 图片x轴
     */
    protected int x;

    /**
     * 图片y轴
     */
    protected int y;

    /**
     * 图片质量
     */
    protected double quality;
}
