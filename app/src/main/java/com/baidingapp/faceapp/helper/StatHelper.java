package com.baidingapp.faceapp.helper;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class StatHelper {

    // Get the BarEntry for plotting the Bar Chart
    public static List<BarEntry> getBarEntry(int[] allRateScores) {
        // Get the frequencies of all rate scores
        int allSize = allRateScores.length;
        float[] scoreFreq = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (int i=0; i<allSize; i++) {
            scoreFreq[allRateScores[i]-1]++;
        }
        for (int i=0; i<10; i++) {
            scoreFreq[i] = scoreFreq[i]/allSize;
        }

        // Construct data for plotting BarChart
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i=1; i<=10; i++) {
            barEntries.add(new BarEntry(i, scoreFreq[i-1]));
        }

        return barEntries;
    }

}
