package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import pt.ipp.isep.dei.esoft.project.ui.console.RegistrationUI;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.PropertySaleListMenu;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuGUI implements Initializable {

    private MainGUI mainApp;

    private RegistrationUI registrationUI;

    private PropertySaleListMenu propertySaleListMenu;

    @FXML
    private Button loginButton1;

    @FXML
    private Button propertySaleslistButton;

    @FXML
    private Button registerButton;

    @FXML
    void loginButton(ActionEvent event) {
        try {
            LoginGUI loginGUI = (LoginGUI) mainApp.replaceSceneContent("/fxml/login.fxml");
            loginGUI.setMainApp(mainApp);
        } catch (Exception ex){
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    void propertySalesListButton(ActionEvent event) {
    propertySaleListMenu.run();
    }

    @FXML
    void registerButton(ActionEvent event) {
        registrationUI.run();
    }




    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registrationUI = new RegistrationUI();
        propertySaleListMenu = new PropertySaleListMenu();
    }


}


