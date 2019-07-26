package com.wl.weatherutils;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wl.weatherutils.adapter.HourlyTempRvAdapter;
import com.wl.weatherutils.bean.TempItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HSL
 * @Time: 2019/7/25 10:24
 * @E-mail: xxx@163.com
 * @Description: 主页~
 */
public class MainActivity extends AppCompatActivity {

    RecyclerView weatherRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        List<TempItem> items = new ArrayList<>();
        items.add(new TempItem(24, "24℃"));
        items.add(new TempItem(25, "25℃"));
        items.add(new TempItem(27, "27℃"));
        items.add(new TempItem(23, "23℃"));
        items.add(new TempItem(26, "26℃"));
        items.add(new TempItem(28, "28℃"));
        items.add(new TempItem(30, "30℃"));
        items.add(new TempItem(32, "32℃"));
        items.add(new TempItem(36, "36℃"));
        items.add(new TempItem(30, "30℃"));
        items.add(new TempItem(28, "28℃"));
        items.add(new TempItem(26, "26℃"));

        weatherRv = findViewById(R.id.weather_rv);
        weatherRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        HourlyTempRvAdapter hourlyTempRvAdapter = new HourlyTempRvAdapter();
        hourlyTempRvAdapter.setEnableLoadMore(false);
        weatherRv.setAdapter(hourlyTempRvAdapter);
        hourlyTempRvAdapter.notifyDataChanged(items);
    }
}
