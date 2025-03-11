package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.CreatePropertySaleRequestController;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.House;
import pt.ipp.isep.dei.esoft.project.domain.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.*;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleRequestDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.*;

/**
 * The type Create property sale request ui.
 */
public class CreatePropertySaleRequestUI implements Runnable {

    public void run() {
        CreatePropertySaleRequestController controller = new CreatePropertySaleRequestController();

        // var
        String prompt;
        PropertySaleRequestDTO propertySaleRequestDTO = new PropertySaleRequestDTO();
        PropertyDTO propertyDTO = propertySaleRequestDTO.property;
        Property.Type propertyType;

        StoreDTO selectedStore;

        // property type
        do {
            prompt = "Select Property Type:";
            propertyType = (Property.Type) showAndSelectOne(List.of(Property.Type.values()), prompt);
        } while (propertyType == null);

        if (propertyType == Property.Type.LAND) propertyDTO = new LandDTO();
        if (propertyType == Property.Type.APARTMENT) propertyDTO = new ApartmentDTO();
        if (propertyType == Property.Type.HOUSE) propertyDTO = new HouseDTO();


        // branch
        List<StoreDTO> storeList = controller.getStoreList();
        do {
            prompt = "Select a Store which to choose an agent:";
            selectedStore = (StoreDTO) showAndSelectOne(storeList, prompt);
        } while (selectedStore == null);

        // agent
        List<AgentDTO> agentList = controller.getAgentsFromStore(selectedStore);
        do {
            prompt = "Select Agent from selected branch: ";
            propertySaleRequestDTO.agent = (AgentDTO) showAndSelectOne(agentList, prompt);
        } while (propertySaleRequestDTO.agent == null);


        // type of business
        do {
            prompt = "Select Type of Business";
            propertySaleRequestDTO.typeOfBusiness = (PropertySaleRequest.TypesOfBusinesses) showAndSelectOne(List.of(PropertySaleRequest.TypesOfBusinesses.values()), prompt);
        } while (propertySaleRequestDTO.typeOfBusiness == null);

        // area
        prompt = "Type the area of the property:";
        propertyDTO.area = readDoubleFromConsole(prompt);

        // distance from city center
        prompt = "Type the distance from city center:";
        propertyDTO.distanceFromCityCenter = readDoubleFromConsole(prompt);

        // requested price
        prompt = "Type the price for the property:";
        propertySaleRequestDTO.requestedPrice = (float) readDoubleFromConsole(prompt);

        // state
        List<StateDTO> statesList = controller.getStates();

        do {
            prompt = "Select a State:";
            propertyDTO.state = (StateDTO) showAndSelectOne(statesList, prompt);
        } while (propertyDTO.state == null);

        // district
        List<DistrictDTO> districtsList = propertyDTO.state.districtList;

        do {
            prompt = "Select a Distric:";
            propertyDTO.district = (DistrictDTO) showAndSelectOne(districtsList, prompt);
        } while (propertyDTO.district == null);

        // city
        List<CityDTO> citiesList = propertyDTO.district.cityList;

        do {
            prompt = "Select a City:";
            propertyDTO.city = (CityDTO) showAndSelectOne(citiesList, prompt);
        } while (propertyDTO.city == null);

        // street
        do {
            prompt = "Type the property's street:";
            propertyDTO.street = readLineFromConsole(prompt);
        } while (propertyDTO.street == null);

        // zipcode
        do {
            prompt = "Type the property's zip code:";
            propertyDTO.zipCode = readIntegerFromConsole(prompt);
        } while (propertyDTO.zipCode < 0);

        // residential property var
        if (propertyType.equals(Property.Type.APARTMENT) || propertyType.equals(Property.Type.HOUSE)) {
            ResidentialPropertyDTO residentialPropertyDTO = (ResidentialPropertyDTO) propertyDTO;

            // number of bedrooms
            prompt = "Type the number of bedrooms:";
            residentialPropertyDTO.numberOfBedrooms = readIntegerFromConsole(prompt);

            prompt = "Type the number of bathrooms:";
            residentialPropertyDTO.numberOfBathrooms = readIntegerFromConsole(prompt);

            do {
                prompt = "Type the available equipment";
                residentialPropertyDTO.availableEquipment = readLineFromConsole(prompt);
            } while (residentialPropertyDTO.availableEquipment == null);

            if (propertyType.equals(Property.Type.HOUSE)) {
                HouseDTO houseDTO = (HouseDTO) propertyDTO;

                List<String> booleanList = List.of(new String[]{"Yes", "No"});
                int index;

                do {
                    prompt = "Does the property have an inhabitable loft:";
                    index = showAndSelectIndex(booleanList, prompt);
                } while (index < 0);

                houseDTO.existanceOfInhabitableLoft = index == 0;

                do {
                    prompt = "Does the property have a basement:";
                    index = showAndSelectIndex(booleanList, prompt);
                } while (index < 0);

                houseDTO.existanceOfBasement = index == 0;

                do {
                    prompt = "Select the direction of the sun exposure:";
                    houseDTO.sunExposure = (House.SunExposure) showAndSelectOne(List.of(House.SunExposure.values()), prompt);
                } while (houseDTO.sunExposure == null);
            }
        }

        do {
            prompt = "Insert properties photos (not implemented: only a string for now)";
            propertyDTO.photos = readLineFromConsole(prompt);
        } while (propertyDTO.photos == null);

        propertySaleRequestDTO.property = propertyDTO;

        OperationResult operationResult = controller.createSalesRequest(propertySaleRequestDTO);
        if (operationResult.success()) System.out.println("Operation Successful!");
        else System.out.println(operationResult.getErrorMessage() + "\nError: operation Failed!");

    }


}
