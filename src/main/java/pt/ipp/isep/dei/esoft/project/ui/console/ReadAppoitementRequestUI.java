package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ReadAppoitementRequestController;
import pt.ipp.isep.dei.esoft.project.application.controller.ReviewVisitRequestsController;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Message;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Notification;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.VisitRequestReponse;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.NotificationDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.VisitRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleRequestDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.repository.PropertySaleRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;
import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.readLineFromConsole;
import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.showClientNotificationsList;

public class ReadAppoitementRequestUI implements Runnable{
    VisitRequestDTO visitRequestDTOS;
    NotificationDTO notificationDTO;

    @Override
    public void run() {
        String prompt = "Select a message:";
        ReadAppoitementRequestController controller = new ReadAppoitementRequestController();
        List<Message> messageList = controller.getNotifications();

        int input;
        do {
            input = Utils.showClientNotificationsListAndSelectIndex(messageList, prompt);

            if (input >= 0 && input < messageList.size() )

            acceptOrDeclineResponse(notificationDTO);

        } while (input > -1);
    }

    private void acceptOrDeclineResponse(NotificationDTO notificationDTO) {

        ReadAppoitementRequestController controller = new ReadAppoitementRequestController();
        controller.getClientFromUserSession();
        VisitRequest visitRequest = controller.getVisitRequest();

        System.out.println("Agent name: " + visitRequest.getPropertySale().getAgent().getName());
        System.out.println("Agent phone number " + visitRequest.getPropertySale().getAgent().getPhoneNumber());
        System.out.println("Property Info: " + visitRequest.getPropertySale().toString());


        System.out.println("Appointment Start time: " + visitRequest.getVisitStart());
        System.out.println("Appointment End time: " + visitRequest.getVisitEnd());

        String prompt = "Do you accept the visit shedule? (Y/N)";

        boolean accepted = Utils.confirm(prompt);

        if (accepted)
            System.out.println("Your appointement is sheculed");
        else
            declineRequest(visitRequestDTOS);
    }

    private void declineRequest(VisitRequestDTO visitRequestDTO) {
        ReadAppoitementRequestController controller = new ReadAppoitementRequestController();

        OperationResult operationResult = controller.addDeclinedVisitRequest(visitRequestDTO);

        String declinningReason;

        if (operationResult.success()) {
            declinningReason = "Write the declinning reason";
            declinningReason = Utils.readLineFromConsole(declinningReason);
            System.out.println("Operation Sucessfull");
        }
        else
            System.out.println(operationResult.getErrorMessage() + "\nOperation Failed!");
    }


}
