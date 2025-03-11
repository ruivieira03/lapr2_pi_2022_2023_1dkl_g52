package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ListEmployeeController;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * The type List employee ui.
 */
public class ListEmployeeUI implements Runnable{

    private StoreRepository storeRepository;

    /**
     * Method used to run the class ListEmployeeUI.
     */

    @Override
    public void run() {

        ListEmployeeController controller = new ListEmployeeController();

        storeRepository = Repositories.getStoreRepository();

        if(storeRepository.getAgentList().isEmpty()){
            System.out.println("There are no Employees in the system");
            return;
        }


        List<StoreDTO> storeDTOList = controller.listAllEmployees();

        for (StoreDTO storeDTO : storeDTOList) {
            System.out.println("««««««««««««««««««««««««««««««««««««««««Stores in the Network»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»»");
            System.out.println();
            System.out.println("|                   ID                 |            Name           |             Number Of Properties        |");
            System.out.println(storeDTO);

            for (AgentDTO agentDTO : storeDTO.agents) {
                System.out.println("|       Name         | Passport Number | Phone Number | Tax Number |      adress        |       email        |");
                System.out.println(agentDTO);
                System.out.println();
            }
        }

    }


}

