package pt.ipp.isep.dei.esoft.project.util.data;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.PropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySoldDTO;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ImportFileReaderTest {

    private static final String FILE_PATH = "C:\\Users\\Pedro Nogueira\\OneDrive\\ISEP\\LAPR2.0\\lapr2_pi_2022_2023_1dkl_g52\\docs\\legacyRealStateUSAMoodle.csv";
    private static final ImportFileReader ifr = new ImportFileReader();

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




    @Test
    void readLocation() {
        List<String[]> propertyLocation = ifr.readFileData(FILE_PATH, 8, 9);

        assertEquals("449 N Santa Claus Lane", propertyLocation.get(0)[0]);
        assertEquals("99705", propertyLocation.get(0)[propertyLocation.get(0).length - 1]);
        assertEquals("648 E Street", propertyLocation.get(propertyLocation.size() - 1)[0]);
        assertEquals("99501", propertyLocation.get(propertyLocation.size() - 1)[propertyLocation.get(propertyLocation.size() - 1).length - 1]);
    }

    @Test
    void readLocationWhenFileIsNotCSV() {
        List<String[]> propertyLocation = ifr.readFileData("C:\\Users\\Pedro Nogueira\\OneDrive\\ISEP\\LAPR2.0\\lapr2_pi_2022_2023_1dkl_g52\\docs\\legacyRealStateUSAMoodle.txt", 8, 9);

        assertNull(propertyLocation);
    }

    @Test
    void readLocationWhenFileDoesntExist() {
        List<String[]> propertyLocation = ifr.readFileData("invalid/file/path.csv", 8, 9);

        assertEquals(0, propertyLocation.size());
    }

    @Test
    void readLocationWhenFileIsEmpty() {
        List<String[]> propertyLocation = ifr.readFileData("C:\\Users\\Pedro Nogueira\\OneDrive\\ISEP\\LAPR2.0\\lapr2_pi_2022_2023_1dkl_g52\\src\\test\\Test-Files\\emptyFile.csv", 8, 9);

        assertEquals(0, propertyLocation.size());
    }




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




    @Test
    void readStoreData() {
        StoreNetworkDTO storeData = ifr.readStoreData(FILE_PATH);

        assertEquals(1, storeData.stores.get(0).id);
        assertEquals("North Pole", storeData.stores.get(0).name);
        assertEquals(2, storeData.stores.get(storeData.stores.size() - 1).id);
        assertEquals("Anchorage", storeData.stores.get(storeData.stores.size() - 1).name);
    }

    @Test
    void readStoreDataWhenFileIsNotCSV() {
        StoreNetworkDTO storeData = ifr.readStoreData("C:\\Users\\Pedro Nogueira\\OneDrive\\ISEP\\LAPR2.0\\lapr2_pi_2022_2023_1dkl_g52\\docs\\legacyRealStateUSAMoodle.txt");

        assertNull(storeData);
    }

    @Test
    void readStoreDataWhenFileDoesntExist() {
        StoreNetworkDTO storeData = ifr.readStoreData("invalid/file/path.csv");

        assertEquals(0, storeData.stores.size());
    }

    @Test
    void readStoreDataWhenFileIsEmpty() {
        StoreNetworkDTO storeData = ifr.readStoreData("C:\\Users\\Pedro Nogueira\\OneDrive\\ISEP\\LAPR2.0\\lapr2_pi_2022_2023_1dkl_g52\\src\\test\\Test-Files\\emptyFile.csv");

        assertEquals(0, storeData.stores.size());
    }


    @Test
    void readPropertySoldData() {
        List<PropertySoldDTO> propertySoldData = ifr.readPropertySoldData(FILE_PATH);

        assertEquals(1, propertySoldData.get(0).id);
        assertEquals(203748, propertySoldData.get(0).soldPrice);
        assertEquals(503, propertySoldData.get(propertySoldData.size() - 1).id);
        assertEquals(131667, propertySoldData.get(propertySoldData.size() - 1).soldPrice);
    }
}