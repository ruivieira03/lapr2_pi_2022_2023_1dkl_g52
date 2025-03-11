# US 001 - Display listed properties 

# 4. Tests 

### ImportFileReaderTest.java

- Test readClientData

````java
    @Test
    void readClientData() {
        List<ClientDTO> clientData = ifr.readClientData(FILE_PATH);

        assertEquals("Georgia PEDDIE", clientData.get(0).name);
        assertEquals("907-488-1419", clientData.get(0).phoneNumber);
        assertEquals("Ellie SOUTAR", clientData.get(clientData.size() - 1).name);
        assertEquals("907-488-1921", clientData.get(clientData.size() - 1).phoneNumber);
    }

    @Test
    void readClientDataWhenFileIsNotCSV() {
        List<ClientDTO> clientData = ifr.readClientData("C:\\Users\\Pedro Nogueira\\OneDrive\\ISEP\\LAPR2.0\\lapr2_pi_2022_2023_1dkl_g52\\docs\\legacyRealStateUSAMoodle.txt");

        assertNull(clientData);
    }

    @Test
    void readClientDataWhenFileDoesntExist() {
        List<ClientDTO> clientData = ifr.readClientData("invalid/file/path.csv");

        assertEquals(0, clientData.size());
    }

    @Test
    void readClientDataWhenFileIsEmpty() {
        List<ClientDTO> clientData = ifr.readClientData("C:\\Users\\Pedro Nogueira\\OneDrive\\ISEP\\LAPR2.0\\lapr2_pi_2022_2023_1dkl_g52\\src\\test\\Test-Files\\emptyFile.csv");

        assertEquals(0, clientData.size());
    }
````

- Test readLocation

````java
 @Test
    void readLocation() {
        List<String[]> propertyLocation = ifr.readLocation(FILE_PATH, 8, 9);

        assertEquals("449 N Santa Claus Lane", propertyLocation.get(0)[0]);
        assertEquals("99705", propertyLocation.get(0)[propertyLocation.get(0).length - 1]);
        assertEquals("648 E Street", propertyLocation.get(propertyLocation.size() - 1)[0]);
        assertEquals("99501", propertyLocation.get(propertyLocation.size() - 1)[propertyLocation.get(propertyLocation.size() - 1).length - 1]);
    }

    @Test
    void readLocationWhenFileIsNotCSV() {
        List<String[]> propertyLocation = ifr.readLocation("C:\\Users\\Pedro Nogueira\\OneDrive\\ISEP\\LAPR2.0\\lapr2_pi_2022_2023_1dkl_g52\\docs\\legacyRealStateUSAMoodle.txt", 8, 9);

        assertNull(propertyLocation);
    }

    @Test
    void readLocationWhenFileDoesntExist() {
        List<String[]> propertyLocation = ifr.readLocation("invalid/file/path.csv", 8, 9);

        assertEquals(0, propertyLocation.size());
    }

    @Test
    void readLocationWhenFileIsEmpty() {
        List<String[]> propertyLocation = ifr.readLocation("C:\\Users\\Pedro Nogueira\\OneDrive\\ISEP\\LAPR2.0\\lapr2_pi_2022_2023_1dkl_g52\\src\\test\\Test-Files\\emptyFile.csv", 8, 9);

        assertEquals(0, propertyLocation.size());
    }
````

- Test readPropertyData

````java
@Test
    void readPropertyData() {
        List<PropertyDTO> propertyData = ifr.readPropertyData(FILE_PATH);

        assertEquals(1710, propertyData.get(0).area);
        assertEquals(29, propertyData.get(0).distanceFromCityCenter);
        assertEquals(1214, propertyData.get(propertyData.size() - 1).area);
        assertEquals(36, propertyData.get(propertyData.size() - 1).distanceFromCityCenter);
    }

    @Test
    void readPropertyDataWhenFileIsNotCSV() {
        List<PropertyDTO> propertyData = ifr.readPropertyData("C:\\Users\\Pedro Nogueira\\OneDrive\\ISEP\\LAPR2.0\\lapr2_pi_2022_2023_1dkl_g52\\docs\\legacyRealStateUSAMoodle.txt");

        assertNull(propertyData);
    }

    @Test
    void readPropertyDataWhenFileDoesntExist() {
        List<PropertyDTO> propertyData = ifr.readPropertyData("invalid/file/path.csv");

        assertEquals(0, propertyData.size());
    }

    @Test
    void readPropertyDataWhenFileIsEmpty() {
        List<PropertyDTO> propertyData = ifr.readPropertyData("C:\\Users\\Pedro Nogueira\\OneDrive\\ISEP\\LAPR2.0\\lapr2_pi_2022_2023_1dkl_g52\\src\\test\\Test-Files\\emptyFile.csv");

        assertEquals(0, propertyData.size());
    }
````

- Test readStoreData

````java
@Test
    void readStoreData() {
        List<StoreDTO> storeData = ifr.readStoreData(FILE_PATH);

        assertEquals(1, storeData.get(0).id);
        assertEquals("North Pole", storeData.get(0).name);
        assertEquals(2, storeData.get(storeData.size() - 1).id);
        assertEquals("Anchorage", storeData.get(storeData.size() - 1).name);
    }

    @Test
    void readStoreDataWhenFileIsNotCSV() {
        List<StoreDTO> storeData = ifr.readStoreData("C:\\Users\\Pedro Nogueira\\OneDrive\\ISEP\\LAPR2.0\\lapr2_pi_2022_2023_1dkl_g52\\docs\\legacyRealStateUSAMoodle.txt");

        assertNull(storeData);
    }

    @Test
    void readStoreDataWhenFileDoesntExist() {
        List<StoreDTO> storeData = ifr.readStoreData("invalid/file/path.csv");

        assertEquals(0, storeData.size());
    }

    @Test
    void readStoreDataWhenFileIsEmpty() {
        List<StoreDTO> storeData = ifr.readStoreData("C:\\Users\\Pedro Nogueira\\OneDrive\\ISEP\\LAPR2.0\\lapr2_pi_2022_2023_1dkl_g52\\src\\test\\Test-Files\\emptyFile.csv");

        assertEquals(0, storeData.size());
    }
````


# 5. Construction (Implementation)


### Class ImportDataUI

```java
public class ImportLegacyDataUI implements Runnable {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    private final ImportLegacyDataController CTRL = new ImportLegacyDataController();
    private String filePath;



    @Override
    public void run() {
        System.out.printf("\n=========================== Import Legacy Data ===========================\n\n");

        requestFilePath();

        try {
            if (dataIsConfirmed()) {
                if (CTRL.importData(filePath)) {
                    System.out.println(ANSI_GREEN + "Data imported successfully!" + ANSI_RESET);
                } else {
                    System.err.println("Data import failed!");
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void requestFilePath() {
        filePath = Utils.readLineFromConsole("Please enter the file path: ");
    }

    private boolean dataIsConfirmed() {
        System.out.println("Please confirm the file path:");
        return confirm("Confirm (Y/N): ");
    }
}
```


### Class ImportDataController

```java
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;

public class ImportLegacyDataController {
    private StoreNetworkRepository storeNetworkRepository = null;
    private ClientRepository clientRepository = null;
    private PropertySaleRepository propertySaleRepository = null;
    private LocationsRepository locationsRepository = null;
    private final ImportFileReader importFileReader = new ImportFileReader();

    public ImportLegacyDataController() {
        getStoreNetworkRepository();
        getClientRepository();
        getPropertySaleRepository();
        getLocationsRepository();
    }

    private StoreNetworkRepository getStoreNetworkRepository() {
        if (storeNetworkRepository == null) {
            storeNetworkRepository = Repositories.getStoreNetworkRepository();
        }

        return storeNetworkRepository;
    }

    private ClientRepository getClientRepository() {
        if (clientRepository == null) {
            clientRepository = Repositories.getClientRepository();
        }

        return clientRepository;
    }

    private PropertySaleRepository getPropertySaleRepository() {
        if (propertySaleRepository == null) {
            propertySaleRepository = Repositories.getPropertySaleRepository();
        }

        return propertySaleRepository;
    }

    private LocationsRepository getLocationsRepository() {
        if (locationsRepository == null) {
            Repositories repositories = Repositories.getInstance();
            locationsRepository = Repositories.getLocationsRepository();
        }

        return locationsRepository;
    }


    public boolean importData(String filePath) {
        List<Location> storeLocations = locationsRepository.addLocationsThroughData(getStoreLocations(filePath));
        List<Location> propertyLocations = locationsRepository.addLocationsThroughData(getPropertyLocations(filePath));
        List<String[]> propertyList = getPropertyData(filePath);
        List<Client> clientList = clientRepository.addClientThroughData(getClientData(filePath));
        storeNetworkRepository.addStoreThroughData(getStoreData(filePath));
        propertySaleRepository.addPropertySaleThroughData(getPropertySaleData(filePath), clientList);

        return true;
    }

    private List<String[]> getPropertyLocations(String filePath) {
        return importFileReader.readLocation(filePath, 8, 9);
    }

    private List<String[]> getStoreLocations(String filePath) {
        return importFileReader.readLocation(filePath, 27, 28);
    }

    private List<ClientDTO> getClientData(String filePath) {
        return importFileReader.readClientData(filePath);
    }

    private List<PropertySaleDTO> getPropertySaleData(String filePath) {
        return importFileReader.readPropertySaleData(filePath, 18, 25);
    }

    private List<PropertyDTO> getPropertyData(String filePath) {
        return importFileReader.readPropertyData(filePath);
    }

    private List<StoreDTO> getStoreData(String filePath) {
        return importFileReader.readStoreData(filePath);
    }
}
```

### Class ImportFileReader

```java
package pt.ipp.isep.dei.esoft.project.util.data;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.House;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ApartmentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.LandDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.PropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImportFileReader {
    private BufferedReader reader = null;
    private String line = "";



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
            e.printStackTrace();
        }

        return clientData;
    }




    public List<String[]> readLocation (String filePath, int start, int end) {
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
                    if (firstRowIsNotSID(row) ) {
                        propertyLocationData.add(Arrays.copyOfRange(row, start, end)[0].split(", "));
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return propertyLocationData;
    }


    public List<PropertyDTO>  readPropertyData(String filePath) {
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
            e.printStackTrace();
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
                        storeData.add(new StoreDTO(Integer.parseInt(row[25]), row[26], null, null, null, row[28], new Email(row[29])));
                    }
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return storeData;

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
}
````


# 6. Integration and Demo

## Menu
````
=======REAL ESTATE USA=======


Main Menu
1. Do Login
2. Do Register
3. List properties
4. Know the Development Team

0 - Cancel

Type your option: 
1

Login UI:

Enter UserId/Email: 
admin@ex.com

Enter Password: 
1234


Admin Menu:
1. List Properties
2. Register Employee
3. Add location
4. Import Legacy Data

0 - Cancel

Type your option: 
4
````

### Import Legacy Data Successful
````
=========================== Import Legacy Data ===========================


Please enter the file path: 
C:\Users\Pedro Nogueira\OneDrive\ISEP\LAPR2.0\lapr2_pi_2022_2023_1dkl_g52\docs\legacyRealStateUSAMoodle.csv
Please confirm the file path:


Confirm (Y/N): 

y

Data imported successfully!
````

### Import Legacy Data with invalid file
````
=========================== Import Legacy Data ===========================


Please enter the file path: 
asdasd.txt
Please confirm the file path:


Confirm (Y/N): 

y

Invalid file format. Only CSV files are supported.
````

### Import Legacy with faulty data
````
=========================== Import Legacy Data ===========================


Please enter the file path: 
C:\Users\Pedro Nogueira\OneDrive\ISEP\LAPR2.0\lapr2_pi_2022_2023_1dkl_g52\docs\legacyRealStateUSAMoodle.csv
Please confirm the file path:


Confirm (Y/N): 

y

Failed to add client: 383
Error: Invalid Email Address.
Failed to add client: 412
Error: Invalid Email Address.

Data imported successfully!
````




