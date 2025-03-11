package pt.ipp.isep.dei.esoft.project.domain.store.mappers;

import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.employee.StoreManager;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.StoreManagerDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.CityMapper;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.DistrictMapper;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.StateMapper;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.Store;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.User;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.UserMapper;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;
import pt.ipp.isep.dei.esoft.project.repository.LocationsRepository;
import pt.ipp.isep.dei.esoft.project.repository.PropertySaleRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The StoreMapper class is responsible for mapping between Store and StoreDTO objects.
 * It provides methods for converting Store objects to StoreDTO objects and vice versa.
 */

public class StoreMapper {

    private final transient UserMapper userMapper = new UserMapper();
    private final transient StateMapper stateMapper = new StateMapper();
    private final transient DistrictMapper districtMapper = new DistrictMapper();
    private final transient CityMapper cityMapper = new CityMapper();

    private final Repositories repositories = Repositories.getInstance();

    /**
     * Instantiates a new Store mapper.
     */
    public StoreMapper() {

    }

    /**
     * Converts a Store object to a StoreDTO object.
     *
     * @param store the Store object to be converted.
     * @return the converted StoreDTO object, wrapped in an ErrorOptional.
     * If the conversion fails, an empty ErrorOptional with an error message is returned.
     */
    public ErrorOptional<StoreDTO> toDTO(Store store) {

        PropertySaleRepository propertySaleRepository = Repositories.getPropertySaleRepository();

        if (store == null) return ErrorOptional.empty("Error - Mapper - Store cannot be null!");

        StoreDTO dto = new StoreDTO();

        dto.agents = new ArrayList<>();
        ErrorOptional<UserDTO> userErrorOptional;
        for (Agent agent : store.getAgents().get()) {
            userErrorOptional = userMapper.toDTO(agent);
            if (userErrorOptional.hasError()) return ErrorOptional.empty(userErrorOptional.getErrorMessage());
            if (!(userErrorOptional.get() instanceof AgentDTO)) {
                return ErrorOptional.empty("Error - Mapper - UserMapper did not return an Agent as expected!");
            }
            dto.agents.add((AgentDTO) userErrorOptional.get());
        }
        if (store.getStoreManager() != null) {
            userErrorOptional = userMapper.toDTO(store.getStoreManager());
            if (userErrorOptional.hasError()) return ErrorOptional.empty(userErrorOptional.getErrorMessage());
            if (!(userErrorOptional.get() instanceof StoreManagerDTO)) {
                return ErrorOptional.empty("Error - Mapper - UserMapper did not return an StoreManager as expected!");
            }
            dto.storeManager = (StoreManagerDTO) userErrorOptional.get();
        } else dto.storeManager = null;

        ErrorOptional<StateDTO> state = stateMapper.toDTO(store.getState());
        if (state.hasError()) return ErrorOptional.empty(state.getErrorMessage());

        ErrorOptional<DistrictDTO> district = districtMapper.toDTO(store.getDistrict());
        if (district.hasError()) return ErrorOptional.empty(district.getErrorMessage());

        ErrorOptional<CityDTO> city = cityMapper.toDTO(store.getCity());
        if (city.hasError()) return ErrorOptional.empty(city.getErrorMessage());

        dto.id = store.getId();
        dto.name = store.getName();
        dto.email = store.getEmail();
        dto.state = state.get();
        dto.district = district.get();
        dto.city = city.get();
        dto.propertyCount = store.getPropertyCount();

        return ErrorOptional.of(dto);
    }


    /**
     * Converts a StoreDTO object to a Store object.
     *
     * @param dto the StoreDTO object to be converted.
     * @return the converted Store object, wrapped in an ErrorOptional.
     * If the conversion fails, an empty ErrorOptional with an error message is returned.
     */
    public ErrorOptional<Store> toDomain(StoreDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - StoreDTO cannot be null!");

        LocationsRepository locationsRepository = Repositories.getLocationsRepository();

        Store store = new Store();

        store.setName(dto.name);
        store.setEmail(dto.email.toString());
        store.setTelephoneNumber(dto.telephoneNumber);
        store.setAddress(dto.adress);
        store.setZipCode(dto.zipCode);

        Optional<State> state = locationsRepository.getStateByName(dto.state.name);
        if (state.isEmpty())
            return ErrorOptional.empty("Error - Mapper - LocationsRepository does not contain State[" + dto.state.name + "]");
        store.setState(state.get());

        Optional<District> district = locationsRepository.getDistrictByName(state.get(), dto.district.name);
        if (district.isEmpty())
            return ErrorOptional.empty("Error - Mapper - State[" + state.get() + "] does not contain District[" + dto.district.name + "]");
        store.setDistrict(district.get());

        Optional<City> city = locationsRepository.getCityByName(district.get(), dto.city.name);
        if (city.isEmpty())
            return ErrorOptional.empty("Error - Mapper - District[" + district.get() + "] does not contain City[" + dto.city.name + "]");
        store.setCity(city.get());


        ErrorOptional<User> userErrorOptional;

        List<Agent> agentList = new ArrayList<>();
        if (dto.agents != null || dto.agents.size() != 0) {

            for (AgentDTO agentDTO : dto.agents) {
                userErrorOptional = userMapper.toDomain(agentDTO);
                if (userErrorOptional.hasError()) return ErrorOptional.empty(userErrorOptional.getErrorMessage());
                agentList.add((Agent) userErrorOptional.get());
            }
        }
        store.setAgents(agentList);


        if (dto.storeManager == null) store.setStoreManager(null);
        else {
            userErrorOptional = userMapper.toDomain(dto.storeManager);
            if (userErrorOptional.hasError()) return ErrorOptional.empty(userErrorOptional.getErrorMessage());
            store.setStoreManager((StoreManager) userErrorOptional.get());
        }

        return ErrorOptional.of(store);
    }
}
