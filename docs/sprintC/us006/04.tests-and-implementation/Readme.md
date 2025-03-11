# US 006 - Specifying Locations

# 4. Tests

**Test 1:** Check that it is not possible to create an instance of the Task class with null values.

	@Test(expected = IllegalArgumentException.class)
		public void ensureNullIsNotAllowed() {
		State instance = new State(null, null, null);
        
	}


**Test 2:** Check that it is not possible to create an instance of the State class with a non-alphanumeric name - AC1.

	@Test(expected = IllegalArgumentException.class)
		public void ensureReferenceMeetsAC1() {

		State instance = new State("|Ileggal_Name|", new String[] {"district"}, new String[][] {{"City"}});
	}

**Test 3:** Check that it is not possible to create an instance of the State class if there's no districts - AC2.
    
    @Test(expected = IllegalArgumentException.class)
		public void ensureReferenceMeetsAC2() {

		State instance = new State("stateName", new String[] {}, new String[][] {{"City"}});
	}

**Test 4:** Check that is not possible to create an instance of the State class if there's no Cities - AC3.

    @Test(expected = IllegalArgumentException.class)
		public void ensureReferenceMeetsAC3() {

		State instance = new State("StateName", new String[] {"district"}, new String[][] {{}});
	}


**Test 5** Check that it is possible to create an instance of State class if all critirea is met.

    @Test()
		public void ensureCreatingInstanceOfClass() {
        
        String stateName = "California";
        String districtName = "FirstDistrict";
        String cityName = "San Francisco";
    
        String [] districts = new String[] {districtName};
        String [][] cities = new String[][]  {{cityName}};
    
        State instance = new State(stateName, districts, cities);

        assert(instance.toString.equals(StateName));
        assert(instance.getDistricts()[0].toString.equals(StateName));
        assert(instance.getCities()[0]toString.equals(StateName));
	}



# 5. Construction (Implementation)


## Class SpecifyLocationsController


```java
public boolean createState(String stateName, String[] districtsNameList, String[][] citiesNamesArray) {
    LocationsRepository locationsRepository = repositories.getLocationsRepository();
    
    return locationsRepository.createState(stateName, districtsNameList, citiesNamesList);
    
}
```


## Class LocationsRepository

```java
public bollean createState(String stateName, String[] districtsNameList, String[][] citiesNamesArray) {
    try {
        State newState = new State(stateName, districtsNameList, citiesNamesList);
    } except (IllegalArgumentException) {return false};
    
    addState(newState);
}
```

## Class State
```java
public State(String stateName, String[] districtsNameList, String[][] citiesNamesArray) throws IllegalArgumentException {
    
    if (stateName == null || districtsNameList == null || citiesNamesArray == null || districtsNameList.length() != citiesNamesArray.length()) throw new IllegalArgumentException("There must be at least one district and one city for each district!");
        
    this.name = stateName;    
    for (int i = 0; i < districtsNameList.length(); i++) {
        this.locations.add(new District(districtList[i], citiesNameList[i]))
    }
}
```

## Class District

```java
    public District(String districtName, String[] citiesNamesList) throws IllegalArgumentException {
        
        if (citiesNameList == null || districtName == null || citiesNAmeList.length() == 0 ) throw new IllegalArgumentException("There must be at leat one city!")

        this.name = districtName;
        for (String cityName: citiesNameList){
            this.locations.add(new City(cityName))
        }
    }
```


## Class City

```java
    public City(Strin cityName) throws IllegalArgumentException {
        if (cityName == null) throw new IllegalArgumentException("A cityName can not be null!");
        
        this.name = cityName;
    }
```

# 6. Integration and Demo

* A new option on the Employee menu options was added.

* Some demo purposes some tasks are bootstrapped while system starts.


# 7. Observations

Platform and Organization classes are getting too many responsibilities due to IE pattern and, therefore, they are becoming huge and harder to maintain.

Is there any way to avoid this to happen?





