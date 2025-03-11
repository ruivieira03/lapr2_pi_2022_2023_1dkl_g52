package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ReviewVisitRequestsController;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.VisitRequestResponseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.readLineFromConsole;

/**
 * The type Review visit requests ui.
 */
public class ReviewVisitRequestsUI implements Runnable {
    public void run() {
        ReviewVisitRequestsController controller = new ReviewVisitRequestsController();

        String prompt = "Select a request:";
        ErrorOptional<List<VisitRequestDTO>> propertySaleRequestList = controller.getPropertySaleRequestListFromAgent();
        if (propertySaleRequestList.hasError()) {
            System.out.println(propertySaleRequestList.getErrorMessage() + "\nOperation Failed!");
            return;
        }

        int input;
        do {
            input = Utils.showVisitRequestsListAndSelectIndex(propertySaleRequestList.get(), prompt);

            if (input >= 0 && input < propertySaleRequestList.get().size())
                acceptOrDeclineRequest(propertySaleRequestList.get().get(input));

        } while (input > -1);


    }

    private void acceptOrDeclineRequest(VisitRequestDTO visitRequestDTO) {

        String prompt = "Selected Request";
        List<VisitRequestDTO> list = new ArrayList<>();
        list.add(visitRequestDTO);

        Utils.showVisitRequestsList(list, prompt);

        prompt = "Do you accept the request? (Y/N)";

        boolean accepted = Utils.confirm(prompt);

        if (accepted)
            acceptRequest(visitRequestDTO);
        else
            declineRequest(visitRequestDTO);
    }

    private void acceptRequest(VisitRequestDTO visitRequestDTO) {
        ReviewVisitRequestsController controller = new ReviewVisitRequestsController();

        String prompt;

        ClientDTO clientDTO = visitRequestDTO.client;
        VisitRequestResponseDTO visitRequestResponseDTO = new VisitRequestResponseDTO();

        prompt = "Type the visit date:";
        LocalDate visitDate = Utils.readLocalDate(prompt);

        prompt = "Type the start of the Visit:";
        LocalTime visitStartTime = Utils.readLocalTime(prompt);

        prompt = "Type the end of the Visit:";
        LocalTime visitEndTime = Utils.readLocalTime(prompt);

        visitRequestDTO.visitStart = visitDate.atTime(visitStartTime);
        visitRequestDTO.visitEnd = visitDate.atTime(visitEndTime);

        OperationResult operationResult = controller.addAcceptedRequests(visitRequestDTO);

        if (operationResult.success())
            System.out.println("Operation Sucessfull");
        else
            System.out.println(operationResult.getErrorMessage() + "\nOperation Failed!");

        visitRequestResponseDTO.visitRequestDTO = visitRequestDTO;

        prompt = "Write a message to the owner:";
        visitRequestResponseDTO.message = readLineFromConsole(prompt);

        String subject = "Visit request accepted";

        operationResult = controller.sendEmailToClient(clientDTO,visitRequestResponseDTO,subject, visitRequestDTO);

        if (operationResult.success()) {
            controller.sendResponseToClient(clientDTO, visitRequestResponseDTO);
            System.out.println("Operation Sucessfull");
        }
       else
            System.out.println(operationResult.getErrorMessage() + "\nOperation Failed!");
    }

    private void declineRequest(VisitRequestDTO visitRequestDTO) {
        ReviewVisitRequestsController controller = new ReviewVisitRequestsController();

        ClientDTO clientDTO = visitRequestDTO.client;

        OperationResult operationResult = controller.addDeclinedVisitRequest(visitRequestDTO);


        if (operationResult.success())
            System.out.println("Operation Sucessfull");
        else
            System.out.println(operationResult.getErrorMessage() + "\nOperation Failed!");

        VisitRequestResponseDTO visitRequestResponseDTO = new VisitRequestResponseDTO();

        visitRequestResponseDTO.visitRequestDTO = visitRequestDTO;
        String prompt = "Write a message to the owner:";
        visitRequestResponseDTO.message = readLineFromConsole(prompt);

        String subject = "Visit request declined";

        operationResult = controller.sendEmailToClient(clientDTO,visitRequestResponseDTO,subject,visitRequestDTO);
        if (operationResult.success())
            System.out.println("Operation Sucessfull");
        else
            System.out.println(operationResult.getErrorMessage() + "\nOperation Failed!");
    }

}
