package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import pt.ipp.isep.dei.esoft.project.application.controller.RegressionController;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuInterface;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LinearRegressionGUI implements Initializable, MenuInterface {

    MainGUI mainApp;

    RegressionController controller = new RegressionController();
    @FXML
    private Button areaButton;

    @FXML
    private Button backButton;

    @FXML
    private Button bathroomsButton;

    @FXML
    private Button bedroomButton;

    @FXML
    private Button distanceButton;

    @FXML
    private Button parkingButton;

    @FXML
    private TextArea textResult;

    @FXML
    void areaPress(MouseEvent event) {
        textResult.clear();
        controller.areaRegression();
        textResult.setText("====Area Regression====\n");
        getResult();
        disableButton(areaButton);
    }

    @FXML
    void backPress(MouseEvent event) {
        try {
            ChooseRegressionGUI regressionGUI = (ChooseRegressionGUI) mainApp.replaceSceneContent("/fxml/chooseRegression.fxml");
            regressionGUI.setMainApp(mainApp);
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void bathroomPress(MouseEvent event) {
        textResult.clear();
        controller.bathroomRegression();
        textResult.setText("====Amount of bathrooms Regression====\n");
        getResult();
        disableButton(bathroomsButton);

    }

    @FXML
    void bedroomsPress(MouseEvent event) {
        textResult.clear();
        controller.bedroomRegression();
        textResult.setText("====Amount of Bedrooms Regression====\n");
        getResult();
        disableButton(bedroomButton);

    }

    @FXML
    void distancePress(MouseEvent event) {
        textResult.clear();
        controller.distanceRegression();
        textResult.setText("====Distance from city center Regression====\n");
        getResult();
        disableButton(distanceButton);
    }

    @FXML
    void parkingPress(MouseEvent event) {
        textResult.clear();
        controller.parkingRegression();
        textResult.setText("====Amount of parking spaces Regression====\n");
        getResult();
        disableButton(parkingButton);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }

    public void getResult() {
        String text = textResult.getText();

        text += "\nEquation :" + controller.getEquation();

        text += "\nR^2 : " + controller.getR2();

        text += "\nR : " + controller.getR();

        text += "\nIntercept std Error : " + controller.getInterSTDError();

        text += "\nSlope std Error : " + controller.getSloptSTDError();

        text += "\nMean Square Error : " + controller.getMeanSquareError();

        text += "\nSlope confidence Interval : " + controller.getConfideceInterval();

        text += "\nNumber of points(N) : " + controller.getNumberOfPoints();

        textResult.setText(text);
    }

    public void disableButton(Button button) {
        areaButton.setDisable(false);
        distanceButton.setDisable(false);
        bedroomButton.setDisable(false);
        bathroomsButton.setDisable(false);
        parkingButton.setDisable(false);

        button.setDisable(true);
    }
}