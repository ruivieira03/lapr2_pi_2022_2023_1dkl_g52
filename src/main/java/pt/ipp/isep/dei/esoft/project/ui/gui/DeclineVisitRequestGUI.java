package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import pt.ipp.isep.dei.esoft.project.application.controller.ReviewVisitRequestsController;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.VisitRequestResponseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.util.dispatch.MessageBuilder;

import java.net.URL;
import java.util.ResourceBundle;

public class DeclineVisitRequestGUI implements Initializable {

    MainGUI mainApp;
    @FXML
    private Button declineButton;

    @FXML
    private TextArea message;

    ReviewVisitRequestsGUI reviewVisitRequestsGUI;

    ReviewVisitRequestsController controller;

    VisitRequestDTO visitRequestDTO;

    VisitRequestResponseDTO visitRequestResponseDTO = new VisitRequestResponseDTO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new ReviewVisitRequestsController();
        visitRequestDTO = controller.getSelectedVisitRequest();
    }

    @FXML
    void declineRequest(ActionEvent event) {
        String messageText = message.getText();

        if (messageText.isEmpty()) {
            exibirMensagemDeErro("Text area must be filled!");
            return;
        }

        try {

            controller.addDeclinedVisitRequest(visitRequestDTO);

            visitRequestResponseDTO.visitRequestDTO = visitRequestDTO;
            visitRequestResponseDTO.message = MessageBuilder.AcceptedVisitRequest(visitRequestDTO,messageText) ;

            controller.sendEmailToClient(visitRequestDTO.client,visitRequestResponseDTO,"Visit Request Declined",visitRequestDTO);
            controller.sendResponseToClient(visitRequestDTO.client,visitRequestResponseDTO);

            ReviewVisitRequestsGUI acceptVisitRequestGUI = (ReviewVisitRequestsGUI) mainApp.replaceSceneContent("/fxml/reviewVisitRequests.fxml");
            acceptVisitRequestGUI.setMainApp(mainApp);
        } catch (NumberFormatException e) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }

    private void exibirMensagemDeErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

}
