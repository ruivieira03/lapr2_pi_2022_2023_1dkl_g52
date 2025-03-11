package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.EmployeeDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.StoreRepository;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.List;

/**
 * The type Register employee controller.
 */
public class RegisterEmployeeController {
    private final Repositories repositories;

    /**
     * Instantiates a new Register employee controller.
     */
    public RegisterEmployeeController() {
        this.repositories = Repositories.getInstance();
    }


    /**
     * Method to create a new Employee.
     *
     * @param employeeDTO the employee dto
     * @param password    the password
     * @param storeDTO    the store dto
     * @return operation result
     */
    public OperationResult createEmployee(EmployeeDTO employeeDTO, String password, StoreDTO storeDTO) {
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();
        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();

        if (!authenticationRepository.addUserWithRole(employeeDTO, password))
            return OperationResult.failed("Error - Controller - Failed to register in AuthRepository!");

        return storeNetworkRepository.registerEmployee(employeeDTO, storeDTO);
    }

    /**
     * Gets store list.
     *
     * @return the store list
     */
    public List<StoreDTO> getStoreList() {
        StoreRepository storeRepository = Repositories.getStoreRepository();

        return storeRepository.getSortedStoreList();
    }
}