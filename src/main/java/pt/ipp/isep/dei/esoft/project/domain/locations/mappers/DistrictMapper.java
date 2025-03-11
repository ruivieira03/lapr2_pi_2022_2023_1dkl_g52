package pt.ipp.isep.dei.esoft.project.domain.locations.mappers;

import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.ArrayList;
import java.util.List;

/**
 * The type District mapper.
 */
public class DistrictMapper {

    private final transient CityMapper cityMapper = new CityMapper();

    /**
     * Instantiates a new District mapper.
     */
    public DistrictMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param district the district
     * @return the error optional
     */
    public ErrorOptional<DistrictDTO> toDTO(District district) {
        if (district == null) return ErrorOptional.empty("Error - Mapper - District cannot be null!");

        DistrictDTO dto = new DistrictDTO();

        dto.name = district.getName();

        dto.cityList = new ArrayList<>();
        ErrorOptional<CityDTO> errorOptional;
        for (City city : district.getCities()) {
            errorOptional = cityMapper.toDTO(city);
            if (errorOptional.hasError()) return ErrorOptional.empty(errorOptional.getErrorMessage());
            dto.cityList.add(errorOptional.get());
        }

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<District> toDomain(DistrictDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper DistrictDTO cannot be null!");

        District district = new District();

        district.setName(dto.name);

        List<City> cityList = new ArrayList<>();
        for (CityDTO cityDTO : dto.cityList) {
            ErrorOptional<City> errorOptional;
            errorOptional = cityMapper.toDomain(cityDTO);
            if (errorOptional.hasError()) return ErrorOptional.empty(errorOptional.getErrorMessage());
            cityList.add(errorOptional.get());
        }

        district.setCities(cityList);

        return ErrorOptional.of(district);
    }
}
