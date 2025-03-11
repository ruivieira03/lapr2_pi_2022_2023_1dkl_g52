package pt.ipp.isep.dei.esoft.project.util.regression;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class MultiLinearRegression {
    private final OLSMultipleLinearRegression regression;


    public MultiLinearRegression() {
        this.regression = new OLSMultipleLinearRegression();
    }

    public void loadData(double[][] dependentData, double[] independentData) {
        try {
            regression.newSampleData(independentData, dependentData);
        } catch (Exception ignore) {
            return;
        }
    }

    public double getR2() {
        return regression.calculateRSquared();
    }

    public long getNumberOfPoints() {
        return DataCollector.dependedData().length;
    }

    public double getR() {
        return Math.pow(regression.calculateRSquared(), 0.5);
    }

    public double errorVariance() {
        return regression.estimateErrorVariance();
    }

    public boolean isDataLoaded() {
        try {
            regression.calculateHat();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}