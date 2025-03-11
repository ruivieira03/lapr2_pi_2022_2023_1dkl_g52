package pt.ipp.isep.dei.esoft.project.domain.property;

import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;

/**
 * The type Property.
 */
public abstract class Property implements Serializable {
    /**
     * property area
     */
    private double area;
    /**
     * property street
     */
    private String street;
    /**
     * property zip code
     */
    private int zipCode;
    /**
     * distance from the property to the city center
     */
    private double distanceFromCityCenter;
    /**
     * city where the property is located
     */
    private City city;
    /**
     * district where the property is located
     */
    private District district;
    /**
     * state where the property is located
     */
    private State state;
    /**
     * property photos
     */
    private String photos;
    /**
     * property owner
     */
    private Client client;


    /**
     * The enum Type.
     */
    public enum Type {
        /**
         * Land type.
         */
        LAND("Land"),
        /**
         * Apartment type.
         */
        APARTMENT("Apartment"),
        /**
         * House type.
         */
        HOUSE("House");

        private final String label;

        Type(String label) {
            this.label = label;
        }

        /**
         * Gets label.
         *
         * @return the label
         */
        public String getLabel() {
            return label;
        }

        /**
         * From label type.
         *
         * @param label the label
         * @return the type
         */
        public static Type fromLabel(String label) {
            for (Type type : Type.values()) {
                if (type.getLabel().equalsIgnoreCase(label)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid label: " + label);
        }
    }


    /**
     * Empty Constructor
     */
    public Property() {

    }

    /**
     * Method used to get the area from a property
     *
     * @return a float representing the area
     */
    public double getArea() {
        return area;
    }

    /**
     * Method used to get the street from a property
     *
     * @return a String representing the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Method used to get the distance from the property to the city center
     *
     * @return a float representing the distance from the city center
     */
    public double getDistanceFromCityCentre() {
        return distanceFromCityCenter;
    }

    /**
     * Method used for getting the zip code of a property
     *
     * @return a String representing the zip code
     */
    public int getZipCode() {
        return zipCode;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * Gets district.
     *
     * @return the district
     */
    public District getDistrict() {
        return district;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * Gets photos.
     *
     * @return the photos
     */
    public String getPhotos() {
        return this.photos;
    }

    /**
     * Gets client.
     *
     * @return the client
     */
    public Client getClient() {
        return this.client;
    }


    /**
     * Method used to change the area of a property
     *
     * @param area the area
     */
    public void setArea(double area) {
        this.area = area;
    }

    /**
     * Method used to change the distance from the city of a property
     *
     * @param distanceFromCityCentre the distance from city centre
     */
    public void setDistanceFromCityCentre(double distanceFromCityCentre) {
        this.distanceFromCityCenter = distanceFromCityCentre;
    }

    /**
     * Method used to change the street of a property
     *
     * @param street the street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Method used to change the zip code of property
     *
     * @param zipCode the zip code
     */
    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Sets district.
     *
     * @param district the district
     */
    public void setDistrict(District district) {
        this.district = district;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Sets photos.
     *
     * @param photos the photos
     */
    public void setPhotos(String photos) {
        this.photos = photos;
    }

    /**
     * Sets client.
     *
     * @param client the client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Method used to check if one object is equal to another
     *
     * @param outroObjeto
     * @return true if the objects are the same or false if the objects are different
     */
    @Override
    public boolean equals(Object outroObjeto) {
        if (this == outroObjeto) return true;

        if (outroObjeto == null || getClass() != outroObjeto.getClass()) return false;

        Property outraProperty = (Property) outroObjeto;
        return this.zipCode == outraProperty.zipCode
                && this.street.equalsIgnoreCase(outraProperty.street)
                && this.area == outraProperty.area
                && this.distanceFromCityCenter == outraProperty.distanceFromCityCenter
                && this.city.equals(outraProperty.city)
                && this.district.equals(outraProperty.district)
                && this.state.equals(outraProperty.state);
    }

    /**
     * Method used to check if the property attributes are valid
     *
     * @return true if the property attributes are valid or false if not valid
     */
    public OperationResult isValid() {
        if (area <= 0) return OperationResult.failed("Error - Property - Area must be higher than 0!");

        if (!state.isValid().success()) return state.isValid();

        if (!district.isValid().success()) return district.isValid();

        if (!city.isValid().success()) return city.isValid();

        if (!client.isValid().success()) return client.isValid();

        if (!isZipCodeValid(this.zipCode).success()) return isZipCodeValid(this.zipCode);

        if (street.isEmpty()) return OperationResult.failed("Error - Property - Street cannot be empty!");

        if (distanceFromCityCenter < 0)
            return OperationResult.failed("Error - Property - DistanceFromCityCenter cannot be negative!");

        return OperationResult.successfull();
    }

    /**
     * Is zip code valid operation result.
     *
     * @param zipCode the zip code
     * @return the operation result
     */
    public OperationResult isZipCodeValid(int zipCode) {
        final int RequiredLength = 5;
        String zipCodeS = String.valueOf(zipCode);

        if (zipCodeS.length() == RequiredLength)
            return OperationResult.successfull();

        return OperationResult.failed("Error - Property - ZipCode must have 5 digits!");
    }
}
