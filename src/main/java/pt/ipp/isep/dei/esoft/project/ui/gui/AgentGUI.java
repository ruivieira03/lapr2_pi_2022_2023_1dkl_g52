package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.fxml.Initializable;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.console.ListPropertySaleRequestsUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ListPurchaseRequestUI;
import pt.ipp.isep.dei.esoft.project.ui.console.RegisterPropertySaleUI;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.PropertySaleListMenu;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgentGUI implements Initializable, MenuInterface {

    MainGUI mainApp;
    private PropertySaleListMenu propertySaleListMenu;
    private RegisterPropertySaleUI registerPropertySaleUI;
    private ListPropertySaleRequestsUI listPropertySaleRequestsUI;
    private ListPurchaseRequestUI listPurchaseRequestUI;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        propertySaleListMenu = new PropertySaleListMenu();
        registerPropertySaleUI = new RegisterPropertySaleUI();
        listPropertySaleRequestsUI = new ListPropertySaleRequestsUI();
        listPurchaseRequestUI = new ListPurchaseRequestUI();
    }

    @FXML
    private Button listPropertiesButton;

    @FXML
    private MenuItem logoutButton;

    @FXML
    private Button listVisitButton;

    @FXML
    private Button managePropertySaleRequestsButton;

    @FXML
    private Button managePurchaseRequestsButton;

    @FXML
    private Button manageVisitRequestsButton;

    @FXML
    private Button registerPropertySaleButton;

    @FXML
    void listProperties(ActionEvent event) throws Exception {
        propertySaleListMenu.run();
    }

    @FXML
    void logout(ActionEvent event) {
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
    void managePropertySaleRequests(ActionEvent event) {
        listPropertySaleRequestsUI.run();
    }

    @FXML
    void managePurchaseRequestsButton(ActionEvent event) {
        listPurchaseRequestUI.run();
    }

    @FXML
    void manageVisitRequests(ActionEvent event) throws Exception {
        try {
            ReviewVisitRequestsGUI reviewVisitRequestsGUI = (ReviewVisitRequestsGUI) mainApp.replaceSceneContent("/fxml/reviewVisitRequests.fxml");
            reviewVisitRequestsGUI.setMainApp(mainApp);
        }catch (Exception ex){
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    void listVisitRequests(ActionEvent event) throws Exception{
        try {
            ListVisitRequestsGUI listVisitRequestsGUI = (ListVisitRequestsGUI) mainApp.replaceSceneContent("/fxml/listVisitRequests.fxml");
            listVisitRequestsGUI.setMainApp(mainApp);
        }catch (Exception ex){
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
        }
    }


    @FXML
    void registerPropertySale(ActionEvent event) {
        registerPropertySaleUI.run();
    }

    @Override
    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }
}
