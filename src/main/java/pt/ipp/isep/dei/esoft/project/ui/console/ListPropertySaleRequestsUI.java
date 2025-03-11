package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ListPropertySaleRequestsController;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleRequestDTO;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.*;

/**
 * The type List property sale requests ui.
 */
public class ListPropertySaleRequestsUI implements Runnable {

    public void run() {
        ListPropertySaleRequestsController controller = new ListPropertySaleRequestsController();

        String prompt = "Select a request:";
        ErrorOptional<List<PropertySaleRequestDTO>> propertySaleRequestList = controller.getPropertySaleRequestListFromAgent();
       if (propertySaleRequestList.hasError()) {
            System.out.println(propertySaleRequestList.getErrorMessage() + "\nOperation Failed!");
            return;
        }

        int input;
        do {
            input = Utils.showPropertySaleRequestAndSelectIndex(propertySaleRequestList.get(), prompt);

            if (input >= 0 && input < propertySaleRequestList.get().size())
                acceptOrDeclineRequest(propertySaleRequestList.get().get(input));

        } while (input > -1);


    }

    private void acceptOrDeclineRequest(PropertySaleRequestDTO propertySaleRequestDTO) {

        String prompt = "Selected Request";
        List<PropertySaleRequestDTO> list = new ArrayList<>();
        list.add(propertySaleRequestDTO);

        Utils.showPropertySaleRequestsList(list, prompt);

        prompt = "Do you accept the request? (Y/N)";

        boolean accepted = Utils.confirm(prompt);

        if (accepted)
            acceptRequest(propertySaleRequestDTO);
        else
            declineRequest(propertySaleRequestDTO);
    }

    private void acceptRequest(PropertySaleRequestDTO propertySaleRequestDTO) {
        ListPropertySaleRequestsController controller = new ListPropertySaleRequestsController();

        String prompt;
        PropertySale.CommissionType typeOfCommission;
        double commission = -1;

        do {

            prompt = "Select a type of commission:";
            typeOfCommission = (PropertySale.CommissionType) showAndSelectOne(List.of(PropertySale.CommissionType.values()), prompt);
        } while (typeOfCommission == null);

        do {
            prompt = "Type the commission (a number or a precentage beteewn 0-1)";
            if (typeOfCommission == PropertySale.CommissionType.FIXED) {
                commission = readDoubleFromConsole(prompt);
            }
            if (typeOfCommission == PropertySale.CommissionType.PERCENTAGE) {
                do {
                    commission = readDoubleFromConsole(prompt);
                } while (commission < 0 || commission > 1);
            }
        } while (commission < 0);

        OperationResult operationResult = controller.createPropertySale(propertySaleRequestDTO,typeOfCommission,commission);

        if (operationResult.success())
            System.out.println("Operation Sucessfull");
        else
            System.out.println(operationResult.getErrorMessage() + "\nOperation Failed!");


        MessageDTO messageDTO = new MessageDTO();
        prompt = "Write a message to the owner:";
        messageDTO.message = readLineFromConsole(prompt);

        operationResult = controller.sendMessageToClient(propertySaleRequestDTO.property.client, messageDTO);

        if (operationResult.success())
            System.out.println("Operation Sucessfull");
        else
            System.out.println(operationResult.getErrorMessage() + "\nOperation Failed!");

    }

    private void declineRequest(PropertySaleRequestDTO propertySaleRequestDTO) {
        ListPropertySaleRequestsController controller = new ListPropertySaleRequestsController();

        OperationResult operationResult = controller.removePropertySaleRequest(propertySaleRequestDTO);

        if (operationResult.success())
            System.out.println("Operation Sucessfull");
        else
            System.out.println(operationResult.getErrorMessage() + "\nOperation Failed!");

        MessageDTO messageDTO = new MessageDTO();
        String prompt = "Write a message to the owner:";
        messageDTO.message = readLineFromConsole(prompt);

        operationResult = controller.sendMessageToClient(propertySaleRequestDTO.property.client, messageDTO);
        if (operationResult.success())
            System.out.println("Operation Sucessfull");
        else
            System.out.println(operationResult.getErrorMessage() + "\nOperation Failed!");
    }

}
