package pt.ipp.isep.dei.esoft.project.domain.property.mappers;

import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ApartmentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ResidentialPropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.property.House;
import pt.ipp.isep.dei.esoft.project.domain.property.ResidentialProperty;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type Residential property mapper.
 */
public class ResidentialPropertyMapper {

    private final ApartmentMapper apartmentMapper = new ApartmentMapper();
    private final HouseMapper houseMapper = new HouseMapper();

    /**
     * Instantiates a new Residential property mapper.
     */
    public ResidentialPropertyMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param residentialProperty the residential property
     * @return the error optional
     */
    public ErrorOptional<ResidentialPropertyDTO> toDTO(ResidentialProperty residentialProperty) {
        if (residentialProperty == null)
            return ErrorOptional.empty("Error - Mapper - Residential Property cannot be null!");

        ErrorOptional<? extends ResidentialPropertyDTO> errorOptional;

        if (residentialProperty instanceof Apartment)
            errorOptional = apartmentMapper.toDTO((Apartment) residentialProperty);
        else if (residentialProperty instanceof House) errorOptional = houseMapper.toDTO((House) residentialProperty);
        else return ErrorOptional.empty("Error - Mapper - Invalid ResidentialProperty type!");

        ResidentialPropertyDTO dto = errorOptional.get();

        dto.numberOfBedrooms = residentialProperty.getNumberOfBedrooms();
        dto.numberOfBathrooms = residentialProperty.getNumberOfBathrooms();
        dto.numberOfParkingSpaces = residentialProperty.getNumberOfParkingSpaces();
        dto.availableEquipment = residentialProperty.getAvailableEquipment();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<ResidentialProperty> toDomain(ResidentialPropertyDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - ResidentialPropertyDTO cannot be null!");

        ErrorOptional<? extends ResidentialProperty> propertyErrorOptional;

        if (dto instanceof ApartmentDTO) propertyErrorOptional = apartmentMapper.toDomain((ApartmentDTO) dto);
        else if (dto instanceof HouseDTO) propertyErrorOptional = houseMapper.toDomain((HouseDTO) dto);
        else return ErrorOptional.empty("Error - Mapper - Invalid ResidentialPropertyDTO type!");

        if (propertyErrorOptional.hasError()) return ErrorOptional.empty(propertyErrorOptional.getErrorMessage());

        ResidentialProperty residentialProperty = propertyErrorOptional.get();

        residentialProperty.setNumberOfBedrooms(dto.numberOfBedrooms);
        residentialProperty.setNumberOfBathrooms(dto.numberOfBathrooms);
        residentialProperty.setNumberOfParkingSpaces(dto.numberOfParkingSpaces);
        residentialProperty.setAvailableEquipment(dto.availableEquipment);

        return ErrorOptional.of(residentialProperty);
    }
}
