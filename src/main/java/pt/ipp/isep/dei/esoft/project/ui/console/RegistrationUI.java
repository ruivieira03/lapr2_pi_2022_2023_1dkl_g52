package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegistrationController;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.readIntegerFromConsole;
import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.readLineFromConsole;

/**
 * The type Registration ui.
 */
public class RegistrationUI implements Runnable {
    public void run() {
        Scanner sc = new Scanner(System.in);
        RegistrationController controller = new RegistrationController();

        String prompt, password, emailAddress;

        ClientDTO dto = new ClientDTO();


        do {
            prompt = "Name:";
            dto.name = readLineFromConsole(prompt);
        } while (dto.name == null);

        do {
            prompt = "PassportNumber";
            dto.passportNumber = readIntegerFromConsole(prompt);
        } while (dto.passportNumber == 0);

        do {
            prompt = "Tax number:";
            dto.taxNumber = readLineFromConsole(prompt);
        } while (dto.taxNumber == null);

        do {
            prompt = "Address(optional):";
            dto.address = readLineFromConsole(prompt);
        } while (dto.address == null);

        do {
            prompt = "Email address:";
            emailAddress = readLineFromConsole(prompt);
            dto.email = emailAddress;
        } while (dto.email  == null);

        do {
            prompt = "Telephone number:";
            dto.phoneNumber = readLineFromConsole(prompt);
        } while (dto.phoneNumber == null);

        do {
            prompt = "Password:";
            password = readLineFromConsole(prompt);
        } while (password == null);


        OperationResult operationResult = controller.createClient(dto, password);
        if (operationResult.success())
            System.out.println("Successfully registered");

        else System.out.println(operationResult.getErrorMessage() + "\nError: Operation Failed!");

    }
}
