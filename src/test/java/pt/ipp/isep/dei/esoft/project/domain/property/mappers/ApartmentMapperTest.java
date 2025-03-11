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
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ApartmentDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ApartmentMapperTest {

    private Apartment apartment;
    private ApartmentDTO apartmentDTO;
    private final ApartmentMapper mapper = new ApartmentMapper();

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


        // -----DTO------
        // locations
        StateDTO stateDTO = new StateDTO("State", new String[]{"District"}, new String[][]{{"City"}});
        DistrictDTO districtDTO = stateDTO.districtList.get(0);
        CityDTO cityDTO = districtDTO.cityList.get(0);

        // owner
        ClientDTO clientDTO = new ClientDTO("name", 123456789, "123-123-1234", "123-12-1234", "", "ex@ex.com");

        apartmentDTO = new ApartmentDTO(1000, 1.0, "Street", 12345, stateDTO, districtDTO, cityDTO, "p", clientDTO, 1, 2, 3, "Avaliable Equipment");

    }

    @Test
    void toDTO() {
        // valid
        ErrorOptional<ApartmentDTO> dto = mapper.toDTO(apartment);
        assertFalse(dto.hasError());

        // invalid - null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid
        ErrorOptional<Apartment> domain = mapper.toDomain(apartmentDTO);
        assertFalse(domain.hasError());

        // invalid - null
        domain = mapper.toDomain(null);
        assert (domain.hasError());
    }
}