package com.baidingapp.faceapp.helper;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class StatHelper {

    // Get the BarEntry for plotting the Bar Chart in InputRateFace
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


    // Get the BarEntry for plotting the Bar Chart in OutputRateFace
    public static List<BarEntry> getAllBarEntry(int[][] allRateScores) {
        int numRows = allRateScores.length;
        int numCols = allRateScores[0].length;
        float[] scoreMean = new float[numCols];

        for (int j=0; j<numCols; j++) {
            scoreMean[j] = 0;
            int k = 0;
            for (int i=0; i<numRows; i++) {
                // Ger rid of the null values
                if ((allRateScores[i][j] > 0) && (allRateScores[i][j] < 11)) {
                    scoreMean[j] = scoreMean[j] + allRateScores[i][j];
                    k++;
                }
            }
            scoreMean[j] = scoreMean[j]/k;
        }

        // Construct data for plotting BarChart
        List<BarEntry> allBarEntries = new ArrayList<>();
        for (int i=1; i<=numCols; i++) {
            allBarEntries.add(new BarEntry(i, scoreMean[i-1]));
        }

        return allBarEntries;
    }

}
