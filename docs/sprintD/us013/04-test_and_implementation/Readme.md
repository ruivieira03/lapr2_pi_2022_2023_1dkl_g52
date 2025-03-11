# US 013 - List All Employees

# 4. Tests

**Test 1:** Check that it is not possible to create an instance of the Task class with null values.

	@Test
    



*It is also recommended to organize this content by subsections.*

# 5. Construction (Implementation)


## Class ListEmployeeController

```java
public class ListEmployeeController {

    private static Repositories repositories;
    public ListEmployeeController(){
        repositories = Repositories.getInstance();
    }

    public List<StoreDTO> listAllEmployees(){
        StoreRepository storeRepository = Repositories.getStoreRepository();

        return storeRepository.getSortedStoreList();
    }

}

```

## Class StoreRepository

```java
public class StoreRepository implements Serializable {
    List<Store> stores = new ArrayList<>();

    NetworkManager networkManager;

    private transient StoreMapper mapper = new StoreMapper();
    private transient UserMapper userMapper = new UserMapper();


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


    public ErrorOptional<Agent> getAgentFromEmail(String email) {
        ErrorOptional<Agent> agent = ErrorOptional.empty("");

        for (Store store : stores) {
            agent = store.getAgentFromEmail(email);
            if (!agent.hasError()) return agent;
        }

        return ErrorOptional.empty(agent.getErrorMessage() + "\nError - StoreNetworkRepository does not contain Agent[" + email + "]");
    }

    public ErrorOptional<Store> getStore(StoreDTO dto) {
        for (Store store : stores)
            if (store.getId() == dto.id) return ErrorOptional.of(store);

        return ErrorOptional.empty("Error - Repository - Repository does not contain Store[" + dto.id + "]!");
    }

    public ErrorOptional<AgentDTO> getAgentDTOFromEmail(String email) {
        ErrorOptional<Agent> agent = getAgentFromEmail(email);

        if (agent.hasError()) return ErrorOptional.empty(agent.getErrorMessage());

        ErrorOptional<UserDTO> user = userMapper.toDTO(agent.get());
        if (user.hasError()) return ErrorOptional.empty(user.getErrorMessage());

        return ErrorOptional.of((AgentDTO) user.get());
    }


    public OperationResult createStore(StoreDTO dto) {
        ErrorOptional<Store> storeNetwork = mapper.toDomain(dto);

        if (storeNetwork.hasError())
            ErrorOptional.empty(storeNetwork.getErrorMessage() + "\nError - Repository - Failed to convert StoreNetworkDTO into StoreNetwork!");

        return addStore(storeNetwork.get());
    }

    private OperationResult addStore(Store store) {
        OperationResult localValidation = store.isValid();

        if (!localValidation.success())
            return OperationResult.failed(localValidation.getErrorMessage() + "\nError - Repository - StoreNetwork failed local validation!");
        if (!isValid(store))
            return OperationResult.failed("Error - Repository - StoreNetwork failed global validation!");

        stores.add(store);
        return OperationResult.successfull();
    }

    public boolean isValid(Store store) {
        for (Store store1 : stores) {
            if (store1.equals(store)) return false;
        }

        return true;
    }


    public OperationResult registerEmployee(EmployeeDTO employeeDTO, StoreDTO storeDTO) {
        ErrorOptional<User> user = userMapper.toDomain(employeeDTO);

        if (user.hasError())
            return OperationResult.failed(user.getErrorMessage() + "\nError - Repository - Failed to initiate User!");

        if (user.get() instanceof Agent) return registerAgent((Agent) user.get(), storeDTO);
        else if (user.get() instanceof StoreManager) return registerStoreManager((StoreManager) user.get(), storeDTO);
        else if (user.get() instanceof NetworkManager) return registerNetworkManager((NetworkManager) user.get());

        else return OperationResult.failed("Error - Repository - Invalid User type!");
    }

    private OperationResult registerAgent(Agent agent, StoreDTO storeDTO) {
        ErrorOptional<Store> store = getStore(storeDTO);
        if (store.hasError()) return OperationResult.failed(store.getErrorMessage() + "\nError - Repository - Failed ");

        return store.get().registerAgent(agent);
    }

    private OperationResult registerStoreManager(StoreManager storeManager, StoreDTO storeDTO) {

        ErrorOptional<Store> store = getStore(storeDTO);
        if (store.hasError()) return OperationResult.failed(store.getErrorMessage() + "\nError - Repository - Failed ");

        return store.get().registerStoreManager(storeManager);
    }

    private OperationResult registerNetworkManager(NetworkManager networkManager) {
        // FIXME: do validations
        this.networkManager = networkManager;

        return OperationResult.successfull();
    }

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

    public List<AgentDTO> getAgentListFromStore(StoreDTO storeDTO) {
        ErrorOptional<Store> store = getStore(storeDTO);
        if (store.hasError()) return null;


        List<AgentDTO> list = new ArrayList<>();
        for (Agent agent : store.get().getAgents().get()) {
            ErrorOptional<UserDTO> user = userMapper.toDTO(agent);
            if (!user.hasError()) list.add((AgentDTO) user.get());
        }

        return list;

    }

    public void deserialize() {
        mapper = new StoreMapper();
        userMapper = new UserMapper();
        networkManager = new NetworkManager();
    }

    public void addStorePropertyCount(String agentEmail) {
        for (Store store : stores) {
            for (Agent agent : store.getAgents().get()) {
                if (agent.hasEmail(agentEmail)) store.addPropertyCount();
            }
        }
    }

    public void addStorePropertyCount(StoreDTO storeDTO) {
        for (Store store : stores) {
            if (store.getEmail().equals(storeDTO.email)) store.addPropertyCount();
        }
    }


    public static class SortByPropertyCount implements Comparator<Store> {

        @Override
        public int compare(Store store, Store t1) {
            return store.getPropertyCount() - t1.getPropertyCount();
        }
    }
}

```


## Class Store
````java
public class Store implements Serializable {
    private List<Agent> agents;
    private StoreManager storeManager;

    private int id;
    private String name;
    private String email;
    private String telephoneNumber;
    private String address;
    private int zipCode;
    private State state;
    private District district;
    private City city;
    private int propertyCount;


    /**
     * method to initiate a new store
     */
    public Store() {
        this.agents = new ArrayList<>();
        this.storeManager = null;
        this.propertyCount = 0;
    }


    /**
     * method to get an agent of the store by his email
     *
     * @param email email
     * @return agent
     */
    public ErrorOptional<Agent> getAgentFromEmail(String email) {
        Optional<Agent> agentReturn = Optional.empty();

        for (Agent agent : agents) {
            if (agent.hasEmail(email)) {
                return ErrorOptional.of(agent);
            }
        }

        return ErrorOptional.empty("Error - Store - Store[" + this.id + "] does not contain Agent[" + email + "]");

    }



    /**
     * method to get the agents from a store
     *
     * @return agents
     */
    public ErrorOptional<List<Agent>> getAgents() {

        return ErrorOptional.of(agents);
    }

    /**
     * method to change the agents of a store
     *
     * @param agents agents
     */
    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    /**
     * method to get the manager of a store
     *
     * @return agents
     */
    public StoreManager getStoreManager() {
        return storeManager;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public State getState() {
        return state;
    }

    public District getDistrict() {
        return district;
    }

    public City getCity() {
        return city;
    }

    public int getPropertyCount() {
        return propertyCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setPropertyCount(int propertyCount) {
        this.propertyCount = propertyCount;
    }

    /**
     * method to change the manager of a store
     *
     * @param storeManager storeManager
     */
    public void setStoreManager(StoreManager storeManager) {
        this.storeManager = storeManager;
    }

    public void addPropertyCount() {
        this.propertyCount++;
    }


    /**
     * method to add an agent
     *
     * @param agent
     * @return
     */
    private OperationResult addAgent(Agent agent) {
        if (!agent.isValid().success()) return agent.isValid();
        if (!isAgentValid(agent).success()) return isAgentValid(agent);

        agents.add(agent);
        return OperationResult.successfull();
    }

    /**
     * method to verify if agent doesn't already exist
     *
     * @param agent
     * @return
     */

    private OperationResult isAgentValid(Agent agent) {
        for (Agent a : agents) {
            if (a.equals(agent))
                return OperationResult.failed("Error - Store - Agent already exists!");
        }

        return OperationResult.successfull();
    }


    /**
     * method to add the recently created Store Manager
     *
     * @param storeManager
     * @return
     */

    private OperationResult addStoreManager(StoreManager storeManager) {
        if (!storeManager.isValid().success()) return storeManager.isValid();
        if (!isStoreManagerValid(storeManager).success()) return isStoreManagerValid(storeManager);

        this.storeManager = storeManager;
        return OperationResult.successfull();
    }

    /**
     * method to verify if recently added store manager doesn't already exist
     *
     * @param storeManager
     * @return
     */

    private OperationResult isStoreManagerValid(StoreManager storeManager) {
        if (!this.storeManager.equals(storeManager))
            return OperationResult.successfull();

        return OperationResult.failed("Error - Store - StoreMAnager already registered!");
    }

    public OperationResult registerAgent(Agent agent) {
        if (!agent.isValid().success()) return OperationResult.failed("Error - Store - Failed local validation!");
        if (!isAgentValid(agent).success()) return OperationResult.failed("Error - Store - Failed global validation!");

        agents.add(agent);
        return OperationResult.successfull();
    }

    public OperationResult registerStoreManager(StoreManager storeManager) {
        if (!storeManager.isValid().success())
            return OperationResult.failed("Error - Store - Failed local validation!");

        this.storeManager = storeManager;
        return OperationResult.successfull();
    }

    public OperationResult isValid() {
        // TODO: implement method
        return OperationResult.successfull();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj.getClass() == this.getClass())) return false;

        Store other = (Store) obj;

        return other.email.equals(this.email);
    }
}


````

# 6. Integration and Demo


# 7. Observations


