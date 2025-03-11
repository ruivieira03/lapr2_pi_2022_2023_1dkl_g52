package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.StoreRepository;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.SplitStoreListAlgorithms;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;


import java.util.List;

/**
 * The type Network stores split controller.
 */
public class NetworkStoresSplitController {
    private final Repositories repositories = Repositories.getInstance();

    private static int numberStores;

    /**
     * Network stores split error optional.
     *
     * @return the error optional
     */
    public ErrorOptional<List<List<StoreDTO>>> NetworkStoresSplit(int numberOfStores) {

        StoreRepository storeRepository = Repositories.getStoreRepository();

        List<StoreDTO> storeList = getStoreDTOList();

        SplitStoreListAlgorithms algorithms = new SplitStoreListAlgorithms();

        List<List<StoreDTO>> splitedList = algorithms.splitStoreListInTwo(storeList,numberOfStores);

        return ErrorOptional.of(splitedList);
    }

    public List<StoreDTO> getStoreDTOList(){
        StoreRepository storeRepository = Repositories.getStoreRepository();
        return storeRepository.getSortedStoreList();

    }

    public int getStoresListsDiference(){
        SplitStoreListAlgorithms algorithms = new SplitStoreListAlgorithms();
        return algorithms.getMinDiffFinal();
    }

    public float getExecutionTimeInSeconds(){
        SplitStoreListAlgorithms algorithms = new SplitStoreListAlgorithms();
        return algorithms.getTimeSecondsFinal();
    }

    public void setNumberStores(int number){
        numberStores=number;
    }
    public int getNumberStores(){
        return numberStores;
    }
}
