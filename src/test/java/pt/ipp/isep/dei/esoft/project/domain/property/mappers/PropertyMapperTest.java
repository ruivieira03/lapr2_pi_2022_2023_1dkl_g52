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
import pt.ipp.isep.dei.esoft.project.domain.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ApartmentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.LandDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.PropertyDTO;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PropertyMapperTest {

    private Apartment apartment;
    private House house;
    private Land land;
    private ApartmentDTO apartmentDTO;
    private HouseDTO houseDTO;
    private LandDTO landDTO;
    private final PropertyMapper mapper = new PropertyMapper();

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

        // land
        land = new Land();
        land.setArea(1000);
        land.setDistanceFromCityCentre(1.0);
        land.setZipCode(12345);
        land.setStreet("Street");
        land.setState(state);
        land.setDistrict(district);
        land.setCity(city);
        land.setClient(client);
        land.setPhotos("p");

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
        landDTO = new LandDTO(1000, 1.0, "Street", 12345, stateDTO, districtDTO, cityDTO, "p", clientDTO);

        // add need stuff to the repositories
        // locations
        Repositories.getLocationsRepository().createState(stateDTO);

        // client
        Repositories.getClientRepository().createClient(clientDTO);
    }

    @Test
    void toDTO() {
        // valid - apartment
        ErrorOptional<? extends PropertyDTO> dto = mapper.toDTO(apartment);
        assertFalse(dto.hasError());

        // valid - house
        dto = mapper.toDTO(house);
        assertFalse(dto.hasError());

        // valid - land
        dto = mapper.toDTO(land);
        assertFalse(dto.hasError());

        // all attributes must be equal
        assertEquals(land.getArea(), dto.get().area);
        assertEquals(land.getDistanceFromCityCentre(), dto.get().distanceFromCityCenter);
        assertEquals(land.getState().getName(), dto.get().state.name);
        assertEquals(land.getDistrict().getName(), dto.get().district.name);
        assertEquals(land.getCity().getName(), dto.get().city.name);
        assertEquals(land.getZipCode(), dto.get().zipCode);
        assertEquals(land.getStreet(), dto.get().street);
        assertEquals(land.getClient().getEmail().toString(), dto.get().client.email.toString());
        assertEquals(land.getPhotos(), dto.get().photos);

        // invalid - invalid PropertyType
        class InvalidPropertyType extends Property{};
        InvalidPropertyType invalidPropertyType = new InvalidPropertyType();
        dto = mapper.toDTO(invalidPropertyType);

        // invalid - null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid - apartmentDTO
        ErrorOptional<? extends Property> domain = mapper.toDomain(apartmentDTO);
        assertFalse(domain.hasError());

        // valid - houseDTO
        domain = mapper.toDomain(houseDTO);
        assertFalse(domain.hasError());

        // valid - landDTO
        domain = mapper.toDomain(landDTO);

        // all attributes must be equal
        assertEquals(landDTO.area, domain.get().getArea());
        assertEquals(landDTO.distanceFromCityCenter, domain.get().getDistanceFromCityCentre());
        assertEquals(landDTO.state.name, domain.get().getState().getName());
        assertEquals(landDTO.district.name, domain.get().getDistrict().getName());
        assertEquals(landDTO.city.name, domain.get().getCity().getName());
        assertEquals(landDTO.zipCode, domain.get().getZipCode());
        assertEquals(landDTO.street, domain.get().getStreet());
        assertEquals(landDTO.client.email.toString(), domain.get().getClient().getEmail().toString());
        assertEquals(landDTO.photos, domain.get().getPhotos());

        // invalid - client repository does not contain client
        landDTO.client = new ClientDTO("Invalid", 123456789, "123-123-1234", "123-12-1234", "adress", "invalid@ex.com");
        domain = mapper.toDomain(landDTO);
        assert (domain.hasError());

        // invalid - location repository does not contain city
        landDTO.city = new CityDTO("Invalid City");
        domain = mapper.toDomain(landDTO);
        assert (domain.hasError());

        // invalid - location repository does not contain district
        landDTO.district = new DistrictDTO("Invalid District");
        domain = mapper.toDomain(landDTO);
        assert (domain.hasError());

        // invalid - location repository does not contain state
        landDTO.state = new StateDTO("Invalid State");
        domain = mapper.toDomain(landDTO);
        assert (domain.hasError());


        // invalid - invalid PropertyType
        class InvalidPropertyDTOType extends PropertyDTO{};
        InvalidPropertyDTOType invalidPropertyDTOType = new InvalidPropertyDTOType();
        domain = mapper.toDomain(invalidPropertyDTOType);
        assert (domain.hasError());

        // invalid - null
        domain = mapper.toDomain(null);
        assert (domain.hasError());

    }
}