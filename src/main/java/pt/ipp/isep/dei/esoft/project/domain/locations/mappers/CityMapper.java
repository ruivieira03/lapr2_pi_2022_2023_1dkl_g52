package pt.ipp.isep.dei.esoft.project.domain.locations.mappers;

import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type City mapper.
 */
public class CityMapper {

    /**
     * Instantiates a new City mapper.
     */
    public CityMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param city the city
     * @return the error optional
     */
    public ErrorOptional<CityDTO> toDTO(City city) {
        if (city == null) return ErrorOptional.empty("Error - Mapper - City cannot be null!");

        CityDTO dto = new CityDTO();

        dto.name = city.getName();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<City> toDomain(CityDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - CityDTO cannot be null!");

        City city = new City();

        city.setName(dto.name);

        return ErrorOptional.of(city);
    }
}
