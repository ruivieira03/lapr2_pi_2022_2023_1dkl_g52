package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import pt.ipp.isep.dei.esoft.project.application.controller.ReviewVisitRequestsController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReviewVisitRequestsGUI implements Initializable {

    private MainGUI mainApp;

    @FXML
    private ListView<String> ListView;

    String[] visitRequests = {"pizza"};

    List<VisitRequestDTO> visitRequestDTOList;
    VisitRequestDTO currentVR;

    @FXML
    private Label myLabel;

    @FXML
    private Button acceptButton;

    @FXML
    private Button declineButton;
    @FXML
    private MenuItem agentMenu;
    @FXML
    private MenuItem logoutButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReviewVisitRequestsController controller = new ReviewVisitRequestsController();
        visitRequestDTOList = controller.getPropertySaleRequestListFromAgent().get();
        List<String> list = new ArrayList<>();
        for(VisitRequestDTO visitRequestDTO : controller.getPropertySaleRequestListFromAgent().get()){
            list.add(visitRequestDTO.toString());
        }
        ListView.getItems().addAll(list);
        acceptButton.setDisable(true);
        declineButton.setDisable(true);
        ListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int currentVRIndex = ListView.getSelectionModel().getSelectedIndex();
                currentVR = visitRequestDTOList.get(currentVRIndex);
                controller.setSelectedVisitRequest(currentVR);
                acceptButton.setDisable(false);
                declineButton.setDisable(false);
            }
        });
    }

    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    void acceptVisitRequest(ActionEvent event) {
        try {
            AcceptVisitRequestGUI acceptVisitRequestGUI = (AcceptVisitRequestGUI) mainApp.replaceSceneContent("/fxml/acceptVisitRequestGUI.fxml");
            acceptVisitRequestGUI.setMainApp(mainApp);
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void declineVisitRequest(ActionEvent event) {
        try {
            DeclineVisitRequestGUI declineVisitRequestGUI = (DeclineVisitRequestGUI) mainApp.replaceSceneContent("/fxml/declineVisitRequestGUI.fxml");
            declineVisitRequestGUI.setMainApp(mainApp);
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void sortList(ActionEvent event) {
        ReviewVisitRequestsController ctrl = new ReviewVisitRequestsController();
        ctrl.sortList(visitRequestDTOList);
        ListView.getItems().clear();
        ListView.refresh();

        List<String> list = new ArrayList<>();
        for (VisitRequestDTO visitRequestDTO : visitRequestDTOList) {
            list.add(visitRequestDTO.toString());
        }
        ListView.getItems().addAll(list);
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
    void back(ActionEvent event) {
        try {
            AgentGUI agentGUI = (AgentGUI) mainApp.replaceSceneContent("/fxml/agentGUI.fxml");
            agentGUI.setMainApp(mainApp);
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
