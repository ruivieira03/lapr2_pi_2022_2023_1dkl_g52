# US 004 - To Submit a request to list a property

# 4. Tests

**Test 1:** Check that it is not possible to create an instance of the Task class with null values.

	@Test
    void createPropertySaleRequest() {

        State state = new State("state", new String[]{"District"}, new String[][]{{"City"}});
        District district = state.getDistricts().get(0);
        City city = district.getCities().get(0);
        Client client = new Client("name", 123, 123, 123, "adress", "email@ex.com");
        Agent agent = new Agent("name", 123, 123, "123", "agent@ex.com", 123123123);

        // fail local validation
        assert(!propertySaleRequestRepository.createPropertySaleRequest(PropertySaleRequest.TypesOfBusinesses.RENT, Property.Type.HOUSE,
                -1, 10, 10000, state, district, city, "adress", "123", "photo",
                client, agent, 1, 1, 1, "none",
                true, true, House.SunExposure.SOUTH));



*It is also recommended to organize this content by subsections.*

# 5. Construction (Implementation)


## Class CreatePropertySaleRequestController

```java
public class CreatePropertySaleRequestController {
    private static Repositories repositories;

    public CreatePropertySaleRequestController() {
        this.repositories = Repositories.getInstance();
    }

    public boolean createSalesRequest(PropertySaleRequest.TypesOfBusinesses typeOfBusiness, Property.Type propertyType,
                                      double area, double distanceFromcityCenter,
                                      double requestedPrice, State state, District district, City city, String street,
                                      String zipCode, String photos, Agent agent,
                                      int numberOfBedRooms, int numberOfBathrooms,
                                      int parkingSpaces, String availableEquipment, boolean existanceOfInhabitableLoft,
                                      boolean existanceOfBasement, House.SunExposure sunExposure) {

        Optional<Client> client = getClientFromUserSession();
        if (client.isEmpty())
            return false;

        PropertySaleRequestRepository propertySaleRequestRepository = repositories.getPropertySaleRequestRepository();

        return (propertySaleRequestRepository.createPropertySaleRequest(typeOfBusiness,propertyType,area,distanceFromcityCenter,
                requestedPrice,state,district,city,street,zipCode,photos,client.get(),agent,numberOfBedRooms,numberOfBathrooms,
                parkingSpaces,availableEquipment,existanceOfInhabitableLoft,existanceOfBasement,sunExposure));

    }
    public Optional<Client> getClientFromUserSession(){
        AuthenticationRepository authenticationRepository = repositories.getAuthenticationRepository();
        Email email = authenticationRepository.getCurrentUserSession().getUserId();

        ClientRepository clientRepository = repositories.getClientRepository();
        return (clientRepository.getClientFromEmail(email));
    }
    public List<Branch> getBranches() {
        BranchRepository storeNetworkRepository= repositories.getBranchRepository();
        return storeNetworkRepository.getBranchList();
    }

    public List<Agent> getAgents(Branch storeNetwork) {
        BranchRepository storeNetworkRepository = repositories.getBranchRepository();
        return BranchRepository.getAgents(storeNetwork);
    }

    public List<State> getStates() {
        LocationsRepository locationsRepository = repositories.getLocationsRepository();
        return locationsRepository.getStatesList();
    }

    public List<District> getDistrict(State state) {
        LocationsRepository locationsRepository = repositories.getLocationsRepository();
        return state.getDistricts();
    }

    public List<City> getCities(District District) {
        LocationsRepository locationsRepository = repositories.getLocationsRepository();
        return District.getCities();
    }
```



## Class BranchesRepository

```java
public class BranchRepository {
    List<Branch> branches = new ArrayList<>();

    public static List<Agent> getAgents(Branch storeNetwork) {
        List<Store> stores = storeNetwork.getStores();
        List<Agent> branchAgents = new ArrayList<>();
        for (Store Store : stores) {
            List<Agent> StoreAgents = Store.getAgents();
            for (Agent Agent : StoreAgents) {
                branchAgents.add(Agent);
            }
        }
        return branchAgents;
    }

   
    public Optional<Agent> getAgentFromEmail(String email) {
        Optional<Agent> agent = Optional.empty();

        for (Branch storeNetwork : branches) {
            agent = storeNetwork.getAgentFromEmail(email);
            if (agent.isPresent()) break;
        }

        return agent;
    }

 
    public Optional<Agent> getAgentFromEmail(Email email) {
        Optional<Agent> agent = Optional.empty();

        for (Branch storeNetwork : branches) {
            agent = storeNetwork.getAgentFromEmail(email);
            if (agent.isPresent()) break;
        }

        return agent;
    }

    public List<Branch> getBranchList() {
        return new ArrayList<>(branches);
    }

    public boolean createBranch() {
        branches.add(new Branch());
        return true;
    }
}
```
## Class LocationRepositories
```java
public class LocationsRepository {
    
    List<State> states = new ArrayList<>();

    
    public boolean createState(String stateName, String[] districtNamesList, String[][] cityNamesArray) {
        State state;
        try {
            state = new State(stateName, districtNamesList, cityNamesArray);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return addState(state);
    }

   
    private boolean addState(State state) {
        if (!state.isValid()) return false;
        if (isValid(state)) return false;

        states.add(state);
        return true;
    }

    
    private boolean isValid(State state) {
        return states.contains(state);
    }

    
    public List<State> getStatesList() {
        return new ArrayList<>(states);
    }

}
```

## Class PropertySaleRequestRepositories

```java
public class PropertySaleRequestRepository {
    List<PropertySaleRequest> propertySaleRequests = new ArrayList<>();

    public boolean createPropertySaleRequest(PropertySaleRequest.TypesOfBusinesses typeOfBusiness, Property.Type propertyType, double area, double distanceFromcityCenter,
                                             double requestedPrice, State state, District district, City city, String street,
                                             String zipCode, String photos, Client client, Agent agent,
                                             int numberOfBedRooms, int numberOfBathrooms,
                                             int parkingSpaces, String availableEquipment, boolean existanceOfInhabitableLoft,
                                             boolean existanceOfBasement, House.SunExposure sunExposure) {
        try {
            PropertySaleRequest propertySaleRequest = new PropertySaleRequest(typeOfBusiness, propertyType, area, distanceFromcityCenter, requestedPrice, state, district, city, street, zipCode, photos, client, agent, numberOfBedRooms, numberOfBathrooms, parkingSpaces, availableEquipment, existanceOfInhabitableLoft, existanceOfBasement, sunExposure);
            return addPropertySaleRequest(propertySaleRequest);
        }catch (IllegalArgumentException e){
            return false;
        }
    }



    public boolean addPropertySaleRequest(PropertySaleRequest propertySaleRequest)
    {
        if (!propertySaleRequest.isValid()) return false;
        if (!isValid(propertySaleRequest)) return false;

        propertySaleRequests.add(propertySaleRequest);
        return true;
    }

    private boolean isValid(PropertySaleRequest propertySaleRequest) {
        return !propertySaleRequests.contains(propertySaleRequest);
    }
}

```

## Class PropertySaleRequest
````java
public class PropertySaleRequest {

    
    public enum TypesOfBusinesses {
        RENT,
        SELL
    }
    
    private Property property;
   
    private TypesOfBusinesses typeOfBusiness;
    
    private double requestedPrice;

    
    private Agent agent;
    
    public PropertySaleRequest(TypesOfBusinesses typeOfBusiness, Property.Type propertyType, double area, double distanceFromcityCenter,
                               double requestedPrice, State state, District district, City city, String street,
                               String zipCode, String photos, Client client, Agent agent,
                               int numberOfBedRooms, int numberOfBathrooms,
                               int parkingSpaces, String availableEquipment, boolean existanceOfInhabitableLoft,
                               boolean existanceOfBasement, House.SunExposure sunExposure) {


        if (state == null || district == null || city == null || street == null || zipCode == null
                || photos == null || client == null || agent == null)
            throw new IllegalArgumentException("Arguments cannot be null!");
        this.typeOfBusiness = typeOfBusiness;
        this.requestedPrice = requestedPrice;
        this.agent = agent;

        if (propertyType.equals(Property.Type.LAND)) {
            this.property = new Land(area, distanceFromcityCenter, state, district, city, street, zipCode, photos, client);

        } else if (propertyType.equals(Property.Type.APARTMENT)) {
            this.property = new Apartment(area, distanceFromcityCenter, state, district, city, street, zipCode, photos,
                    client, numberOfBedRooms, numberOfBathrooms, parkingSpaces, availableEquipment);

        } else if (propertyType.equals(Property.Type.HOUSE)) {
            this.property = new House(area, distanceFromcityCenter, state, district, city, street, zipCode, photos,
                    client, numberOfBedRooms, numberOfBathrooms, parkingSpaces, availableEquipment, existanceOfInhabitableLoft,
                    existanceOfBasement, sunExposure);

        } else
            throw new IllegalArgumentException("Illegal propertyType!");
    }

    
    public double getRequestedPrice() {
        return requestedPrice;
    }

    
    public TypesOfBusinesses getTypeOfBusiness() {
        return typeOfBusiness;
    }

    
    public Property getProperty() {
        return property;
    }

   
    public Agent getAgent() {
        return agent;
    }

    
    public void setProperty(Property property) {
        this.property = property;
    }

    
    public void setRequestedPrice(float requestedPrice) {
        this.requestedPrice = requestedPrice;
    }

    
    public void setTypeOfBusiness(TypesOfBusinesses typeOfBusiness) {
        this.typeOfBusiness = typeOfBusiness;
    }

    
    @Override
    public boolean equals(Object outroObjeto) {
        if (this == outroObjeto) {
            return true;
        }
        if (outroObjeto == null || getClass() != outroObjeto.getClass()) {
            return false;
        }
        PropertySaleRequest outraPropertySale = (PropertySaleRequest) outroObjeto;
        return this.property.equals(outraPropertySale.property)
                && this.requestedPrice == outraPropertySale.requestedPrice
                && this.typeOfBusiness == outraPropertySale.typeOfBusiness
                && this.agent.equals(outraPropertySale.agent);

    }

    
    public boolean isValid() {
        if (!property.isValid()) {
            return false;
        }
        return ((typeOfBusiness.equals(TypesOfBusinesses.RENT) || typeOfBusiness.equals(TypesOfBusinesses.SELL)) &&
                requestedPrice > 0);
    }



    
    public static class SortByCityComparator implements Comparator<PropertySaleRequest> {

        
        public int compare(PropertySaleRequest p1, PropertySaleRequest p2) {
            return p1.property.getCity().compareTo(p2.property.getCity());
        }
    }
}

````

# 6. Integration and Demo


# 7. Observations


