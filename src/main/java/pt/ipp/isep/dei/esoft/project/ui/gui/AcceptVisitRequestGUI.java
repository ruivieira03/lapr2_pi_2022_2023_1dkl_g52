package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipp.isep.dei.esoft.project.application.controller.ReviewVisitRequestsController;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.VisitRequestResponseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.util.dispatch.MessageBuilder;

public class AcceptVisitRequestGUI implements Initializable {
    MainGUI mainApp;
    @FXML
    private TextField StartMinute;

    @FXML
    private Button acceptButton;

    @FXML
    private TextField endHour;

    @FXML
    private TextField endMinute;

    @FXML
    private TextField message;

    @FXML
    private TextField startHour;

    @FXML
    private DatePicker visitDate;

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
    void acceptRequest(ActionEvent event) {
        LocalDate localDate = visitDate.getValue();
        String startHourText = startHour.getText();
        String startMinuteText = StartMinute.getText();
        String endHourText = endHour.getText();
        String endMinuteText = endMinute.getText();
        String messageText = MessageBuilder.AcceptedVisitRequest(visitRequestDTO,message.getText()) ;

        if (localDate == null || startHourText.isEmpty() || startMinuteText.isEmpty() ||
                endHourText.isEmpty() || endMinuteText.isEmpty() || messageText.isEmpty()) {
            exibirMensagemDeErro("All fields must be filled!");
            return;
        }

        LocalDate currentDate = LocalDate.now();
        if (localDate.isBefore(currentDate)) {
            exibirMensagemDeErro("The selected date has passed!");
            return;
        }

        try {
            int startHourValue = Integer.parseInt(startHourText);
            int startMinuteValue = Integer.parseInt(startMinuteText);
            int endHourValue = Integer.parseInt(endHourText);
            int endMinuteValue = Integer.parseInt(endMinuteText);

            // Verificar se as horas e os minutos estão dentro do intervalo correto
            if (startHourValue < 0 || startHourValue > 23 || startMinuteValue < 0 || startMinuteValue > 59 ||
                    endHourValue < 0 || endHourValue > 23 || endMinuteValue < 0 || endMinuteValue > 59) {
                exibirMensagemDeErro("The hours and minutes are incorrect!");
                return;
            }

            LocalTime currentTime = LocalTime.now();
            LocalTime visitStart = LocalTime.of(startHourValue, startMinuteValue);
            LocalTime visitEnd = LocalTime.of(endHourValue, endMinuteValue);

            // Verificar se o visitStart está no passado em relação ao momento atual
            if (localDate.equals(currentDate) && visitStart.isBefore(currentTime)) {
                exibirMensagemDeErro("The selected start time has passed!");
                return;
            }

            // Verificar se o visitEnd está no passado em relação ao visitStart
            LocalDateTime visitStartDateTime = localDate.atTime(visitStart);
            LocalDateTime visitEndDateTime = localDate.atTime(visitEnd);
            if (visitEndDateTime.isBefore(visitStartDateTime)) {
                exibirMensagemDeErro("The end time cannot be earlier than the start time!");
                return;
            }

            visitRequestDTO.visitStart = visitStartDateTime;
            visitRequestDTO.visitEnd = visitEndDateTime;

           controller.addAcceptedRequests(visitRequestDTO);

            visitRequestResponseDTO.visitRequestDTO = visitRequestDTO;
            visitRequestResponseDTO.message = messageText;

            controller.sendEmailToClient(visitRequestDTO.client,visitRequestResponseDTO,"Visit Request Accepted",visitRequestDTO);
            controller.sendResponseToClient(visitRequestDTO.client,visitRequestResponseDTO);

            ReviewVisitRequestsGUI acceptVisitRequestGUI = (ReviewVisitRequestsGUI) mainApp.replaceSceneContent("/fxml/reviewVisitRequests.fxml");
            acceptVisitRequestGUI.setMainApp(mainApp);
        } catch (NumberFormatException e) {
            exibirMensagemDeErro("The hours and minutes must be numbers.");
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
