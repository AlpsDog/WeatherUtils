package com.wl.weatherlib.interfaces;

/**
 * @Project: WeatherUtils
 * @Package: com.wl.weatherlib.interfaces
 * @Author: HSL
 * @Time: 2019/07/25 14:20
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public interface TempEntry {

    /**
     * 获取温度值
     *
     * @return
     */
    int getTempValue();

    /**
     * 获取温度文本
     *
     * @return
     */
    String getTempText();

}
