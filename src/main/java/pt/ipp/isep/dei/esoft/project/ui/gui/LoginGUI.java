package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.*;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuGUIItem;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuInterface;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.net.URL;
import java.util.*;

public class LoginGUI implements  Initializable {

    private MainGUI mainApp;
    @FXML
    private TextField emailField;

    @FXML
    private Label invalidLogin;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    private AuthenticationController controller;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new AuthenticationController();
        invalidLogin.setVisible(false);
    }

    public boolean doLogin(String id, String pwd) {
        boolean success = true;
        success = controller.doLogin(id, pwd);
        if (!success) {
            invalidLogin.setVisible(true);
            return false;
        }
        return true;
    }

    @FXML
    void doLogin(ActionEvent event) throws Exception {
        String id = emailField.getText();
        String pwd = passwordField.getText();

        boolean login = doLogin(id, pwd);
        if(!login){
            passwordField.clear();
        } else {
                List<UserRoleDTO> roles = this.controller.getUserRoles();
                if ((roles == null) || (roles.isEmpty())) {
                    System.out.println("No role assigned to user.");
                } else {
                    UserRoleDTO role = selectsRole(roles);
                    if (!Objects.isNull(role)) {
                        List<MenuGUIItem> rolesUI = getMenuGUIItemForRoles();
                        this.redirectToRoleGUI(rolesUI, role);
                    } else {
                        System.out.println("No role selected.");

                    }
                }
        }
    }

    public MainGUI getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }

    private List<MenuGUIItem> getMenuGUIItemForRoles() {
        List<MenuGUIItem> rolesGUI = new ArrayList<>();

        rolesGUI.add(new MenuGUIItem(AuthenticationController.Roles.ADMINISTRATOR.name(), "/fxml/adminGUI.fxml" ));
        rolesGUI.add(new MenuGUIItem(AuthenticationController.Roles.AGENT.name(), "/fxml/agentGUI.fxml" ));
        rolesGUI.add(new MenuGUIItem(AuthenticationController.Roles.CLIENT.name(), "/fxml/clientGUI.fxml"));
        rolesGUI.add(new MenuGUIItem(AuthenticationController.Roles.NETWORK_MANAGER.name(),"/fxml/networkManagerGUI.fxml"));
        //TODO: Complete with other user roles and related RoleUI

        return rolesGUI;
    }

    private void logout() {
        controller.doLogout();
    }

    private void redirectToRoleGUI(List<MenuGUIItem> rolesUI, UserRoleDTO role) throws Exception {
        for(MenuGUIItem item : rolesUI){
            if(item.hasRole(role.getDescription())){
                    Initializable GUI = mainApp.replaceSceneContent(item.getFxml());
                ((MenuInterface)GUI).setMainApp(mainApp);

            }
        }
    }

    private UserRoleDTO selectsRole(List<UserRoleDTO> roles) {
        if (roles.size() == 1) {
            return roles.get(0);
        } else {
            return (UserRoleDTO) Utils.showAndSelectOne(roles, "Select the role you want to adopt in this session:");
        }
    }
}
