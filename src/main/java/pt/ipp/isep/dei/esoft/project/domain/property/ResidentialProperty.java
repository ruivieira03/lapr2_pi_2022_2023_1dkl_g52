package pt.ipp.isep.dei.esoft.project.domain.property;

/**
 * The type Residential property.
 */
public abstract class ResidentialProperty extends Property {
    /**
     * number of bedrooms in the residential property
     */
    private int numberOfBedrooms;
    /**
     * number of bathrooms in the residential property
     */
    private int numberOfBathrooms;
    /**
     * number of parking spaces in the residential property
     */
    private int numberOfParkingSpaces;
    /**
     * available equipment in the residential property
     */
    private String availableEquipment;


    /**
     * Instantiates a new Residential property.
     */
    public ResidentialProperty() {
        super();
    }

    /**
     * Method used for getting the number of bathrooms in a residential property
     *
     * @return an integer representing the number os bathrooms
     */
    public int getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    /**
     * Method used for getting the number of bedrooms in a residential property
     *
     * @return an integer representing the number of bathrooms
     */
    public int getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    /**
     * Method used for getting the number of parking spaces in a residential property
     *
     * @return an integer repesenting the number of bedrooms
     */
    public int getNumberOfParkingSpaces() {
        return numberOfParkingSpaces;
    }

    /**
     * Method used for getting the available equipment in a residential property
     *
     * @return an interger repesenting the number of parking spaces
     */
    public String getAvailableEquipment() {
        return availableEquipment;
    }

    /**
     * Method used to change the number of bathrooms in a residential property
     *
     * @param numberOfBathrooms the number of bathrooms
     */
    public void setNumberOfBathrooms(int numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    /**
     * Methos used to change the number of bedrooms in a residential property
     *
     * @param numberOfBedrooms number of bedrooms in a residential property
     */
    public void setNumberOfBedrooms(int numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    /**
     * Method used to change the number of parking spaces in a residential property
     *
     * @param numberOfParkingSpaces number of parking spaces in a residential property
     */
    public void setNumberOfParkingSpaces(int numberOfParkingSpaces) {
        this.numberOfParkingSpaces = numberOfParkingSpaces;
    }

    /**
     * Method used to change the available equipment in a residential property
     *
     * @param availableEquipment equipment available in a residential property
     */
    public void setAvailableEquipment(String availableEquipment) {
        this.availableEquipment = availableEquipment;
    }

    /**
     * Method used to check if one object is equal to another
     *
     * @param outroObjeto object to be compared to the initial object
     * @return true if the objects are the same or false if the objects are different
     */

    @Override
    public boolean equals(Object outroObjeto) {
        if (this == outroObjeto) {
            return true;
        }
        if (outroObjeto == null || getClass() != outroObjeto.getClass()) {
            return false;
        }
        ResidentialProperty outraResidentialProperty = (ResidentialProperty) outroObjeto;
        return super.equals(outroObjeto) && this.availableEquipment.equalsIgnoreCase(outraResidentialProperty.availableEquipment)
                && this.numberOfBathrooms == outraResidentialProperty.numberOfBathrooms
                && this.numberOfBedrooms == outraResidentialProperty.numberOfBedrooms
                && this.numberOfParkingSpaces == outraResidentialProperty.numberOfParkingSpaces;
    }

    /**
     * Method used to get equipment available in a residential property
     *
     * @param strings the strings
     * @return a string representing the equipment available in a residential property
     */
    public static String checkEquipment(String[] strings) {
        StringBuilder builder = new StringBuilder();

        if (strings[0].equals("Y")) {
            builder.append("Central Heating, ");

        } else if (strings[0].equals("NA")) {
            builder.append("NA, ");

        } else if (strings[0].equals("N")) {
            builder.append("No Central Heating, ");
        }


        if (strings[1].equals("Y")) {
            builder.append("Air Conditioner, ");

        } else if (strings[1].equals("NA")) {
            builder.append("NA, ");

        } else if (strings[1].equals("N")) {
            builder.append("No Air Conditioner, ");
        }

        return builder.toString();
    }
}
