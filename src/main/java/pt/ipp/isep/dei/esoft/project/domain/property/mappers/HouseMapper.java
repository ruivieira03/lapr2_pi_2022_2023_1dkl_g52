package pt.ipp.isep.dei.esoft.project.domain.property.mappers;

import pt.ipp.isep.dei.esoft.project.domain.property.House;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type House mapper.
 */
public class HouseMapper {

    /**
     * Instantiates a new House mapper.
     */
    public HouseMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param house the house
     * @return the error optional
     */
    public ErrorOptional<HouseDTO> toDTO(House house) {
        if (house == null) return ErrorOptional.empty("Error - Mapper - House cannot be null!");

        HouseDTO dto = new HouseDTO();

        dto.existanceOfBasement = house.getExistanceOfBasement();
        dto.existanceOfInhabitableLoft = house.getExistanceOfInhabitableLoft();
        dto.sunExposure = house.getSunExposure();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<House> toDomain(HouseDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - HouseDTO cannot be null!");

        House house = new House();

        house.setExistanceOfBasement(dto.existanceOfBasement);
        house.setExistanceOfInhabitableLoft(dto.existanceOfInhabitableLoft);
        house.setSunExposure(dto.sunExposure);

        return ErrorOptional.of(house);
    }
}

