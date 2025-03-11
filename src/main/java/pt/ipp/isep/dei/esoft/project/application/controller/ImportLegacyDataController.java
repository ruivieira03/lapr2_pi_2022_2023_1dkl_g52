package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySoldDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.repository.*;
import pt.ipp.isep.dei.esoft.project.util.data.ImportFileReader;

import java.util.List;

/**
 * The type Import legacy data controller.
 */
public class ImportLegacyDataController {
    private final ImportFileReader importFileReader = new ImportFileReader();


    /**
     * Instantiates a new Import legacy data controller.
     */
    public ImportLegacyDataController() {
    }


    /**
     * File reader to import data.
     *
     * @param filePath the file path
     * @return the boolean
     */
    public boolean importData(String filePath) {
        List<ClientDTO> clientList = importFileReader.readClientData(filePath);
        List<StateDTO> statesList = importFileReader.readStates(filePath);
        List<StoreDTO> storeList = importFileReader.readStoreData(filePath);
        List<PropertySoldDTO> propertySaleRequestDTO = importFileReader.readPropertySoldData(filePath);

        addClientData(clientList);
        addSatesData(statesList);
        addStoreData(storeList);
        addPropertySoldData(propertySaleRequestDTO);

        return true;
    }

    /**
     * Add client data.
     *
     * @param clientList the client list
     */
    public void addClientData(List<ClientDTO> clientList) {
        ClientRepository clientRepository = Repositories.getClientRepository();

        for (ClientDTO client : clientList)
            clientRepository.createClient(client);
    }


    /**
     * Add sates data.
     *
     * @param stateList the state list
     */
    public void addSatesData(List<StateDTO> stateList) {
        LocationsRepository locationsRepository = Repositories.getLocationsRepository();

        for (StateDTO state : stateList)
         locationsRepository.createState(state);
    }

    /**
     * Add store data.
     *
     * @param storeList the store list
     */
    public void addStoreData(List<StoreDTO> storeList) {
        StoreRepository storeRepository = Repositories.getStoreRepository();

        for (StoreDTO storeDTO : storeList) {
            storeRepository.createStore(storeDTO);
            storeRepository.addStorePropertyCount(storeDTO);
        }
    }

    /**
     * Add property sold data.
     *
     * @param propertySoldList the property sold list
     */
    public void addPropertySoldData(List<PropertySoldDTO> propertySoldList) {
        PropertySoldRepository propertySoldRepository = Repositories.getPropertySoldRepository();

        for (PropertySoldDTO dto : propertySoldList)
            propertySoldRepository.createPropertySold(dto);
    }
}
