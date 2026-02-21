//Chart rendering uses MPAndroidChart
//Library: MPAndroidChart by Phi Jay
//License: Apache 2.0
//Source: https://github.com/PhilJay/MPAndroidChart

/*
* Part of planned enhancement: Category 1 - Software Design
* Christopher DeMello
* Professor Conlan
* CS499
* Created: 1/24/26
*/

package com.CS360.weighttracker.util;

import com.CS360.weighttracker.model.WeightEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Logic for chart setup. Keep out of dashboard activity for clean and reusable code
public class ChartUtils {
    private ChartUtils() {}


    //This function converts database weight entries to be used in the chart
    //Sets weights as y-axis data
    //Extracts and separates the x-axis dates
    //Returns a data points object to render chart
    public static LineData buildWeightLineData(List<WeightEntry> weights,
                                               List<String> xLabelsOut) {
        //prevents null pointer exception and chart render safely if no data
        if (weights == null) {
            weights = Collections.emptyList();
        }
        //Entry represents one point on the line chart.
        List<Entry> entries = new ArrayList<>();
        //Clears stale labels from previous chart render
        xLabelsOut.clear();

        //loops through weight entries and adds x and y labels to them
        for (int i = 0; i < weights.size(); i++) {
            //get weight entry that contains date and weight
            WeightEntry weight = weights.get(i);
            //at position i, plot this weight
            entries.add(new Entry(i, (float) weight.getWeight()));
            //get date associated with weight entry
            xLabelsOut.add(weight.getDate());
        }
        //Adds one line on the chart and a legend for Weight
        LineDataSet dataSet = new LineDataSet(entries, "Weight");
        //prevents clutter over every dot
        dataSet.setDrawValues(false);
        //Shows each individual entry
        dataSet.setDrawCircles(true);
        //Makes line thicker than default
        dataSet.setLineWidth(2f);

        return new LineData(dataSet);
    }
    //Configures the x-axis of the chart
    public static void styleXAxisAsDates(XAxis xAxis, List<String> labels) {
        //Sets to x-axis to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //ensures each label maps to one entry
        xAxis.setGranularity(1f);
        //prevents overlap of dates
        xAxis.setLabelRotationAngle(-45f);

        //Converts axis to display date strings
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            //assigns value of x-axis to an integer
            public String getFormattedValue(float value) {
                int i = (int) value;
                //prevents edge cases
                if (i < 0 || i >= labels.size()) {
                    return "";
                }
                //returns numeric x-value into date string
                return labels.get(i);
            }
        });
    }
}
