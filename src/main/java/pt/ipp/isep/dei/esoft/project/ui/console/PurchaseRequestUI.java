package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ListPropertiesController;
import pt.ipp.isep.dei.esoft.project.application.controller.PurchaseRequestController;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PurchaseRequestDTO;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.readDoubleFromConsole;

/**
 * The type Purchase request ui.
 */
public class PurchaseRequestUI implements Runnable {
    public void run() {

        PurchaseRequestController purchaseController = new PurchaseRequestController();
        ListPropertiesController listController = new ListPropertiesController();

        String prompt;
        ClientDTO client = new ClientDTO();
        client.email = null;

         client = purchaseController.getClientFromUserSession().get();


        PurchaseRequestDTO dto = new PurchaseRequestDTO();

        dto.propertySale = listController.getSelectedProperty().get();

            dto.client = client;


            do {
                prompt = "orderAmount:";
                dto.orderAmount = (float) readDoubleFromConsole(prompt);
            } while (dto.orderAmount == 0);

            OperationResult operationResult = purchaseController.createPurchaseRequest(dto);
            if (operationResult.success())
                System.out.println("Operation Successful!");

            else System.out.println(operationResult.getErrorMessage() + "\n Error: Operation Failed!");


    }
}
