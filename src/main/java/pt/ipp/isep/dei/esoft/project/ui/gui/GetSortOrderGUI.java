package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainGUI;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuInterface;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetSortOrderGUI implements Initializable, MenuInterface {

    MainGUI mainApp;
    public static int choice;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Button AreaAscending;

    @FXML
    private Button AreaDescending;

    @FXML
    private Button backButton;

    @FXML
    void SelectAscending(MouseEvent event) {
        setChoice(1);
        try {
            DisplaySortedPropertiesSoldGUI displaySortedPropertiesSoldGUI = (DisplaySortedPropertiesSoldGUI)  mainApp.replaceSceneContent("/fxml/displaySortedPropertiesSoldGUI.fxml");
            displaySortedPropertiesSoldGUI.setMainApp(mainApp);
        }catch (Exception ex){
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    void SelectDescending(MouseEvent event) {
        setChoice(2);
        try {
            DisplaySortedPropertiesSoldGUI displaySortedPropertiesSoldGUI = (DisplaySortedPropertiesSoldGUI)  mainApp.replaceSceneContent("/fxml/displaySortedPropertiesSoldGUI.fxml");
            displaySortedPropertiesSoldGUI.setMainApp(mainApp);
        }catch (Exception ex){
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    void goBack(MouseEvent event) {
        try {
            GetSortAlgorithmGUI getSortAlgorithmGUI = (GetSortAlgorithmGUI)  mainApp.replaceSceneContent("/fxml/getSortAlgorithmGUI.fxml");
            getSortAlgorithmGUI.setMainApp(mainApp);
        }catch (Exception ex){
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
        }

    }

    @Override
    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }

    private void setChoice(int i){choice = i;}
}


