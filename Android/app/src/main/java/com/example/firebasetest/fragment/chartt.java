package com.example.firebasetest.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.firebasetest.R;
import com.macroyau.thingspeakandroid.ThingSpeakChannel;
import com.macroyau.thingspeakandroid.ThingSpeakLineChart;

import java.util.Calendar;

import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class chartt extends Fragment {

    private ThingSpeakChannel tsChannel1,tsChannel2,tsChannel3;
    private ThingSpeakLineChart tsChart1,tsChart2,tsChart3;
    private LineChartView chartView1,chartView2,chartView3;

    public chartt() {
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.chart2, container, false);

        tsChannel1 = new ThingSpeakChannel(884467);



//        tsChannel1.setChannelFeedUpdateListener(new ThingSpeakChannel.ChannelFeedUpdateListener() {
//            @Override
//            public void onChannelFeedUpdated(long channelId, String channelName, ChannelFeed channelFeed) {
//                // Notify last update time of the Channel feed through a Toast message
//               Date lastUpdate = channelFeed.getChannel().getUpdatedAt();
////                Toast.makeText(chartt.this, lastUpdate.toString(), Toast.LENGTH_LONG).show();
//            }
//        });

        tsChannel1.loadChannelFeed();



        // Create a Calendar object dated 5 minutes ago
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -5);

        // Configure LineChartView
        chartView1 = view.findViewById(R.id.chart);
        chartView2 = view.findViewById(R.id.chart22);
        chartView3 = view.findViewById(R.id.chart3);


        chartView1.setZoomEnabled(true);
        chartView1.setValueSelectionEnabled(true);

        // Create a line chart from Field1 of ThinkSpeak Channel 9
        tsChart1 = new ThingSpeakLineChart(884467, 1);
        // Get entries at maximum
        tsChart1.setNumberOfEntries(50);
        // Set value axis labels on 10-unit interval
        tsChart1.setValueAxisLabelInterval(2);
        // Set date axis labels on 5-minute interval
        tsChart1.setDateAxisLabelInterval(1);
        // Show the line as a cubic spline
        tsChart1.useSpline(true);
        // Set the line color
        tsChart1.setLineColor(Color.parseColor("#D32F2F"));
        // Set the axis color
        tsChart1.setAxisColor(Color.parseColor("#455a64"));
        // Set the starting date (5 minutes ago) for the default viewport of the chart
        tsChart1.setChartStartDate(calendar.getTime());
        // Set listener for chart data update
        tsChart1.setListener(new ThingSpeakLineChart.ChartDataUpdateListener() {
            @Override
            public void onChartDataUpdated(long channelId, int fieldId, String title, LineChartData lineChartData, Viewport maxViewport, Viewport initialViewport) {
                // Set chart data to the LineChartView
                chartView1.setLineChartData(lineChartData);
                // Set scrolling bounds of the chart
                chartView1.setMaximumViewport(maxViewport);
                // Set the initial chart bounds
                chartView1.setCurrentViewport(initialViewport);
           /* LineChartData data = new LineChartData();
            float data1=data.getBaseValue();
            TextView tvName = (TextView)findViewById(R.id.textView);
            tvName.setText((int) data1);*/

            }
        });
        // Load chart data asynchronously
        tsChart1.loadChartData();

        chartView2.setZoomEnabled(true);
        chartView2.setValueSelectionEnabled(true);

        // Create a line chart from Field1 of ThinkSpeak Channel 9
        tsChart2 = new ThingSpeakLineChart(884467, 2);
        // Get 200 entries at maximum
        tsChart2.setNumberOfEntries(50);
        // Set value axis labels on 10-unit interval
        tsChart2.setValueAxisLabelInterval(2);
        // Set date axis labels on 5-minute interval
        tsChart2.setDateAxisLabelInterval(1);
        // Show the line as a cubic spline
        tsChart2.useSpline(true);
        // Set the line color
        tsChart2.setLineColor(Color.parseColor("#166EE5"));
        // Set the axis color
        tsChart2.setAxisColor(Color.parseColor("#455a64"));
        // Set the starting date (5 minutes ago) for the default viewport of the chart
        tsChart2.setChartStartDate(calendar.getTime());
        // Set listener for chart data update
        tsChart2.setListener(new ThingSpeakLineChart.ChartDataUpdateListener() {
            @Override
            public void onChartDataUpdated(long channelId, int fieldId, String title, LineChartData lineChartData, Viewport maxViewport, Viewport initialViewport) {
                // Set chart data to the LineChartView
                chartView2.setLineChartData(lineChartData);
                // Set scrolling bounds of the chart
                chartView2.setMaximumViewport(maxViewport);
                // Set the initial chart bounds
                chartView2.setCurrentViewport(initialViewport);
           /* LineChartData data = new LineChartData();
            float data1=data.getBaseValue();
            TextView tvName = (TextView)findViewById(R.id.textView);
            tvName.setText((int) data1);*/

            }
        });
        // Load chart data asynchronously
        tsChart2.loadChartData();

        chartView3.setZoomEnabled(true);
        chartView3.setValueSelectionEnabled(true);

        // Create a line chart from Field1 of ThinkSpeak Channel 9
        tsChart3 = new ThingSpeakLineChart(884467, 3);
        // Get 200 entries at maximum
        tsChart3.setNumberOfEntries(50);
        // Set value axis labels on 10-unit interval
        tsChart3.setValueAxisLabelInterval(2);
        // Set date axis labels on 5-minute interval
        tsChart3.setDateAxisLabelInterval(1);
        // Show the line as a cubic spline
        tsChart3.useSpline(true);
        // Set the line color
        tsChart3.setLineColor(Color.parseColor("#7E412E"));
        // Set the axis color
        tsChart3.setAxisColor(Color.parseColor("#455a64"));
        // Set the starting date (5 minutes ago) for the default viewport of the chart
        tsChart3.setChartStartDate(calendar.getTime());
        // Set listener for chart data update
        tsChart3.setListener(new ThingSpeakLineChart.ChartDataUpdateListener() {
            @Override
            public void onChartDataUpdated(long channelId, int fieldId, String title, LineChartData lineChartData, Viewport maxViewport, Viewport initialViewport) {
                // Set chart data to the LineChartView
                chartView3.setLineChartData(lineChartData);
                // Set scrolling bounds of the chart
                chartView3.setMaximumViewport(maxViewport);
                // Set the initial chart bounds
                chartView3.setCurrentViewport(initialViewport);
           /* LineChartData data = new LineChartData();
            float data1=data.getBaseValue();
            TextView tvName = (TextView)findViewById(R.id.textView);
            tvName.setText((int) data1);*/

            }
        });
        // Load chart data asynchronously
        tsChart3.loadChartData();






        return view;
    }

}