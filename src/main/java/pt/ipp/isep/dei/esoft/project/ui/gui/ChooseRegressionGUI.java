package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuInterface;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChooseRegressionGUI implements Initializable, MenuInterface {

    MainGUI mainApp;
    @FXML
    private Button backButton;

    @FXML
    private Button linearRegressionButton;

    @FXML
    private Button multiLinearRegressionButton;

    @FXML
    void backPress(MouseEvent event) {
        try {
            networkManagerGUI networkManagerGUI = (networkManagerGUI) mainApp.replaceSceneContent("/fxml/networkManagerGUI.fxml");
            networkManagerGUI.setMainApp(mainApp);
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void linearPress(MouseEvent event) {
        try {
            LinearRegressionGUI linearRegressionGUI = (LinearRegressionGUI) mainApp.replaceSceneContent("/fxml/LinearRegressionGUI.fxml");
            linearRegressionGUI.setMainApp(mainApp);
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void multiPress(MouseEvent event) {try {
        MultiLinearRegressionGUI multiLinearRegressionGUI = (MultiLinearRegressionGUI) mainApp.replaceSceneContent("/fxml/MultiLinearRegresssionGUI.fxml");
        multiLinearRegressionGUI.setMainApp(mainApp);
    } catch (Exception ex) {
        Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
    }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }
}
