package pt.ipp.isep.dei.esoft.project.util.data;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.House;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ApartmentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.LandDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.PropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySoldDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImportFileReader {
    private BufferedReader reader = null;
    private String line = "";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public List<ClientDTO> readClientData(String filePath) {
        List<ClientDTO> clientData = new ArrayList<>();

        if (fileIsNotCSV(filePath)) {
            System.out.println("Invalid file format. Only CSV files are supported.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            this.reader = reader;

            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    String[] row = line.split(";");

                    if (sidIsNotEmpty(row)) {
                        if (firstRowIsNotSID(row)) {
                            clientData.add(new ClientDTO(row[1], Integer.parseInt(row[2]), row[5], row[3], null, row[4]));
                        }
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.err.println("Failed to add client: " + lineNumber);
                    System.err.println("Error: " + e.getMessage());
                    clientData.add(new ClientDTO());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        return clientData;
    }


    public List<String[]> readFileData(String filePath, int start, int end) {
        List<String[]> propertyLocationData = new ArrayList<>();

        if (fileIsNotCSV(filePath)) {
            System.out.println("Invalid file format. Only CSV files are supported.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            this.reader = reader;

            while (lineIsNotNull()) {
                String[] row = line.split(";");

                if (sidIsNotEmpty(row)) {
                    if (firstRowIsNotSID(row)) {
                        propertyLocationData.add(Arrays.copyOfRange(row, start, end)[0].split(","));
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        return propertyLocationData;
    }


    public List<PropertyDTO> readPropertyData(String filePath) {
        List<PropertyDTO> propertyData = new ArrayList<>();

        if (fileIsNotCSV(filePath)) {
            System.out.println("Invalid file format. Only CSV files are supported.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            this.reader = reader;

            while (lineIsNotNull()) {
                String[] row = line.split(";");

                if (sidIsNotEmpty(row)) {
                    if (firstRowIsNotSID(row)) {
                        if (row[6].equalsIgnoreCase("house")) {
                            propertyData.add(new HouseDTO(Double.parseDouble(row[7]), Double.parseDouble(row[9]), null, 0, null, null, null, null, null, Integer.parseInt(row[10]), Integer.parseInt(row[11]), Integer.parseInt(row[12]), null, Boolean.parseBoolean(row[15]), Boolean.parseBoolean(row[16]), House.SunExposure.fromAbbreviation(row[17])));

                        } else if (row[6].equalsIgnoreCase("apartment")) {
                            propertyData.add(new ApartmentDTO(Double.parseDouble(row[7]), Double.parseDouble(row[9]), null, 0, null, null, null, null, null, Integer.parseInt(row[10]), Integer.parseInt(row[11]), Integer.parseInt(row[12]), null));

                        } else if (row[6].equalsIgnoreCase("land")) {
                            propertyData.add(new LandDTO(Double.parseDouble(row[7]), Double.parseDouble(row[9]), null, 0, null, null, null, null, null));

                        }
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        return propertyData;
    }

    public List<StoreDTO> readStoreData(String filePath) {
        List<StoreDTO> storeData = new ArrayList<>();

        if (fileIsNotCSV(filePath)) {
            System.out.println("Invalid file format. Only CSV files are supported.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            this.reader = reader;

            while (lineIsNotNull()) {
                String[] row = line.split(";");

                if (sidIsNotEmpty(row)) {
                    if (firstRowIsNotSID(row)) {
                        // store location
                        String[] locationData = row[27].split(",");
                        String adress = locationData[0];

                        int zipCode = 10000;
                        if (locationData.length == 5)
                            zipCode = Integer.parseInt(locationData[4].replace(" ", ""));

                        // property City
                        CityDTO city = new CityDTO(locationData[3]);
                        //
                        // property District
                        DistrictDTO district = new DistrictDTO(locationData[2]);

                        // property State
                        StateDTO state = new StateDTO(locationData[1]);

                        storeData.add(new StoreDTO(row[26], row[29], row[28], adress, zipCode, state, district, city));
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        return storeData;
    }


    public List<PropertySoldDTO> readPropertySoldData(String filePath) {
        List<PropertySoldDTO> propertySoldData = new ArrayList<>();

        if (fileIsNotCSV(filePath)) {
            System.out.println("Invalid file format. Only CSV files are supported.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            this.reader = reader;

            while (lineIsNotNull()) {
                String[] row = line.split(";");

                if (sidIsNotEmpty(row)) {
                    if (firstRowIsNotSID(row)) {
                        // get location data
                        String[] locationData = row[8].split(",");
                        String adress = locationData[0];
                        StateDTO stateDTO = new StateDTO(locationData[1]);
                        DistrictDTO districtDTO = new DistrictDTO(locationData[2]);
                        CityDTO cityDTO = new CityDTO(locationData[3]);
                        int zipCode = 10000;
                        if (locationData.length == 5)
                            zipCode = Integer.parseInt(locationData[4].replace(" ", ""));

                        // get client
                        ClientDTO clientDTO = new ClientDTO(row[1], Integer.parseInt(row[2]), row[5], row[3], null, row[4]);


                        PropertyDTO propertyDTO;

                        if (row[6].equalsIgnoreCase("house")) {
                            propertyDTO = new HouseDTO(Double.parseDouble(row[7]), Double.parseDouble(row[9]), adress, zipCode, stateDTO, districtDTO, cityDTO, "p", clientDTO, Integer.parseInt(row[10]), Integer.parseInt(row[11]), Integer.parseInt(row[12]), null, Boolean.parseBoolean(row[15]), Boolean.parseBoolean(row[16]), House.SunExposure.fromAbbreviation(row[17]));

                        } else if (row[6].equalsIgnoreCase("apartment")) {
                            propertyDTO = new ApartmentDTO(Double.parseDouble(row[7]), Double.parseDouble(row[9]), adress, zipCode, stateDTO, districtDTO, cityDTO, "p", clientDTO, Integer.parseInt(row[10]), Integer.parseInt(row[11]), Integer.parseInt(row[12]), null);

                        } else {
                            propertyDTO = new LandDTO(Double.parseDouble(row[7]), Double.parseDouble(row[9]), adress, zipCode, stateDTO, districtDTO, cityDTO, "p", clientDTO);
                        }
                        // type of business
                        PropertySaleRequest.TypesOfBusinesses typesOfBusinesses = PropertySaleRequest.TypesOfBusinesses.fromLabel(row[24]);
                        // date of request
                        String[] dateOfRequestString = row[22].split("-");
                        int year = Integer.parseInt(dateOfRequestString[2]);
                        int month = Integer.parseInt(dateOfRequestString[1]);
                        int day = Integer.parseInt(dateOfRequestString[0]);
                        LocalDate dateOfRequest = LocalDate.of(year, month, day);

                        // date of sale
                        String[] dateOFSaleString = row[23].split("-");
                        year = Integer.parseInt(dateOfRequestString[2]);
                        month = Integer.parseInt(dateOfRequestString[1]);
                        day = Integer.parseInt(dateOfRequestString[0]);
                        LocalDateTime dateOfSale = LocalDateTime.of(year, month, day, 0, 0);

                        // prices
                        float price = Float.parseFloat(row[18]);
                        float commission = Float.parseFloat(row[20]) / 100;
                        float requestedPrice = (price) / (1 + commission);
                        double paidAmount = Double.parseDouble(row[19]);

                        PropertySoldDTO propertySoldDTO = new PropertySoldDTO(propertyDTO, typesOfBusinesses, requestedPrice, null, dateOfRequest, PropertySale.CommissionType.PERCENTAGE, commission, paidAmount, null, dateOfSale);

                        propertySoldData.add(propertySoldDTO);
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        return propertySoldData;
    }


    private boolean fileIsNotCSV(String filePath) {
        return !filePath.endsWith(".csv");
    }

    private boolean lineIsNotNull() throws IOException {
        return (line = reader.readLine()) != null;
    }

    private boolean sidIsNotEmpty(String[] row) {
        return !row[0].equalsIgnoreCase("");
    }

    private boolean firstRowIsNotSID(String[] row) {
        return !row[0].equalsIgnoreCase("sid");
    }

    public List<StateDTO> readStates(String filePath) {
        List<StateDTO> stateDTOList = new ArrayList<>();

        if (fileIsNotCSV(filePath)) {
            System.out.println("Invalid file format. Only CSV files are supported.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            this.reader = reader;

            while (lineIsNotNull()) {
                String[] row = line.split(";");

                if (sidIsNotEmpty(row)) {
                    if (firstRowIsNotSID(row)) {
                        // property Location
                        String[] locationData = row[8].split(",");

                        // property City
                        CityDTO propertyCity = new CityDTO(locationData[3]);

                        // property District
                        DistrictDTO propertyDistrict = new DistrictDTO(locationData[2]);
                        propertyDistrict.cityList.add(propertyCity);

                        // property State
                        StateDTO propertyState = new StateDTO(locationData[1]);
                        propertyState.districtList.add(propertyDistrict);

                        // checks if its already on the list
                        boolean added = false;
                        for (StateDTO stateDTO : stateDTOList) {
                            if (stateDTO.name.equals(propertyState.name)){
                                added = true;
                                boolean addedDistrict = false;
                                for (DistrictDTO districtDTO : stateDTO.districtList){
                                    if (districtDTO.name.equals(propertyDistrict.name)) {
                                        addedDistrict = true;
                                        boolean addedCity = false;
                                        for (CityDTO cityDTO : districtDTO.cityList) {
                                            if (cityDTO.name.equals(propertyCity.name)) {
                                                addedCity = true;
                                            }
                                        }
                                        if (!addedCity) {
                                            districtDTO.cityList.add(propertyCity);
                                        }
                                    }
                                }
                                if (!addedDistrict)
                                    stateDTO.districtList.add(propertyDistrict);
                            }
                        }

                        if (!added)
                            stateDTOList.add(propertyState);


                        // store location
                        locationData = row[27].split(",");

                        // property City
                        propertyCity = new CityDTO(locationData[3]);

                        // property District
                        propertyDistrict = new DistrictDTO(locationData[2]);
                        propertyDistrict.cityList.add(propertyCity);

                        // property State
                        propertyState = new StateDTO(locationData[1]);
                        propertyState.districtList.add(propertyDistrict);

                        // checks if its already on the list
                        added = false;
                        for (StateDTO stateDTO : stateDTOList) {
                            if (stateDTO.name.equals(propertyState.name)){
                                added = true;
                                boolean addedDistrict = false;
                                for (DistrictDTO districtDTO : stateDTO.districtList){
                                    if (districtDTO.name.equals(propertyDistrict.name)) {
                                        addedDistrict = true;
                                        boolean addedCity = false;
                                        for (CityDTO cityDTO : districtDTO.cityList) {
                                            if (cityDTO.name.equals(propertyCity.name)) {
                                                addedCity = true;
                                            }
                                        }
                                        if (!addedCity) {
                                            districtDTO.cityList.add(propertyCity);
                                        }
                                    }
                                }
                                if (!addedDistrict)
                                    stateDTO.districtList.add(propertyDistrict);
                            }
                        }

                        if (!added)
                            stateDTOList.add(propertyState);

                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        return stateDTOList;

    }
}
