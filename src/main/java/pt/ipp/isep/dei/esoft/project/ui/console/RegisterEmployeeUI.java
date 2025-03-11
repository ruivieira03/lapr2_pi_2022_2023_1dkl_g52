package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterEmployeeController;
import pt.ipp.isep.dei.esoft.project.domain.employee.Employee;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.EmployeeDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.NetworkManagerDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.StoreManagerDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.*;

/**
 * The type Register employee ui.
 */
public class RegisterEmployeeUI implements Runnable {
    public void run() {
        RegisterEmployeeController controller = new RegisterEmployeeController();

        EmployeeDTO employeeDTO;
        StoreDTO store = null;
        Employee.Role employeeRole;

        String prompt, emailS, password;
        do {
            prompt = "Choose a employee type";
            employeeRole = (Employee.Role) Utils.showAndSelectOne(List.of(Employee.Role.values()), prompt);
        } while (employeeRole == null);

        if (employeeRole.equals(Employee.Role.AGENT)) employeeDTO = new AgentDTO();
        else if (employeeRole.equals(Employee.Role.NETWORK_MANAGER)) employeeDTO = new NetworkManagerDTO();
        else employeeDTO = new StoreManagerDTO();


        do {
            prompt = "Type the employee's name:";
            employeeDTO.name = readLineFromConsole(prompt);
        } while (employeeDTO.name == null);

        prompt = "Type employee's passport number:";
        employeeDTO.passportNumber = readIntegerFromConsole(prompt);

        prompt = "Type employee's tax number:";
        employeeDTO.taxNumber = readLineFromConsole(prompt);

        do {
            prompt = "Type the employee's adress:";
            employeeDTO.address = readLineFromConsole(prompt);
        } while (employeeDTO.address == null);

        do {
            prompt = "Type the employee's email adress:";
            emailS = readLineFromConsole(prompt);
        } while (emailS == null);

        employeeDTO.email = emailS;

        prompt = "Type the employee's phone number";
        employeeDTO.phoneNumber = readLineFromConsole(prompt);

        password = Employee.generatePassword();

        prompt = String.format("Confirm?(Y/N)\nname: %s | passport number: %d | tax number: %s | adress: %s | email: %s | phone number: %s | password: %s | employee type: %s",
                employeeDTO.name, employeeDTO.passportNumber, employeeDTO.taxNumber, employeeDTO.address, employeeDTO.email, employeeDTO.phoneNumber, password, employeeRole.name());


        if (confirm(prompt)) {
            OperationResult operationResult = controller.createEmployee(employeeDTO, password, store);

            if (operationResult.success()) System.out.println("Operation Succesfull!");
            else System.out.println(operationResult.getErrorMessage() + "\nError: Operation Failed!");
        }

    }

    private StoreDTO getStore() {
        RegisterEmployeeController controller = new RegisterEmployeeController();

        List<StoreDTO> storesList = controller.getStoreList();

        String prompt = "Choose a store to assign the employee to:";
        StoreDTO store;

        do {
            store = (StoreDTO) showAndSelectOne(storesList, prompt);
        } while (store == null);

        return store;

    }
}
