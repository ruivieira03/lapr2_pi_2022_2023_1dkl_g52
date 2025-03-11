package pt.ipp.isep.dei.esoft.project.domain.locations.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DistrictMapperTest {

    private District district;
    private DistrictDTO districtDTO;
    private final DistrictMapper mapper = new DistrictMapper();

    @BeforeEach
    void beforeEach() {
        // -----Domain-----
        district = new District();
        district.setName("District");

        City city = new City();
        city.setName("City");

        List<City> cityList = new ArrayList<>();
        cityList.add(city);
        district.setCities(cityList);

        // -----DTO-----
        districtDTO = new DistrictDTO("District", new String[]{"City"});
    }

    @Test
    void toDTO() {
        // valid
        ErrorOptional<DistrictDTO> dto = mapper.toDTO(district);
        assertFalse(dto.hasError());
        // all atributes must be equal
        assertEquals (dto.get().name, district.getName());
        assertEquals(dto.get().cityList.size(), dto.get().cityList.size());

        // invalid - null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid
        ErrorOptional<District> domain = mapper.toDomain(districtDTO);
        assertFalse(domain.hasError());
        // all atributes must be the same
        assertEquals(domain.get().getName(), districtDTO.name);
        assertEquals(domain.get().getCities().size(), districtDTO.cityList.size());

        // invalid - null
        domain = mapper.toDomain(null);
        assert (domain.hasError());
    }
}