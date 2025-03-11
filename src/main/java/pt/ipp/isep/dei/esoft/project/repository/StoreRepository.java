package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.employee.NetworkManager;
import pt.ipp.isep.dei.esoft.project.domain.employee.StoreManager;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.EmployeeDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.Store;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.StoreMapper;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.User;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.UserMapper;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Used to store Branches
 */
public class StoreRepository implements Serializable {
    /**
     * The Stores.
     */
    List<Store> stores = new ArrayList<>();

    /**
     * The network manager responsible for managing the network of stores.
     */
    NetworkManager networkManager;

    /**
     * Mapper for converting Store objects to StoreDTO objects and vice versa.
     */
    private transient StoreMapper mapper = new StoreMapper();

    /**
     * Mapper for converting User objects to UserDTO objects and vice versa.
     */
    private transient UserMapper userMapper = new UserMapper();


    /**
     * Returns a list of AgentDTO objects representing all the agents in the repository.
     *
     * @return List of AgentDTO objects
     */
    public List<AgentDTO> getAgentList() {
        List<AgentDTO> agents = new ArrayList<>();

        for (Store store : stores) {
            for (Agent Agent : store.getAgents().get()) {
                ErrorOptional<UserDTO> user = userMapper.toDTO(Agent);
                if (!user.hasError()) agents.add((AgentDTO) user.get());
            }
        }
        return agents;
    }

    /**
     * Returns an Agent object from the given email.
     *
     * @param email the email of the agent
     * @return ErrorOptional containing the Agent object if found, or an empty ErrorOptional if not found
     */


    public ErrorOptional<Agent> getAgentFromEmail(String email) {
        ErrorOptional<Agent> agent = ErrorOptional.empty("");

        for (Store store : stores) {
            agent = store.getAgentFromEmail(email);
            if (!agent.hasError()) return agent;
        }

        return ErrorOptional.empty(agent.getErrorMessage() + "\nError - StoreNetworkRepository does not contain Agent[" + email + "]");
    }

    /**
     * Returns a Store object from the given StoreDTO object.
     *
     * @param dto the StoreDTO object
     * @return ErrorOptional containing the Store object if found, or an empty ErrorOptional if not found
     */
    public ErrorOptional<Store> getStore(StoreDTO dto) {
        for (Store store : stores)
            if (store.getId() == dto.id) return ErrorOptional.of(store);

        return ErrorOptional.empty("Error - Repository - Repository does not contain Store[" + dto.id + "]!");
    }

    /**
     * Gets agent dto from email.
     *
     * @param email the email
     * @return the agent dto from email
     */
    public ErrorOptional<AgentDTO> getAgentDTOFromEmail(String email) {
        ErrorOptional<Agent> agent = getAgentFromEmail(email);

        if (agent.hasError()) return ErrorOptional.empty(agent.getErrorMessage());

        ErrorOptional<UserDTO> user = userMapper.toDTO(agent.get());
        if (user.hasError()) return ErrorOptional.empty(user.getErrorMessage());

        return ErrorOptional.of((AgentDTO) user.get());
    }

    /**
     * Creates a new store based on the given StoreDTO object.
     *
     * @param dto the StoreDTO object
     * @return OperationResult indicating the outcome of the operation
     */
    public OperationResult createStore(StoreDTO dto) {
        ErrorOptional<Store> storeNetwork = mapper.toDomain(dto);

        if (storeNetwork.hasError())
            ErrorOptional.empty(storeNetwork.getErrorMessage() + "\nError - Repository - Failed to convert StoreNetworkDTO into StoreNetwork!");

        return addStore(storeNetwork.get());
    }

    /**
     * Adds a store to the repository.
     *
     * @param store the Store object to add
     * @return OperationResult indicating the outcome of the operation
     */
    private OperationResult addStore(Store store) {
        OperationResult localValidation = store.isValid();

        if (!localValidation.success())
            return OperationResult.failed(localValidation.getErrorMessage() + "\nError - Repository - StoreNetwork failed local validation!");
        if (!isValid(store))
            return OperationResult.failed("Error - Repository - StoreNetwork failed global validation!");

        stores.add(store);
        return OperationResult.successfull();
    }

    /**
     * Checks if a store is valid and can be added to the repository.
     *
     * @param store the Store object to validate
     * @return true if the store is valid, false otherwise
     */
    public boolean isValid(Store store) {
        for (Store store1 : stores) {
            if (store1.equals(store)) return false;
        }

        return true;
    }

    /**
     * Registers an employee (Agent, StoreManager, or NetworkManager) to a store based on the employee's UserDTO and the store's StoreDTO.
     *
     * @param employeeDTO the EmployeeDTO object representing the employee
     * @param storeDTO    the StoreDTO object representing the store
     * @return OperationResult indicating the outcome of the operation
     */

    public OperationResult registerEmployee(EmployeeDTO employeeDTO, StoreDTO storeDTO) {
        ErrorOptional<User> user = userMapper.toDomain(employeeDTO);

        if (user.hasError())
            return OperationResult.failed(user.getErrorMessage() + "\nError - Repository - Failed to initiate User!");

        if (user.get() instanceof Agent) return registerAgent((Agent) user.get(), storeDTO);
        else if (user.get() instanceof StoreManager) return registerStoreManager((StoreManager) user.get(), storeDTO);
        else if (user.get() instanceof NetworkManager) return registerNetworkManager((NetworkManager) user.get());

        else return OperationResult.failed("Error - Repository - Invalid User type!");
    }

    /**
     * Registers an Agent to a store based on the agent and store information.
     *
     * @param agent    the Agent object to register
     * @param storeDTO the StoreDTO object representing the store
     * @return OperationResult indicating the outcome of the operation
     */

    private OperationResult registerAgent(Agent agent, StoreDTO storeDTO) {
        ErrorOptional<Store> store = getStore(storeDTO);
        if (store.hasError()) return OperationResult.failed(store.getErrorMessage() + "\nError - Repository - Failed ");

        return store.get().registerAgent(agent);
    }

    /**
     * Registers a StoreManager to a store based on the store manager and store information.
     *
     * @param storeManager the StoreManager object to register
     * @param storeDTO     the StoreDTO object representing the store
     * @return OperationResult indicating the outcome of the operation
     */
    private OperationResult registerStoreManager(StoreManager storeManager, StoreDTO storeDTO) {

        ErrorOptional<Store> store = getStore(storeDTO);
        if (store.hasError()) return OperationResult.failed(store.getErrorMessage() + "\nError - Repository - Failed ");

        return store.get().registerStoreManager(storeManager);
    }

    /**
     * Registers a NetworkManager to the store network.
     *
     * @param networkManager the NetworkManager object to register
     * @return OperationResult indicating the outcome of the operation
     */

    private OperationResult registerNetworkManager(NetworkManager networkManager) {
        if (this.networkManager != null) {
            return OperationResult.failed("Error - Repository - NetworkManager already registered!");
        }

        if (networkManager.getEmail() == null || networkManager.getEmail().isEmpty()) {
            return OperationResult.failed("Error - Repository - Invalid email for NetworkManager!");
        }
        this.networkManager = networkManager;

        return OperationResult.successfull();
    }

    /**
     * Returns a sorted list of StoreDTO objects based on the number of properties in each store.
     *
     * @return List of StoreDTO objects sorted by property count
     */

    public List<StoreDTO> getSortedStoreList() {

        PropertySaleRepository propertySaleRepository = Repositories.getPropertySaleRepository();

        List<Store> copy = new ArrayList<>(stores);

        SortByPropertyCount c = new SortByPropertyCount();

        copy.sort(c);

        List<StoreDTO> storeDTOS = new ArrayList<>();

        for (Store store : copy) {
            ErrorOptional<StoreDTO> storeDTO = mapper.toDTO(store);
            if (!storeDTO.hasError()) {
                storeDTOS.add(storeDTO.get());
            }
        }


        return storeDTOS;
    }

    /**
     * Returns a list of AgentDTO objects representing the agents in the specified store.
     *
     * @param storeDTO the StoreDTO object representing the store
     * @return List of AgentDTO objects for the specified store
     */

    public List<AgentDTO> getAgentListFromStore(StoreDTO storeDTO) {
        ErrorOptional<Store> store = getStore(storeDTO);
        if (store.hasError())
            return null;


        List<AgentDTO> list = new ArrayList<>();
        for (Agent agent : store.get().getAgents().get()) {
            ErrorOptional<UserDTO> user = userMapper.toDTO(agent);
            if (!user.hasError())
                list.add((AgentDTO) user.get());
        }

        return list;

    }


    public static class SortByPropertyCount implements Comparator<Store> {

        @Override
        public int compare(Store store, Store t1) {
            return store.getPropertyCount() - t1.getPropertyCount();
        }
    }

    /**
     * Call this when this class is desirialized
     */
    public void deserialize() {
        mapper = new StoreMapper();
        userMapper = new UserMapper();
        networkManager = new NetworkManager();
    }

    /**
     * Comparator class for sorting stores based on property count.
     */
    public void addStorePropertyCount(String agentEmail) {
        for (Store store : stores) {
            for (Agent agent : store.getAgents().get()) {
                if (agent.hasEmail(agentEmail)) store.addPropertyCount();
            }
        }
    }

    /**
     * Add store property count.
     *
     * @param storeDTO the store dto
     */
    public void addStorePropertyCount(StoreDTO storeDTO) {
        for (Store store : stores) {
            if (store.getEmail().equals(storeDTO.email)) store.addPropertyCount();
        }
    }
}


