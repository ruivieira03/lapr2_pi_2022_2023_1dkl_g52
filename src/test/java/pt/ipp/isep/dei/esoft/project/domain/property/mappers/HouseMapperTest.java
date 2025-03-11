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
import pt.ipp.isep.dei.esoft.project.domain.property.House;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class HouseMapperTest {

    private House house;
    private HouseDTO houseDTO;
    private final HouseMapper mapper = new HouseMapper();


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

        // property
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

        houseDTO = new HouseDTO(1000, 1.0, "Street", 12345, stateDTO, districtDTO, cityDTO, "p", clientDTO, 1, 2, 3, "Avaliable Equipment", false, true, House.SunExposure.NORTH);
    }

    @Test
    void toDTO() {
        // valid
        ErrorOptional<HouseDTO> dto = mapper.toDTO(house);
        assertFalse(dto.hasError());

        // all attributes must be equal
        assertEquals(house.getSunExposure(), dto.get().sunExposure);
        assertEquals(house.getExistanceOfInhabitableLoft(), dto.get().existanceOfInhabitableLoft);
        assertEquals(house.getExistanceOfBasement(), dto.get().existanceOfBasement);

        // invalid null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid
        ErrorOptional<House> domain = mapper.toDomain(houseDTO);
        assertFalse(domain.hasError());

        // all atributes must be equal
        assertEquals(houseDTO.sunExposure, domain.get().getSunExposure());
        assertEquals(houseDTO.existanceOfInhabitableLoft, domain.get().getExistanceOfInhabitableLoft());
        assertEquals(houseDTO.existanceOfBasement, domain.get().getExistanceOfBasement());

        // invalid - null
        domain = mapper.toDomain(null);
        assert (domain.hasError());
    }
}