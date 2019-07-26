package com.wl.weatherutils.bean;

import com.wl.weatherlib.interfaces.TempEntry;

/**
 * @Project: WeatherUtils
 * @Package: com.wl.weatherutils.bean
 * @Author: HSL
 * @Time: 2019/07/25 17:41
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class TempItem implements TempEntry {

    private int temp;
    private String text;

    public TempItem(int temp, String text) {
        this.temp = temp;
        this.text = text;
    }

    @Override
    public int getTempValue() {
        return temp;
    }

    @Override
    public String getTempText() {
        return text;
    }


    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
