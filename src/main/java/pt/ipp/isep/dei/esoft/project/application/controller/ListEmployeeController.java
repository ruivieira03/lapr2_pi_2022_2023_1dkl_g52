package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.StoreRepository;


import java.util.*;

/**
 * The type List employee controller.
 */
public class ListEmployeeController {

    private static Repositories repositories;

    /**
     * Instantiates a new List employee controller.
     */
    public ListEmployeeController(){
        repositories = Repositories.getInstance();
    }

    /**
     * List all employees list.
     *
     * @return the list
     */
    public List<StoreDTO> listAllEmployees(){
        StoreRepository storeRepository = Repositories.getStoreRepository();

        return storeRepository.getSortedStoreList();
    }

}
