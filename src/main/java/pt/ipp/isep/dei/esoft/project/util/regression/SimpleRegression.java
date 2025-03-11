package pt.ipp.isep.dei.esoft.project.util.regression;

public class SimpleRegression {

    private final org.apache.commons.math3.stat.regression.SimpleRegression regression;


    public SimpleRegression() {
        this.regression = new org.apache.commons.math3.stat.regression.SimpleRegression();
    }

    public void loadData(double[][] data) {
        regression.clear();
        regression.addData(data);
    }

    public String getEquation() {
        String equation = "y = ";
        equation += regression.getSlope() + "x + ";
        equation += regression.getIntercept();

        return equation;
    }

    public long getNumberOfPoints() {
        return regression.getN();
    }

    public double getR2() {
        return regression.getRSquare();
    }

    public double getR() {
        return regression.getR();
    }

    public double getInterSTDError() {
        return regression.getInterceptStdErr();
    }

    public double getSlopSTDError() {
        return regression.getSlopeStdErr();
    }

    public double getMeanSquareError() {
        return regression.getMeanSquareError();
    }

    public double getConfidenceInterval() {
        return regression.getSlopeConfidenceInterval();
    }
}
