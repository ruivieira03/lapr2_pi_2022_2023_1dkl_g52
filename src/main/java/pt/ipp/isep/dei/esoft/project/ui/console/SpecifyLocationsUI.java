package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.SpecifyLocationsController;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.readLineFromConsole;

/**
 * The type Specify locations ui.
 */
public class SpecifyLocationsUI implements Runnable {

    public void run() {
        SpecifyLocationsController controller = new SpecifyLocationsController();

        String prompt, districts, cities;
        String[] districtNamesList;
        String[] cityNamesList;

        StateDTO stateDTO = new StateDTO();

        do {
            prompt = "Insert the State name:";
            stateDTO.name = readLineFromConsole(prompt);
        } while (stateDTO.name == null);


        do {
            prompt = "Insert one or more District names divided by a comma (distric1, district2):";
            districts = readLineFromConsole(prompt);
        } while (districts == null);

        districtNamesList = districts.split(",");
        for (String districtName: districtNamesList) {
            stateDTO.districtList.add(new DistrictDTO(districtName));
        }


        for (DistrictDTO districtDTO: stateDTO.districtList) {
            do {
                prompt = String.format("%s - Insert one or more City names divided by a comma:", districtDTO.name);
                cities = readLineFromConsole(prompt);
            } while (cities == null);
            cityNamesList = cities.split(",");

            for (String cityName: cityNamesList) {
                districtDTO.cityList.add(new CityDTO(cityName));
            }
        }


        OperationResult operationResult = controller.createState(stateDTO);

        if (operationResult.success()) System.out.println("Operation Successfull");

        else System.out.println(operationResult.getErrorMessage() + "\nError! Operation failed");

    }

}
