package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.util.regression.DataCollector;
import pt.ipp.isep.dei.esoft.project.util.regression.MultiLinearRegression;
import pt.ipp.isep.dei.esoft.project.util.regression.SimpleRegression;

public class RegressionController {

    private SimpleRegression simpleRegression;
    private MultiLinearRegression multiLinearRegression;

    public RegressionController() {
    }

    public void multiLinearRegression() {
        multiLinearRegression = new MultiLinearRegression();

        double[][] independentData = DataCollector.independentData();
        double[] dependentData = DataCollector.dependedData();

        multiLinearRegression.loadData(independentData, dependentData);
    }

    public void areaRegression() {
        simpleRegression = new SimpleRegression();
        simpleRegression.loadData(DataCollector.getAreaData());
    }

    public void parkingRegression() {
        simpleRegression = new SimpleRegression();
        simpleRegression.loadData(DataCollector.getNumberOfparkingSpaces());
    }

    public void bathroomRegression() {
        simpleRegression = new SimpleRegression();
        simpleRegression.loadData(DataCollector.getNumberOfBathroomsData());
    }

    public void bedroomRegression() {
        simpleRegression = new SimpleRegression();
        simpleRegression.loadData(DataCollector.getNumberOfBedroomsData());
    }

    public void distanceRegression() {
        simpleRegression = new SimpleRegression();
        simpleRegression.loadData(DataCollector.getDistancFromCityCenterData());
    }

    public double getR2() {
        if (simpleRegression != null)
            return simpleRegression.getR2();

        if (multiLinearRegression != null && multiLinearRegression.isDataLoaded())
            return multiLinearRegression.getR2();

        return 0;
    }

    public String getEquation() {
        if (simpleRegression != null)
            return simpleRegression.getEquation();

        return "";
    }

    public long getNumberOfPoints() {
        if (simpleRegression != null)
            return simpleRegression.getNumberOfPoints();

        if (multiLinearRegression != null && multiLinearRegression.isDataLoaded())
            return multiLinearRegression.getNumberOfPoints();

        return 0;
    }

    public double getR() {
        if (simpleRegression != null)
            return simpleRegression.getR();

        if (multiLinearRegression != null && multiLinearRegression.isDataLoaded())
            return multiLinearRegression.getR();

        return 0;
    }

    public double getInterSTDError() {
        if (simpleRegression != null)
            return simpleRegression.getInterSTDError();

        return 0;
    }

    public double getSloptSTDError() {
        if (simpleRegression != null)
            return simpleRegression.getSlopSTDError();

        return 0;
    }

    public double getMeanSquareError() {
        if (simpleRegression != null)
            return simpleRegression.getMeanSquareError();

        return 0;
    }

    public double getConfideceInterval() {
        if (simpleRegression != null)
            return simpleRegression.getConfidenceInterval();

        return 0;
    }

    public double getErrorVariance() {
        if (multiLinearRegression != null && multiLinearRegression.isDataLoaded())
            return multiLinearRegression.errorVariance();

        return 0;
    }
}
