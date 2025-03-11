package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.NetworkStoresSplitController;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.List;

/**
 * The type Network stores split ui.
 */
public class NetworkStoresSplitUI implements Runnable {
    public void run() {

        NetworkStoresSplitController networkStoresSplitController = new NetworkStoresSplitController();

        long time_start = System.nanoTime();
        List<StoreDTO> list = networkStoresSplitController.getStoreDTOList();
        List<List<StoreDTO>> storeLists = networkStoresSplitController.NetworkStoresSplit(list.size()).get();

        long time_end = System.nanoTime();
        float milisec = (time_end - time_start) / 1000000.0F;
        float seconds = milisec / 1000;


        Utils.showStoreSubLists(storeLists);

        System.out.println("Execution Time : " + milisec + " miliseconds");
    }
}
