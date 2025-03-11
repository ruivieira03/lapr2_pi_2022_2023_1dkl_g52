package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterPropertySaleController;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.House;
import pt.ipp.isep.dei.esoft.project.domain.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.*;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.*;

/**
 * The type Register property sale ui.
 */
public class RegisterPropertySaleUI implements Runnable {
    public void run() {
        RegisterPropertySaleController controller = new RegisterPropertySaleController();

        // var
        String prompt, ownerEmail;
        Property.Type propertyType;
        PropertySaleDTO propertySaleDTO = new PropertySaleDTO();

        do {
            prompt = "Select Property Type:";
            propertyType = (Property.Type) showAndSelectOne(List.of(Property.Type.values()), prompt);
        } while (propertyType == null);

        if (propertyType == Property.Type.LAND) propertySaleDTO.property = new LandDTO();
        if (propertyType == Property.Type.APARTMENT) propertySaleDTO.property = new ApartmentDTO();
        if (propertyType == Property.Type.HOUSE) propertySaleDTO.property = new HouseDTO();
        PropertyDTO propertyDTO = propertySaleDTO.property;


        do {
            prompt = "Select Type of Business";
            propertySaleDTO.typeOfBusiness = (PropertySaleRequest.TypesOfBusinesses) showAndSelectOne(List.of(PropertySaleRequest.TypesOfBusinesses.values()), prompt);
        } while (propertySaleDTO.typeOfBusiness == null);


        prompt = "Type the area of the property:";
        propertyDTO.area = readDoubleFromConsole(prompt);

        prompt = "Type the distance from city center:";
        propertyDTO.distanceFromCityCenter = readDoubleFromConsole(prompt);

        prompt = "Type the price for the property:";
        propertySaleDTO.requestedPrice = (float) readDoubleFromConsole(prompt);

        List<StateDTO> statesList = controller.getStatesList();

        do {
            prompt = "Select a State:";
            propertyDTO.state = (StateDTO) showAndSelectOne(statesList, prompt);
        } while (propertyDTO.state == null);

        List<DistrictDTO> districtsList = propertyDTO.state.districtList;

        do {
            prompt = "Select a Distric:";
            propertyDTO.district = (DistrictDTO) showAndSelectOne(districtsList, prompt);
        } while (propertyDTO.district == null);

        List<CityDTO> citiesList = propertyDTO.district.cityList;

        do {
            prompt = "Select a City:";
            propertyDTO.city = (CityDTO) showAndSelectOne(citiesList, prompt);
        } while (propertyDTO.city == null);

        do {
            prompt = "Type the property's street:";
            propertyDTO.street = readLineFromConsole(prompt);
        } while (propertyDTO.street == null);

        do {
            prompt = "Type the property's zip code:";
            propertyDTO.zipCode = readIntegerFromConsole(prompt);
        } while (propertyDTO.zipCode < 0);

        if (propertyDTO instanceof ResidentialPropertyDTO) {
            ResidentialPropertyDTO residentialPropertyDTO = (ResidentialPropertyDTO) propertyDTO;

            prompt = "Type the number of bedrooms:";
            residentialPropertyDTO.numberOfBedrooms = readIntegerFromConsole(prompt);

            prompt = "Type the number of bathrooms:";
            residentialPropertyDTO.numberOfBathrooms = readIntegerFromConsole(prompt);

            do {
                prompt = "Type the available equipment";
                residentialPropertyDTO.availableEquipment = readLineFromConsole(prompt);
            } while (residentialPropertyDTO.availableEquipment == null);

            if (propertyDTO instanceof HouseDTO) {
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
            prompt = "Insert properties photos (not implemented yet: only a string for now)";
            propertyDTO.photos = readLineFromConsole(prompt);
        } while (propertyDTO.photos == null);

        do {
            prompt = "Insert owners email:";
            ownerEmail = readLineFromConsole(prompt);
        } while (ownerEmail == null);


        do {
            prompt = "Select a type of commission:";
            propertySaleDTO.typeOfCommission = (PropertySale.CommissionType) showAndSelectOne(List.of(PropertySale.CommissionType.values()), prompt);
        } while (propertySaleDTO.typeOfCommission == null);


        do {
            prompt = "Type the commission (a number or a percentage beteewn 0-1)";
            propertySaleDTO.commission = readDoubleFromConsole(prompt);
        } while (propertySaleDTO.commission < 0);

        OperationResult operationResult = controller.createPropertySale(propertySaleDTO, ownerEmail);

        if (operationResult.success()) System.out.println("Operation Successfull!");
        else System.out.println(operationResult.getErrorMessage() + "\nError: Operation Failed!");
    }
}
