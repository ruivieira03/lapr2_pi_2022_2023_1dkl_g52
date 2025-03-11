# US 006 - To create a Task 

# 4. Tests 

**Test 1:** Check that it is not possible to create an instance of the PropertySale class with null values, illegal values and a possible case. 

    @Test
	void createPropertySale() {
        State state = new State("state", new String[]{"District"}, new String[][]{{"City"}});
        District district = state.getDistricts().get(0);
        City city = district.getCities().get(0);
        Client client = new Client("name", 123, 123, 123, "adress", "email@ex.com");
        Agent agent = new Agent("name", 123, 123, "123", "agent@ex.com", 123123123);

        // illegal arguments
        assert (!propertySaleRepository.createPropertySale(null, null, 0, 0,
                0, null, null, null, null, null, null, null,
                null, 0, null, 0, 0, 0,
                null, false, false, null));


        // fail local validation
        assertFalse(propertySaleRepository.createPropertySale(PropertySaleRequest.TypesOfBusinesses.RENT, Property.Type.HOUSE,
                -1, 10, 10000, state, district, city, "adress", "123", "photo",
                client, PropertySale.CommissionType.PERCENTAGE, 1, agent, 1, 1, 1, "none",
                true, true, House.SunExposure.SOUTH));

        // fail global validattion
        assertFalse(propertySaleRepository.createPropertySale(PropertySaleRequest.TypesOfBusinesses.RENT, Property.Type.HOUSE,
                100, 10, 10000, state, district, city, "adress", "123", "photo",
                client, PropertySale.CommissionType.PERCENTAGE, 1, agent, 1, 1, 1, "none",
                true, true, House.SunExposure.SOUTH));


        // working
        assert(propertySaleRepository.createPropertySale(PropertySaleRequest.TypesOfBusinesses.RENT, Property.Type.HOUSE,
                100, 10, 10000, state, district, city, "new street", "123", "photo",
                client, PropertySale.CommissionType.PERCENTAGE, 1, agent, 1, 1, 1, "none",
                true, true, House.SunExposure.SOUTH));
    }



# 5. Construction (Implementation)


## Class RegisterPropertySaleController

```java
public boolean createPropertySale(PropertySaleRequest.TypesOfBusinesses typeOfBusiness, Property.Type propertyType, double area, double distanceFromcityCenter,
        double requestedPrice, State state, District district, City city, String street,
        String zipCode, String photos, String ownerEmail, PropertySale.CommissionType typeOfCommission,
        double commission, int numberOfBedRooms, int numberOfBathrooms,
        int parkingSpaces, String availableEquipment, boolean existanceOfInhabitableLoft,
        boolean existanceOfBasement, House.SunExposure sunExposure) {

        Optional<Agent> agent = getAgentFromSession();
        if (agent.isEmpty()) {return false;}

        Optional<Client> client = getClientFromEmail(ownerEmail);
        if (client.isEmpty()) {return false;}

        PropertySaleRepository propertySaleRepository = repositories.getPropertySaleRepository();
        return propertySaleRepository.createPropertySale(typeOfBusiness, propertyType, area, distanceFromcityCenter,
        requestedPrice, state, district, city, street, zipCode, photos, client.get(), typeOfCommission, commission,
        agent.get(), numberOfBedRooms, numberOfBathrooms, parkingSpaces, availableEquipment,
        existanceOfInhabitableLoft, existanceOfBasement, sunExposure);
        }
```
## Class PropertySaleRepository

```java
public boolean createPropertySale(PropertySaleRequest.TypesOfBusinesses typeOfBusiness, Property.Type propertyType, double area, double distanceFromcityCenter,
        double requestedPrice, State state, District district, City city, String street,
        String zipCode, String photos, Client client, PropertySale.CommissionType typeOfCommission,
        double commission, Agent agent, int numberOfBedRooms, int numberOfBathrooms,
        int parkingSpaces, String availableEquipment, boolean existanceOfInhabitableLoft,
        boolean existanceOfBasement, House.SunExposure sunExposure) {

    try {
        PropertySale propertySale = new PropertySale(typeOfBusiness, propertyType, area, distanceFromcityCenter,
            requestedPrice, state, district, city, street, zipCode, photos, client, typeOfCommission, commission, agent,
            numberOfBedRooms, numberOfBathrooms, parkingSpaces, availableEquipment,
            existanceOfInhabitableLoft, existanceOfBasement, sunExposure);

        return addPropertySale(propertySale);

    } catch (IllegalArgumentException e) {
        return false;
    }
}
```

## Class PropertySale

```java
 public PropertySale(PropertySaleRequest.TypesOfBusinesses typeOfBusiness, Property.Type propertyType, double area, double distanceFromcityCenter,
                        double requestedPrice, State state, District district, City city, String street, String zipCode,
                        String photos, Client client, PropertySale.CommissionType typeOfCommission, double commission, Agent agent,
                        int numberOfBedRooms, int numberOfBathrooms,
                        int parkingSpaces, String availableEquipment, boolean existanceOfInhabitableLoft,
                        boolean existanceOfBasement, House.SunExposure sunExposure) {

        super(typeOfBusiness, propertyType, area, distanceFromcityCenter,
                requestedPrice, state, district, city, street, zipCode, photos, client, agent,
                numberOfBedRooms, numberOfBathrooms, parkingSpaces, availableEquipment,
                existanceOfInhabitableLoft, existanceOfBasement, sunExposure);


        this.typeOfCommission = typeOfCommission;
        this.commission = commission;
    }
```

## PropertySaleRequest

```java
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
```

## Class House 

```java
public House(double area, double distanceFromcityCenter, State state, District district, City city, String street,
                 String zipCode, String photos, Client client, int numberOfBedRooms, int numberOfBathrooms,
                 int parkingSpaces, String availableEquipment, boolean existanceOfInhabitableLoft,
                 boolean existanceOfBasement, SunExposure sunExposure) {

        super(area, distanceFromcityCenter, state, district, city, street, zipCode, photos,
                client, numberOfBedRooms, numberOfBathrooms, parkingSpaces, availableEquipment);

        this.existanceOfInhabitableLoft = existanceOfInhabitableLoft;
        this.existanceOfBasement = existanceOfBasement;
        this.sunExposure = sunExposure;

    }
```

## Class Apartment

```java
public Apartment(double area, double distanceFromcityCenter, State state, District district, City city, String street, String zipCode, String photos, Client client, int numberOfBedRooms, int numberOfBathrooms, int parkingSpaces, String availableEquipment) {
        super(area, distanceFromcityCenter, state, district, city, street, zipCode, photos,
                client, numberOfBedRooms, numberOfBathrooms, parkingSpaces, availableEquipment);
    }
```

## Abstract Class ResidentialProperty

```java
public ResidentialProperty(double area, double distanceFromcityCenter, State state, District district, City city,
                               String street, String zipCode, String photos, Client client, int numberOfBedRooms,
                               int numberOfBathrooms, int parkingSpaces, String availableEquipment) {

        super(area, distanceFromcityCenter, state, district, city, street, zipCode, photos, client);

        if (availableEquipment == null) throw new IllegalArgumentException("available equipment can not be null");

        this.numberOfBathrooms = numberOfBathrooms;
        this.numberOfBedrooms = numberOfBedRooms;
        this.numberOfParkingSpaces = parkingSpaces;
        this.availableEquipment = availableEquipment;
    }
```

## Class Land

```java
public Land(double area, double distanceFromcityCenter, State state, District district, City city, String street, 
                String zipCode, String photos, Client client) {
        
        super(area, distanceFromcityCenter, state, district, city, street, zipCode, photos, client);
}
```

## Abstract Class Property

```java
public Property(double area, double distanceFromcityCenter, State state, District district, City city, String street, String zipCode, String photos, Client client) {

        if (street == null) throw new IllegalArgumentException("Street cannot be null");
        if (zipCode == null) throw new IllegalArgumentException("Zip code cannot be null");
        if (client == null) throw new IllegalArgumentException("Client can be null");

        this.area = area;
        this.street = street;
        this.zipCode = zipCode;
        this.distanceFromCityCentre = distanceFromcityCenter;
        this.city = city;
        this.district = district;
        this.state = state;
        this.photos = photos;
        this.client = client;
    }
```

# 6. Integration and Demo 


* For demo some PropertySales were aded in to bootstrap.


# 7. Observations

PropertySale is going to be used to house comparator for the price while PropertySaleRequest is going to house the rest of the comparators so they can be sorted in their repositoeries.





