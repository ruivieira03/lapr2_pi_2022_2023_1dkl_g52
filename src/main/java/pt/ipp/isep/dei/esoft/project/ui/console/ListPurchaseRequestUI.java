package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ListPurchaseRequestController;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PurchaseRequestDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.*;

/**
 * The type List purchase request ui.
 */
public class ListPurchaseRequestUI implements Runnable {
    public void run() {
        ListPurchaseRequestController controller = new ListPurchaseRequestController();


        String prompt = "Select a property to see the requests:";
        ErrorOptional<List<PropertySaleDTO>> propertySaleList = controller.getPropertySaleList();
        if (propertySaleList.hasError()) {
            System.out.println(propertySaleList.getErrorMessage() + "\nOperation Failed!");
            return;
        }

        int input;

        do {
            input = showPropertySaleAndSelectIndex(propertySaleList.get(), prompt);
            if (input == -1)
                return;

        } while (input < 0 || input >= propertySaleList.get().size());

        PropertySaleDTO selectedProperty = propertySaleList.get().get(input);


        listPurchaseRequest(selectedProperty);
    }

    /**
     * Method to list Purchase Request.
     *
     * @param selectedProperty
     */
    private void listPurchaseRequest(PropertySaleDTO selectedProperty) {
        ListPurchaseRequestController controller = new ListPurchaseRequestController();


        String prompt = "Select a request:";
        ErrorOptional<List<PurchaseRequestDTO>> purchaseRequestList = controller.getPurchaseRequestList(selectedProperty);
        if (purchaseRequestList.hasError()) {
            System.out.println(purchaseRequestList.getErrorMessage() + "\nOperation Failed!");
            return;
        }

        int input;
        do {
            input = showPurchaseRequestsListAndSelectIndex(purchaseRequestList.get(), prompt);

            if (input == -1)
                return;

        } while (input < 0 || input >= purchaseRequestList.get().size());

        PurchaseRequestDTO selectedPurchaseRequest = purchaseRequestList.get().get(input);

        acceptOrDecline(selectedPurchaseRequest);
    }

    /**
     * Method to accept or decline.
     *
     * @param selectedPurchaseRequest
     */


    private void acceptOrDecline(PurchaseRequestDTO selectedPurchaseRequest) {
        ListPurchaseRequestController controller = new ListPurchaseRequestController();

        String prompt = "Selected Request";
        List<PurchaseRequestDTO> list = new ArrayList<>();
        list.add(selectedPurchaseRequest);

        showPurchaseRequestsList(list, prompt);

        prompt = "Do you accept this request? (Y/N)";
        boolean accepted = confirm(prompt);


        MessageDTO message = new MessageDTO();
        if (accepted) {
            prompt = "Accepted - Write a message to the client!";
            message.message = readLineFromConsole(prompt);

            OperationResult operationResult = controller.acceptRequest(selectedPurchaseRequest, message);
            if (operationResult.success()) System.out.println("Operation Successful!");
            else System.out.println(operationResult.getErrorMessage() + "\nError: Operation Failed!");

        } else {
            prompt = "Declined - Write a message to the client!";
            message.message = readLineFromConsole(prompt);

            OperationResult operationResult = controller.declineRequest(selectedPurchaseRequest, message);
            if (operationResult.success()) System.out.println("Operation Successful!");
            else System.out.println(operationResult.getErrorMessage() + "\nError: Operation Failed!");
        }

    }
}