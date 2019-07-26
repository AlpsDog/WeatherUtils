package com.wl.weatherutils.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wl.weatherlib.widget.WeatherChartView;
import com.wl.weatherutils.R;
import com.wl.weatherutils.bean.TempItem;

import java.util.List;

/**
 * @Project: WeatherUtils
 * @Package: com.wl.weatherutils.adapter
 * @Author: HSL
 * @Time: 2019/07/25 17:40
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class HourlyTempRvAdapter extends BaseQuickAdapter<TempItem, BaseViewHolder> {

    public HourlyTempRvAdapter() {
        super(R.layout.item_rv_hour_temp);
    }

    @Override
    protected void convert(BaseViewHolder helper, TempItem item) {
        WeatherChartView view = helper.getView(R.id.temp_wrv);
        view.drawChartView(getData(), helper.getAdapterPosition());
    }

    public void notifyDataChanged(List<TempItem> data) {
        int maxTemp = 0;
        int minTemp = 0;
        for (int i = 0; i < data.size(); i++) {
            int temp = data.get(i).getTemp();
            if (temp > maxTemp) {
                maxTemp = temp;
            }
            if (temp < minTemp) {
                minTemp = temp;
            }
        }
        WeatherChartView.mMaxTemp = maxTemp;
        WeatherChartView.mMinTemp = minTemp;
        WeatherChartView.mNowPosition = 3;
        setNewData(data);
    }
}
