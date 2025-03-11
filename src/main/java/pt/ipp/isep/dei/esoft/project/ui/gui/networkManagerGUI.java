package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.console.ListEmployeeUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ListPropertySoldUI;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuInterface;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.PropertySaleListMenu;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class networkManagerGUI implements Initializable, MenuInterface {

    MainGUI mainApp;
    private PropertySaleListMenu propertySaleListMenu;
    private ListEmployeeUI listEmployeeUI;
    private ListPropertySoldUI listPropertySoldUI;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listEmployeeUI = new ListEmployeeUI();
        propertySaleListMenu = new PropertySaleListMenu();

    }

    @FXML
    private Button EmployeeListButton;

    @FXML
    private Button PropertiesSoldListButton;

    @FXML
    private Button PropertySaleListButton;

    @FXML
    private Button SplitStoresListButton;

    @FXML
    private Button regressionStudyButton;

    @FXML
    void showEmployeeList(MouseEvent event) {
        listEmployeeUI.run();
    }

    @FXML
    void showPropertiesSoldList(MouseEvent event) {
        try {
            GetSortAlgorithmGUI getSortAlgorithmGUI = (GetSortAlgorithmGUI) mainApp.replaceSceneContent("/fxml/getSortAlgorithmGUI.fxml");
            getSortAlgorithmGUI.setMainApp(mainApp);
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void showPropertySaleList(MouseEvent event) {
        propertySaleListMenu.run();
    }

    @FXML
    void showSplitStoresList(MouseEvent event) {
        try {
            ChooseNumberOfStoresGUI chooseNumberOfStoresGUI = (ChooseNumberOfStoresGUI)  mainApp.replaceSceneContent("/fxml/chooseNumberOfStoresGUI.fxml");
            chooseNumberOfStoresGUI.setMainApp(mainApp);
        }catch (Exception ex){
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    private Button backButton;

    @FXML
    void goBack(MouseEvent event) {
        AuthenticationController controller = new AuthenticationController();
        try {
            controller.doLogout();
            MainMenuGUI mainMenuGUI = (MainMenuGUI) mainApp.replaceSceneContent("/fxml/mainMenuGUI.fxml");
            mainMenuGUI.setMainApp(mainApp);
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void regressionPresd(MouseEvent event) {
        try {
            ChooseRegressionGUI chooseRegressionGUI = (ChooseRegressionGUI) mainApp.replaceSceneContent("/fxml/chooseRegression.fxml");
            chooseRegressionGUI.setMainApp(mainApp);
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    @Override
    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }

}
