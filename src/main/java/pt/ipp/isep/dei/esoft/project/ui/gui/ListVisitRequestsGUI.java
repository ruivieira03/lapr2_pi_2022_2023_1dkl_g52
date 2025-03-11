package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import pt.ipp.isep.dei.esoft.project.application.controller.ReviewVisitRequestsController;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListVisitRequestsGUI implements Initializable {

    private MainGUI mainApp;
    @FXML
    private Button backButton;
    @FXML
    private DatePicker beginDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ListView<String> listView;
    @FXML
    private Label myLabel;
    @FXML
    private Button searchButton;
    private ReviewVisitRequestsController ctrl;

    @FXML
    void getVisitRequests(ActionEvent event) {
        List<VisitRequestDTO> visitRequests = ctrl.getVisitRequests(beginDate.getValue(), endDate.getValue());
        List<String> list = new ArrayList<>();
        for(VisitRequestDTO visitRequestDTO : visitRequests){
            list.add(visitRequestDTO.toString());
        }
        listView.getItems().addAll(list);
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            AgentGUI agentGUI = (AgentGUI) mainApp.replaceSceneContent("/fxml/agentGUI.fxml");
            agentGUI.setMainApp(mainApp);
        } catch (Exception ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrl = new ReviewVisitRequestsController();

    }

    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }
}
