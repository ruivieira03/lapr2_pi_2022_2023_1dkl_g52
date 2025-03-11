package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySold;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.PropertySoldMapper;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySoldDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PurchaseRequestDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Property sold repository.
 */
public class PropertySoldRepository extends PropertySale {
    /**
     * The Property sold list.
     */
    List<PropertySold> propertySoldList = new ArrayList<>();

    private transient PropertySoldMapper mapper = new PropertySoldMapper();

    private long startTime;
    private long endTime;

    /**
     * Create property sold operation result.
     * Used to create a property sold from property sold.
     *
     * @param propertySoldDTO the property sold dto
     * @return the operation result
     */
    public OperationResult createPropertySold(PropertySoldDTO propertySoldDTO) {
        ErrorOptional<PropertySold> propertySold = mapper.toDomain(propertySoldDTO);
        if (propertySold.hasError())
            return OperationResult.failed(propertySold.getErrorMessage() + "\nError - Repository - Failed to convert PropertySoldDTO into PropertySold!");

        return addPropertySold(propertySold.get());
    }

    /**
     * Create property sold operation result.
     * Used to create property sold from purchase request.
     *
     * @param purchaseRequestDTO the purchase request dto
     * @return the operation result
     */
    public OperationResult createPropertySold(PurchaseRequestDTO purchaseRequestDTO) {
        ErrorOptional<PropertySold> propertySold = mapper.toDomain(purchaseRequestDTO);
        if (propertySold.hasError())
            return OperationResult.failed(propertySold.getErrorMessage() + "\nError - Repository - Failed to convert PropertySoldDTO into PropertySold!");

        return addPropertySold(propertySold.get());
    }

    /**
     * Add property sold operation result.
     * Used to add a property sold.
     *
     * @param propertySold the property sold
     * @return the operation result
     */
    public OperationResult addPropertySold(PropertySold propertySold){
        OperationResult localValidation = propertySold.isValid();
        if (!localValidation.success())
            return OperationResult.failed(localValidation.getErrorMessage() + "Error - Repository - PropertySold failed local validation!");

        OperationResult globalValidation = isValid(propertySold);
        if (!globalValidation.success())
            return OperationResult.failed(globalValidation.getErrorMessage() + "Error - Repository - PropertySold failed global validation!");

        propertySoldList.add(propertySold);
        return OperationResult.successfull();
    }

    /**
     * Used to validated property sold (global validation).
     *
     * @param propertySold
     * @return
     */
    private OperationResult isValid(PropertySold propertySold) {
        for (PropertySold p : propertySoldList)
            if (p.equals(propertySold))
                return OperationResult.failed("Error - Repository - PropertySoldRepository already contains PropertySold[" + propertySold.getID() + "]!");

        return  OperationResult.successfull();
    }

    /**
     * List property sold list.
     *
     * @param algorithmOptions the algorithm options
     * @return the list
     */
    public List<PropertySoldDTO> listPropertySold(AlgorithmOptions algorithmOptions) {
        List<PropertySold> list = new ArrayList<>(propertySoldList);

        sortProperties(list, algorithmOptions);

        List<PropertySoldDTO> dtos = new ArrayList<>();

        for (PropertySold propertySold : list){
            ErrorOptional<PropertySoldDTO> dto = mapper.toDTO(propertySold);
            if (!dto.hasError())
                dtos.add(dto.get());
        }

        return dtos;
    }

    /**
     * Used to sort the properties sold.
     *
     * @param list
     * @param algorithmOptions
     */

    private void sortProperties (List<PropertySold> list, AlgorithmOptions algorithmOptions) {

        if (algorithmOptions.sortAlgorithm.equals(AlgorithmOptions.SortAlgorithm.BubbleSort)) {
            if (algorithmOptions.sortCriteria.equals(AlgorithmOptions.SortType.SortByAreaDescending)) {
                startTime = System.nanoTime();
                sortBubbleDescending(list);
                endTime = System.nanoTime();
                float timeBubble = (float) (endTime - startTime) / 1000000;
                System.out.printf("\nBubble Sort: %.5f milliseconds\n", timeBubble);
            }
            if (algorithmOptions.sortCriteria.equals(AlgorithmOptions.SortType.SortByAreaAscending)) {
                startTime = System.nanoTime();
                sortBubbleAscending(list);
                endTime = System.nanoTime();
                float timeBubble = (float) (endTime - startTime) / 1000000;
                System.out.printf("\nBubble Sort: %.5f milliseconds\n", timeBubble);
            }
        }

        if (algorithmOptions.sortAlgorithm.equals(AlgorithmOptions.SortAlgorithm.InsertionSort)) {
            if (algorithmOptions.sortCriteria.equals(AlgorithmOptions.SortType.SortByAreaDescending)){
                startTime = System.nanoTime();
                sortInsertionDescending(list);
                endTime = System.nanoTime();
                float timeInsertion = (float) (endTime - startTime) / 1000000;
                System.out.printf("\nInsertion Sort: %.5f milliseconds\n", timeInsertion);
            }
            if (algorithmOptions.sortCriteria.equals(AlgorithmOptions.SortType.SortByAreaAscending)) {
                startTime = System.nanoTime();
                sortInsertionAscending(list);
                endTime = System.nanoTime();
                float timeInsertion = (float) (endTime - startTime) / 1000000;
                System.out.printf("\nInsertion Sort: %.5f milliseconds\n", timeInsertion);
            }
        }
    }

    /**
     * Gets property sold dto list.
     *
     * @return the property sold dto list
     */
    public List<PropertySoldDTO> getPropertySoldDTOList() {
        List<PropertySoldDTO> dtos = new ArrayList<>();

        for (PropertySold propertySold : propertySoldList){
            ErrorOptional<PropertySoldDTO> dto = mapper.toDTO(propertySold);
            if (!dto.hasError())
                dtos.add(dto.get());
        }

        return dtos;
    }

    /**
     * Sort bubble ascending algorithm.
     *
     * @param propertySoldList the property sold list
     */
    public void sortBubbleAscending(List<PropertySold> propertySoldList) {
        PropertySold temp;
        for (int i = 0; i < propertySoldList.size() - 1; i++) {
            for (int j = 0; j < propertySoldList.size() - 1; j++) {
                if (propertySoldList.get(j).getProperty().getArea() > propertySoldList.get(j+1).getProperty().getArea()) {
                    temp = propertySoldList.get(j);
                    propertySoldList.set(j, propertySoldList.get(j+1));
                    propertySoldList.set(j+1, temp);
                }
            }
        }
    }

    /**
     * Sort bubble descending algorithm.
     *
     * @param propertySoldList the property sold list
     */
    public void sortBubbleDescending(List<PropertySold> propertySoldList) {
        PropertySold temp;
        for (int i = 0; i < propertySoldList.size() - 1; i++) {
            for (int j = 0; j < propertySoldList.size() - 1; j++) {
                if (propertySoldList.get(j).getProperty().getArea() < propertySoldList.get(j+1).getProperty().getArea()) {
                    temp = propertySoldList.get(j+1);
                    propertySoldList.set(j+1, propertySoldList.get(j));
                    propertySoldList.set(j, temp);
                }
            }
        }
    }

    /**
     * Sort insertion ascending algorithm.
     *
     * @param propertySoldList the property sold list
     */
    public void sortInsertionAscending(List<PropertySold> propertySoldList) {
        for (int i = 1; i < propertySoldList.size(); i++) {
            PropertySold temp = propertySoldList.get(i);
            int j = i - 1;
            while(j >= 0 && propertySoldList.get(j).getProperty().getArea() > temp.getProperty().getArea()){
                propertySoldList.set(j + 1, propertySoldList.get(j));
                j = j - 1;
            }
            propertySoldList.set(j + 1, temp);
        }
    }

    /**
     * Sort insertion descending algorithm.
     *
     * @param propertySoldList the property sold list
     */
    public void sortInsertionDescending(List<PropertySold> propertySoldList) {
        for (int i = 1; i < propertySoldList.size(); i++) {
            PropertySold temp = propertySoldList.get(i);
            int j = i - 1;
            while(j >= 0 && propertySoldList.get(j).getProperty().getArea() < temp.getProperty().getArea()){
                propertySoldList.set(j + 1, propertySoldList.get(j));
                j = j - 1;
            }
            propertySoldList.set(j + 1, temp);
        }
    }

    /**
     * The type Algorithm options.
     */
    public static class AlgorithmOptions {
        /**
         * The enum Sort algorithm.
         */
        public enum SortAlgorithm {
            /**
             * Bubble sort algorithm.
             */
            BubbleSort,
            /**
             * Insertion sort algorithm.
             */
            InsertionSort
        }

        /**
         * The enum Sort type.
         */
        public enum SortType {
            /**
             * Sort by area ascending sort type.
             */
            SortByAreaAscending,
            /**
             * Sort by area descending sort type.
             */
            SortByAreaDescending
        }

        /**
         * The Sort algorithm.
         */
        public SortAlgorithm sortAlgorithm = SortAlgorithm.BubbleSort;
        /**
         * The Sort criteria.
         */
        public SortType sortCriteria = SortType.SortByAreaAscending;
    }

    /**
     * Deserialize.
     */
    public void deserialize(){
        mapper = new PropertySoldMapper();
    }
}
