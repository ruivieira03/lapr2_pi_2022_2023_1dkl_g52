package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegressionController;

import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.showAndSelectIndex;

public class RegressionUI implements Runnable {

    @Override
    public void run() {
        List<String> options = new ArrayList<>();
        options.add("Linear Regression");
        options.add("Multilinear Regression");

        String prompt = "Choose an option:";

        int input = 0;
        do {
            input = showAndSelectIndex(options, prompt);

        } while (input < -1 || input >= options.size());

        if (input == 0) linearRegression();

        if (input == 1) multilinearRegression();
    }

    private void multilinearRegression() {
        RegressionController controller = new RegressionController();

        controller.multiLinearRegression();

        String text = "=====Multi Linear Regression Study======";

        text += "\nR^2 : " + controller.getR2();

        text += "\nR : " + controller.getR();

        text += "\nError Variance : " + controller.getErrorVariance();

        text += "\nNumber of points(N) : " + controller.getNumberOfPoints();

        System.out.println(text);;
    }

    private void linearRegression() {
        List<String> options = new ArrayList<>();

        options.add("Area");
        options.add("Distance from City center");
        options.add("Amount of Bedrooms");
        options.add("Amount of Bathrooms");
        options.add("Amount of parking spaces");

        String prompt = "Choose a independent variable to be studied:";

        int input = 0;
        do {
            input = showAndSelectIndex(options, prompt);
        } while (input < -1 || input >= options.size());


        if (input == 0) areaRegression();
        else if (input == 1) distanceRegression();
        else if (input == 2) bedroomsRegression();
        else if (input == 3) bathroomsRegression();
        else if (input == 4) parkingRegression();
    }

    private void parkingRegression() {
        RegressionController controller = new RegressionController();

        controller.parkingRegression();

        System.out.println("Amount of parking spaces Linear Regression: \n");

        System.out.println("Equation :" + controller.getEquation());

        System.out.println("R^2 : " + controller.getR2());

        System.out.println("R : " + controller.getR());

        System.out.println("Intercept std Error : " + controller.getInterSTDError());

        System.out.println("Slope std Error : " + controller.getSloptSTDError());

        System.out.println("Mean Square Error : " + controller.getMeanSquareError());

        System.out.println("Slope confidence Interval : " + controller.getConfideceInterval());

        System.out.println("Number of points(N) : " + controller.getNumberOfPoints());
    }

    private void bathroomsRegression() {
        RegressionController controller = new RegressionController();

        controller.bathroomRegression();

        System.out.println("Amount of bathrooms Linear Regression: \n");

        System.out.println("Equation :" + controller.getEquation());

        System.out.println("R^2 : " + controller.getR2());

        System.out.println("R : " + controller.getR());

        System.out.println("Intercept std Error : " + controller.getInterSTDError());

        System.out.println("Slope std Error : " + controller.getSloptSTDError());

        System.out.println("Mean Square Error : " + controller.getMeanSquareError());

        System.out.println("Slope confidence Interval : " + controller.getConfideceInterval());

        System.out.println("Number of points(N) : " + controller.getNumberOfPoints());


    }

    private void bedroomsRegression() {
        RegressionController controller = new RegressionController();

        controller.bedroomRegression();

        System.out.println("Amount of parking spaces Linear Regression: \n");

        System.out.println("Equation :" + controller.getEquation());

        System.out.println("R^2 : " + controller.getR2());

        System.out.println("R : " + controller.getR());

        System.out.println("Intercept std Error : " + controller.getInterSTDError());

        System.out.println("Slope std Error : " + controller.getSloptSTDError());

        System.out.println("Mean Square Error : " + controller.getMeanSquareError());

        System.out.println("Slope confidence Interval : " + controller.getConfideceInterval());

        System.out.println("Number of points(N) : " + controller.getNumberOfPoints());


    }

    private void distanceRegression() {
        RegressionController controller = new RegressionController();

        controller.distanceRegression();

        System.out.println("Equation :" + controller.getEquation());

        System.out.println("R^2 : " + controller.getR2());

        System.out.println("R : " + controller.getR());

        System.out.println("Intercept std Error : " + controller.getInterSTDError());

        System.out.println("Slope std Error : " + controller.getSloptSTDError());

        System.out.println("Mean Square Error : " + controller.getMeanSquareError());

        System.out.println("Slope confidence Interval : " + controller.getConfideceInterval());

        System.out.println("Number of points(N) : " + controller.getNumberOfPoints());


    }

    private void areaRegression() {
        RegressionController controller = new RegressionController();

        controller.areaRegression();

        System.out.println("Area Linear Regression: \n");

        System.out.println("Equation :" + controller.getEquation());

        System.out.println("R^2 : " + controller.getR2());

        System.out.println("R : " + controller.getR());

        System.out.println("Intercept std Error : " + controller.getInterSTDError());

        System.out.println("Slope std Error : " + controller.getSloptSTDError());

        System.out.println("Mean Square Error : " + controller.getMeanSquareError());

        System.out.println("Slope confidence Interval : " + controller.getConfideceInterval());

        System.out.println("Number of points(N) : " + controller.getNumberOfPoints());

    }
}
