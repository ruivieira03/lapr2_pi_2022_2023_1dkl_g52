package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainGUI;
import pt.ipp.isep.dei.esoft.project.repository.PropertySoldRepository;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuInterface;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetSortAlgorithmGUI implements Initializable, MenuInterface {
    MainGUI mainApp;
    public static int choice;
    PropertySoldRepository.AlgorithmOptions algorithmOptions;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private Button BubbleSortButton;

    @FXML
    private Button InsertionSortButton;

    @FXML
    private Button backButton;

    @FXML
    void goBack(MouseEvent event) {
        try {
            networkManagerGUI networkManagerGUI = (networkManagerGUI)  mainApp.replaceSceneContent("/fxml/networkManagerGUI.fxml");
            networkManagerGUI.setMainApp(mainApp);
        }catch (Exception ex){
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    void setBubbleSort(MouseEvent event) {
        setChoice(1);
        try {
            GetSortOrderGUI getSortOrderGUI= (GetSortOrderGUI)  mainApp.replaceSceneContent("/fxml/getSortOrder.fxml");
            getSortOrderGUI.setMainApp(mainApp);
        }catch (Exception ex){
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    void setInsertioSort(MouseEvent event) {
        setChoice(2);
        try {
            GetSortOrderGUI getSortOrderGUI= (GetSortOrderGUI)  mainApp.replaceSceneContent("/fxml/getSortOrder.fxml");
            getSortOrderGUI.setMainApp(mainApp);
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
