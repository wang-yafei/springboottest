package com.example.demo.util.img;

import com.example.demo.util.text.ChineseTextUtil;
import lombok.Data;

import java.awt.*;

@Data
public class FontUtil extends ImgBaseProperty{
    /**
     * 文字内容
     */
    private String context;
    /**
     * 对齐方式
     */
    private Location location = Location.CENTER;//文字的位置，x与y的优先级更高
    /**
     * 背景颜色
     */
    private Color bgColor = Color.WHITE;//背景颜色
    /**
     * 文字颜色
     */
    private Color color = Color.BLACK;//文字颜色
    /**
     * 字体信息
     */
    private Font font = new Font(null, Font.PLAIN, 50);//字体信息


    public int getX() {
        if (x <= 0){
            return 0;
        }
        if (location == Location.LEFT){
            return 0;
        }else {
            int width = getContextWidth();
            x = getWidth() - width;
            if (x < 0){
                return 0;
            }
            if (location == Location.CENTER) {
                return x / 2;
            } else {
                return x;
            }
        }
    }

    //文字长度
    public int getContextWidth(){
        int chinaCount = 0;
        char c[] = context.toCharArray();
        for (int i = 0; i < context.length(); i++) {
            if (ChineseTextUtil.isChinese(c[i])) {
                chinaCount ++;
            }
        }
        return chinaCount * font.getSize() + (context.length() - chinaCount) * font.getSize()/2;
    }

}
