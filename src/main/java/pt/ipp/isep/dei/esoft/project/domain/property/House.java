package pt.ipp.isep.dei.esoft.project.domain.property;

/**
 * The type House.
 */
public class House extends ResidentialProperty {

    /**
     * The enum Sun exposure.
     */
    public enum SunExposure {
        /**
         * North sun exposure.
         */
        NORTH("N"),
        /**
         * East sun exposure.
         */
        EAST("E"),
        /**
         * South sun exposure.
         */
        SOUTH("S"),
        /**
         * West sun exposure.
         */
        WEST("W");

        private final String abbreviation;

        SunExposure(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        /**
         * Gets abbreviation.
         *
         * @return the abbreviation
         */
        public String getAbbreviation() {
            return abbreviation;
        }

        /**
         * From abbreviation sun exposure.
         * Used to convert abbreviation to enum
         *
         * @param abbreviation the abbreviation
         * @return the sun exposure
         */
        public static SunExposure fromAbbreviation(String abbreviation) {
            for (SunExposure exposure : values()) {
                if (exposure.getAbbreviation().equalsIgnoreCase(abbreviation)) {
                    return exposure;
                }
            }
            throw new IllegalArgumentException("Invalid abbreviation: " + abbreviation);
        }
    }

    /**
     * existance of basement in a house
     */
    private boolean existanceOfBasement;
    /**
     * existance of inhabitable loft in a house
     */
    private boolean existanceOfInhabitableLoft;
    /**
     * sun exposure of a house
     */
    private SunExposure sunExposure;


    /**
     * Emty Constructor
     */
    public House() {
        super();
    }

    /**
     * Method used to get the sun exposure in a house
     *
     * @return a boolean representing the sun exposure
     */
    public SunExposure getSunExposure() {
        return sunExposure;
    }

    /**
     * Method used to get the existance of basement in a house
     *
     * @return a boolean representing the existance of basement
     */
    public boolean getExistanceOfBasement() {
        return existanceOfBasement;
    }

    /**
     * Method used to get the existance of inhabitable loft in a house
     *
     * @return a boolean representing the existance of inhabitable loft
     */
    public boolean getExistanceOfInhabitableLoft() {
        return existanceOfInhabitableLoft;
    }

    /**
     * Method used to change the existance of basement value in a house
     *
     * @param existanceOfBasement existance of basement
     */
    public void setExistanceOfBasement(boolean existanceOfBasement) {
        this.existanceOfBasement = existanceOfBasement;
    }

    /**
     * Method used to change the existance of inhabitable loft value in a house
     *
     * @param existanceOfInhabitableLoft existance of inhabitable loft
     */
    public void setExistanceOfInhabitableLoft(boolean existanceOfInhabitableLoft) {
        this.existanceOfInhabitableLoft = existanceOfInhabitableLoft;
    }

    /**
     * Method used to change the sun exposure value in a house
     *
     * @param sunExposure house sun exposure
     */
    public void setSunExposure(SunExposure sunExposure) {
        this.sunExposure = sunExposure;
    }


    /**
     * Method used to check if one object is equal to another
     *
     * @param outroObjeto
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
        House outraHouse = (House) outroObjeto;
        return super.equals(outroObjeto) && this.sunExposure == outraHouse.sunExposure
                && this.existanceOfInhabitableLoft == outraHouse.existanceOfInhabitableLoft
                && this.existanceOfBasement == outraHouse.existanceOfBasement;
    }
}
