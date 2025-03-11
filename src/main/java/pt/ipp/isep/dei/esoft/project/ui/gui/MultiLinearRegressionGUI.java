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

public class MultiLinearRegressionGUI implements Initializable, MenuInterface {


    MainGUI mainApp;
    @FXML
    private Button backButton;

    @FXML
    private TextArea textResult;

    @FXML
    void backPress(MouseEvent event) {
        try {
            ChooseRegressionGUI regressionGUI = (ChooseRegressionGUI) mainApp.replaceSceneContent("/fxml/chooseRegression.fxml");
            regressionGUI.setMainApp(mainApp);
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RegressionController controller = new RegressionController();
        controller.multiLinearRegression();

        String text = "=====Multi Linear Regression Study======";

        text += "\nR^2 : " + controller.getR2();

        text += "\nR : " + controller.getR();

        text += "\nError Variance : " + controller.getErrorVariance();

        text += "\nNumber of points(N) : " + controller.getNumberOfPoints();

        textResult.setText(text);
    }

    @Override
    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }
}
