package pt.ipp.isep.dei.esoft.project.util.regression;


import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ResidentialPropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySoldDTO;
import pt.ipp.isep.dei.esoft.project.repository.PropertySoldRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;

public class DataCollector {

    public static double[][] getAreaData() {
        List<PropertySoldDTO> list = getAndFilterList();

        double[][] data = new double[list.size()][2];

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).property.area;
            data[i][1] = list.get(i).paidAmount;
        }

        return data;
    }


    public static double[][] getDistancFromCityCenterData() {
        List<PropertySoldDTO> list = getAndFilterList();

        double[][] data = new double[list.size()][2];

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).property.distanceFromCityCenter;
            data[i][1] = list.get(i).paidAmount;
        }

        return data;
    }

    public static double[][] getNumberOfBedroomsData() {
        List<PropertySoldDTO> list = getAndFilterList();

        double[][] data = new double[list.size()][2];

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = ((ResidentialPropertyDTO) list.get(i).property).numberOfBedrooms;
            data[i][1] = list.get(i).paidAmount;
        }

        return data;
    }

    public static double[][] getNumberOfBathroomsData() {
        List<PropertySoldDTO> list = getAndFilterList();

        double[][] data = new double[list.size()][2];

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = ((ResidentialPropertyDTO) list.get(i).property).numberOfBathrooms;
            data[i][1] = list.get(i).paidAmount;
        }

        return data;
    }

    public static double[][] getNumberOfparkingSpaces() {
        List<PropertySoldDTO> list = getAndFilterList();

        double[][] data = new double[list.size()][2];

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = ((ResidentialPropertyDTO) list.get(i).property).numberOfBedrooms;
            data[i][1] = list.get(i).paidAmount;
        }


        return data;
    }

    public static double[][] independentData() {
        List<PropertySoldDTO> list = getAndFilterList();

        double[][] data = new double[list.size()][5];

        for (int i = 0; i < list.size(); i++) {

            data[i][0] = list.get(i).property.area;
            data[i][1] = list.get(i).property.distanceFromCityCenter;
            data[i][2] = ((ResidentialPropertyDTO) list.get(i).property).numberOfBedrooms;
            data[i][3] = ((ResidentialPropertyDTO) list.get(i).property).numberOfBathrooms;
            data[i][4] = ((ResidentialPropertyDTO) list.get(i).property).numberOfParkingSpaces;
        }

        return data;
    }

    public static double[] dependedData() {
        List<PropertySoldDTO> list = getAndFilterList();

        double[] data = new double[list.size()];

        for (int i = 0; i < list.size(); i++) {
            data[i] = list.get(i).paidAmount;
        }

        return data;
    }

    private static List<PropertySoldDTO> getAndFilterList() {
        PropertySoldRepository propertySoldRepository = Repositories.getPropertySoldRepository();

        List<PropertySoldDTO> list = propertySoldRepository.getPropertySoldDTOList();

        list.removeIf(propertyDTO -> !(propertyDTO.property instanceof ResidentialPropertyDTO));

        return list;
    }

}
