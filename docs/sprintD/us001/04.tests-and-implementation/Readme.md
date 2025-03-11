# US 001 - To display listed properties

# 4. Tests


*It is also recommended to organize this content by subsections.* 

#### Test 1 - Test if displays the listed properties

```java
@Test
    void listProperties() {
            assert (propertySaleRepository.listProperties(PropertySaleRepository.SortByRecentlyAdded, PropertySaleRequest.TypesOfBusinesses.SELL, Property.Type.HOUSE, 1).size() == 0);
            assert (propertySaleRepository.listProperties(PropertySaleRepository.SortByRecentlyAdded).size() == 1);
            }
```

# 5. Construction (Implementation)


## Class ListPropertiesController 

```java
public List<PropertySale> listProperties(short sortCriteria){

        PropertySaleRepository propertySaleRepository = repositories.getPropertySaleRepository();

        return propertySaleRepository.listProperties(sortCriteria);
        }
```
```java
public List<PropertySale> listProperties(short sortCriteria, short propertyBusiness, short propertyType, int numberOfRooms) {
        PropertySaleRepository propertySaleRepository = repositories.getPropertySaleRepository();

        return propertySaleRepository.listProperties(sortCriteria, propertyBusiness, propertyType, numberOfRooms);
    }
```



## Class PropertySaleRepository

```java
public List<PropertySale> listProperties(short sortCriteria) {
        List<PropertySale> copy = new ArrayList<>(propertySales);

        SortByPriceComparator sortByPrice = new SortByPriceComparator();
        PropertySaleRequest.SortByCityComparator sortByCity = new PropertySaleRequest.SortByCityComparator();

        if (sortCriteria == SortByPrice) {
        copy.sort(sortByPrice);
        return copy;
        }

        if (sortCriteria == SortByCity) {
        copy.sort(sortByCity);
        return copy;
        }

        return copy;
        }
}
```

```java
public List<PropertySale> listProperties(short sortCriteria, short propertyBusiness, short propertyType, int numberOfRooms){
        List<PropertySale> copy=new ArrayList<>(propertySales);

        // filter
        for(int i=0;i<copy.size();i++){
        PropertySale propertySale=copy.get(i);

        if(propertyBusiness>0&&propertyBusiness!=propertySale.getTypeOfBusiness()){
        copy.remove(propertySale);
        i--;
        continue;
        }

        if(numberOfRooms>0){
        if(propertySale.getProperty()instanceof Land){
        copy.remove(propertySale);
        i--;
        continue;
        }

        if(propertySale.getProperty()instanceof House&&((House)propertySale.getProperty()).getNumberOfBedrooms()!=numberOfRooms){
        copy.remove(propertySale);
        i--;
        continue;
        }
        if(propertySale.getProperty()instanceof Apartment&&((Apartment)propertySale.getProperty()).getNumberOfBedrooms()!=numberOfRooms){
        copy.remove(propertySale);
        i--;
        continue;
        }
        }

        if(propertyType>0){
        if(propertyType==Property.Land){
        if(propertySale.getProperty()instanceof Apartment||propertySale.getProperty()instanceof House){
        copy.remove(propertySale);
        i--;
        continue;
        }
        }
        if(propertyType==Property.Apartment){
        if(propertySale.getProperty()instanceof Land||propertySale.getProperty()instanceof House){
        copy.remove(propertySale);
        i--;
        continue;
        }
        }
        if(propertyType==Property.House){
        if(propertySale.getProperty()instanceof Apartment||propertySale.getProperty()instanceof Land){
        copy.remove(propertySale);
        i--;
        }
        }
        }
        }
        SortByPriceComparator sortByPrice=new SortByPriceComparator();
        PropertySaleRequest.SortByCityComparator sortByCity=new PropertySaleRequest.SortByCityComparator();

        if(sortCriteria==SortByPrice){
        copy.sort(sortByPrice);
        return copy;
        }

        if(sortCriteria==SortByCity){
        copy.sort(sortByCity);
        return copy;
        }

        return copy;
        }
````

## Class PropertySale

```java
public static class SortByPriceComparator implements Comparator<PropertySale> {
    public int compare (PropertySale p1,PropertySale p2){
        return Double.compare(p1.calculatePrice(), p2.calculatePrice());
    }
}
```

## Class PropertySaleRequest

```java
public static class SortByCityComparator implements Comparator<PropertySaleRequest> {
        public int compare (PropertySaleRequest p1,PropertySaleRequest p2){
            return p1.property.getCity().compareTo(p2.property.getCity());
        }
    }
```
# 6. Integration and Demo


# 7. Observations






