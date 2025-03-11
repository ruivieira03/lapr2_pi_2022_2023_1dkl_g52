package pt.ipp.isep.dei.esoft.project.domain.property.mappers;

import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.CityMapper;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.DistrictMapper;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.StateMapper;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.property.ResidentialProperty;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.LandDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.PropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ResidentialPropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.UserMapper;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;
import pt.ipp.isep.dei.esoft.project.repository.ClientRepository;
import pt.ipp.isep.dei.esoft.project.repository.LocationsRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.io.Serializable;
import java.util.Optional;

/**
 * The type Property mapper.
 */
public class PropertyMapper implements Serializable {

    private final transient StateMapper stateMapper = new StateMapper();
    private final transient DistrictMapper districtMapper = new DistrictMapper();
    private final transient CityMapper cityMapper = new CityMapper();
    private final transient UserMapper userMapper = new UserMapper();
    private final transient ResidentialPropertyMapper residentialPropertyMapper = new ResidentialPropertyMapper();
    private final transient LandMapper landMapper = new LandMapper();
    private final Repositories repositories = Repositories.getInstance();

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param property the property
     * @return the error optional
     */
    public ErrorOptional<PropertyDTO> toDTO(Property property) {
        if (property == null) return ErrorOptional.empty("Error - Mapper - Property cannot be null!");

        ErrorOptional<? extends PropertyDTO> errorOptional;
        if (property instanceof Land) errorOptional = landMapper.toDTO((Land) property);
        else if (property instanceof ResidentialProperty)
            errorOptional = residentialPropertyMapper.toDTO((ResidentialProperty) property);
        else return ErrorOptional.empty("Error - Mapper - Invalid Property type!");

        PropertyDTO dto = errorOptional.get();

        ErrorOptional<StateDTO> state = stateMapper.toDTO(property.getState());
        ErrorOptional<DistrictDTO> district = districtMapper.toDTO(property.getDistrict());
        ErrorOptional<CityDTO> city = cityMapper.toDTO(property.getCity());
        ErrorOptional<UserDTO> client = userMapper.toDTO(property.getClient());

        if (state.hasError()) return ErrorOptional.empty(state.getErrorMessage());
        if (district.hasError()) return ErrorOptional.empty(district.getErrorMessage());
        if (city.hasError()) return ErrorOptional.empty(city.getErrorMessage());
        if (client.hasError()) return ErrorOptional.empty(client.getErrorMessage());

        dto.area = property.getArea();
        dto.distanceFromCityCenter = property.getDistanceFromCityCentre();
        dto.street = property.getStreet();
        dto.zipCode = property.getZipCode();
        dto.photos = property.getPhotos();
        dto.state = state.get();
        dto.district = district.get();
        dto.city = city.get();
        dto.client = (ClientDTO) client.get();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<Property> toDomain(PropertyDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - PropertyDTO cannot be null!");

        LocationsRepository locationsRepository = Repositories.getLocationsRepository();
        ClientRepository clientRepository = Repositories.getClientRepository();

        ErrorOptional<? extends Property> errorOptional;

        if (dto instanceof ResidentialPropertyDTO)
            errorOptional = residentialPropertyMapper.toDomain((ResidentialPropertyDTO) dto);
        else if (dto instanceof LandDTO) errorOptional = landMapper.toDomain((LandDTO) dto);
        else return ErrorOptional.empty("Error - Mapper - Invalid Property type!");

        if (errorOptional.hasError()) return ErrorOptional.empty(errorOptional.getErrorMessage());


        Property property = errorOptional.get();

        Optional<State> state = locationsRepository.getStateByName(dto.state.name);
        if (state.isEmpty())
            return ErrorOptional.empty("Error - Mapper - State[" + dto.state.name + "] does not exist in the repository!");

        Optional<District> district = locationsRepository.getDistrictByName(state.get(), dto.district.name);
        if (district.isEmpty())
            return ErrorOptional.empty("Error - Mapper - State[" + state.get().getName() + "] does not contain Disitrict[" + dto.district.name + "]!");

        Optional<City> city = locationsRepository.getCityByName(district.get(), dto.city.name);
        if (city.isEmpty())
            return ErrorOptional.empty("Error - Mapper - District[" + district.get().getName() + "] does not contain City[" + dto.city.name + "]");

        ErrorOptional<Client> client = clientRepository.getClientFromEmail(dto.client.email);
        if (client.hasError())
            return ErrorOptional.empty(client.getErrorMessage() + "\nError - Mapper - Client[" + dto.client.email.toString() + "] does not exist in the repository!");


        property.setArea(dto.area);
        property.setDistanceFromCityCentre(dto.distanceFromCityCenter);
        property.setStreet(dto.street);
        property.setZipCode(dto.zipCode);
        property.setState(state.get());
        property.setDistrict(district.get());
        property.setCity(city.get());
        property.setPhotos(dto.photos);
        property.setClient(client.get());

        return ErrorOptional.of(property);
    }
}
