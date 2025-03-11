package pt.ipp.isep.dei.esoft.project.domain.property.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.property.House;
import pt.ipp.isep.dei.esoft.project.domain.property.ResidentialProperty;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ApartmentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ResidentialPropertyDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ResidentialPropertyMapperTest {

    private Apartment apartment;
    private House house;
    private ApartmentDTO apartmentDTO;
    private HouseDTO houseDTO;
    private final ResidentialPropertyMapper mapper = new ResidentialPropertyMapper();

    @BeforeEach
    void beforeEach() {
        // -----Domain-----
        // client
        Client client = new Client();
        client.setName("John");
        client.setPassportNumber(123456789);
        client.setPhoneNumber("123-123-1234");
        client.setTaxNumber("123-12-1234");
        client.setAddress("Adress");
        client.setEmail("email@ex.com");

        // city
        City city = new City();
        city.setName("City");

        // district
        District district = new District();
        district.setName("District");
        List<City> cityList = new ArrayList<>();
        cityList.add(city);
        district.setCities(cityList);

        // state
        State state = new State();
        state.setName("State");
        List<District> districtList = new ArrayList<>();
        districtList.add(district);
        state.setDistricts(districtList);

        // apartment
        apartment = new Apartment();
        apartment.setArea(1000);
        apartment.setDistanceFromCityCentre(1.0);
        apartment.setZipCode(12345);
        apartment.setStreet("Street");
        apartment.setState(state);
        apartment.setDistrict(district);
        apartment.setCity(city);
        apartment.setClient(client);
        apartment.setPhotos("p");
        apartment.setNumberOfBathrooms(1);
        apartment.setNumberOfBedrooms(2);
        apartment.setNumberOfParkingSpaces(3);
        apartment.setAvailableEquipment("Available Equipment");

        // house
        house = new House();
        house.setArea(1000);
        house.setDistanceFromCityCentre(1.0);
        house.setZipCode(12345);
        house.setStreet("Street");
        house.setState(state);
        house.setDistrict(district);
        house.setCity(city);
        house.setClient(client);
        house.setPhotos("p");
        house.setNumberOfBathrooms(1);
        house.setNumberOfBedrooms(2);
        house.setNumberOfParkingSpaces(3);
        house.setAvailableEquipment("Available Equipment");
        house.setSunExposure(House.SunExposure.WEST);
        house.setExistanceOfBasement(false);
        house.setExistanceOfInhabitableLoft(true);

        // -----DTO-----
        // locations
        StateDTO stateDTO = new StateDTO("State", new String[]{"District"}, new String[][]{{"City"}});
        DistrictDTO districtDTO = stateDTO.districtList.get(0);
        CityDTO cityDTO = districtDTO.cityList.get(0);

        // owner
        ClientDTO clientDTO = new ClientDTO("name", 123456789, "123-123-1234", "123-12-1234", "", "ex@ex.com");

        // properties
        apartmentDTO = new ApartmentDTO(1000, 1.0, "Street", 12345, stateDTO, districtDTO, cityDTO, "p", clientDTO, 1, 2, 3, "Avaliable Equipment");
        houseDTO = new HouseDTO(1000, 1.0, "Street", 12345, stateDTO, districtDTO, cityDTO, "p", clientDTO, 1, 2, 3, "Avaliable Equipment", false, true, House.SunExposure.NORTH);
    }

    @Test
    void toDTO() {
        // valid - house
        ErrorOptional<? extends ResidentialPropertyDTO> dto = mapper.toDTO(house);
        assertFalse(dto.hasError());

        // valid - apartment
        dto = mapper.toDTO(apartment);
        assertFalse(dto.hasError());

        // all attributes must be equal
        assertEquals(apartment.getNumberOfBathrooms(), dto.get().numberOfBathrooms);
        assertEquals(apartment.getNumberOfBedrooms(), dto.get().numberOfBedrooms);
        assertEquals(apartment.getNumberOfParkingSpaces(), dto.get().numberOfParkingSpaces);
        assertEquals(apartment.getAvailableEquipment(), dto.get().availableEquipment);

        // invalid - invalid residentialPropertyType
        class InvalidResidentialProperty extends ResidentialProperty{};
        InvalidResidentialProperty invalidResidentialProperty = new InvalidResidentialProperty();
        dto = mapper.toDTO(invalidResidentialProperty);
        assert (dto.hasError());

        // invalid - null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid - houseDTO
        ErrorOptional<? extends ResidentialProperty> domain = mapper.toDomain(houseDTO);
        assertFalse(domain.hasError());

        // valid - apartmentDTO
        domain = mapper.toDomain(apartmentDTO);
        assertFalse(domain.hasError());

        // all attributes must be equal
        assertEquals(apartmentDTO.numberOfBathrooms, domain.get().getNumberOfBathrooms());
        assertEquals(apartmentDTO.numberOfBedrooms, domain.get().getNumberOfBedrooms());
        assertEquals(apartmentDTO.numberOfParkingSpaces, domain.get().getNumberOfParkingSpaces());
        assertEquals(apartmentDTO.availableEquipment, domain.get().getAvailableEquipment());

        // invalid - invalid ResidentialPropertyDTOType
        class InvalidResidentialPropertyDTO extends ResidentialPropertyDTO {};
        InvalidResidentialPropertyDTO invalidResidentialPropertyDTO = new InvalidResidentialPropertyDTO();

        domain = mapper.toDomain(invalidResidentialPropertyDTO);
        assert (domain.hasError());

        // invalid - null
        domain = mapper.toDomain(null);
        assert (domain.hasError());
    }
}