package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.console.CreatePropertySaleRequestUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ReadAppoitementRequestUI;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuInterface;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.NotificationsMenu;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.PropertySaleListMenu;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientGUI implements Initializable, MenuInterface {
    MainGUI mainApp;
    private PropertySaleListMenu propertySaleListMenu;
    private CreatePropertySaleRequestUI createPropertySaleRequestUI;
    private NotificationsMenu notificationsMenu;
    private ReadAppoitementRequestUI readAppoitementRequestUI;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        propertySaleListMenu = new PropertySaleListMenu();
        createPropertySaleRequestUI = new CreatePropertySaleRequestUI();
        notificationsMenu = new NotificationsMenu();
        readAppoitementRequestUI = new ReadAppoitementRequestUI();
    }

    @Override
    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private MenuItem logout;
    @FXML
    private Button createPorpertySaleRequest;

    @FXML
    private Button listProperties;

    @FXML
    private Button notifications;

    @FXML
    void createPropertySaleRequest(ActionEvent event) {
        createPropertySaleRequestUI.run();
    }

    @FXML
    void listProperties(ActionEvent event) {
        propertySaleListMenu.run();
    }

    @FXML
    void notifications(ActionEvent event) {
        notificationsMenu.run();
    }

    @FXML
    void readAppointementResponse(ActionEvent event) {
        readAppoitementRequestUI.run();
    }

    @FXML
    void logout(ActionEvent event) throws Exception {
        AuthenticationController controller = new AuthenticationController();
        try {
            controller.doLogout();
            MainMenuGUI mainMenuGUI = (MainMenuGUI) mainApp.replaceSceneContent("/fxml/mainMenuGUI.fxml");
            mainMenuGUI.setMainApp(mainApp);
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
