package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.AdminMenu;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuInterface;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminGUI implements Initializable,MenuInterface {
    MainGUI mainApp;

    private AdminMenu adminMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminMenu = new AdminMenu();
    }

    @FXML
    private Button back;
    @FXML
    private Button RunAdmin;
    @FXML
    void goBack(MouseEvent event) {
        AuthenticationController controller = new AuthenticationController();
        try {
            controller.doLogout();
            MainMenuGUI mainMenuGUI = (MainMenuGUI) mainApp.replaceSceneContent("/fxml/mainMenuGUI.fxml");
            mainMenuGUI.setMainApp(mainApp);
        }catch (Exception ex){
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
        }
    }


    @FXML
    void runADMIN(MouseEvent event) {
        adminMenu.run();
    }

    @Override
    public void setMainApp(MainGUI mainApp){this.mainApp = mainApp;}
}
