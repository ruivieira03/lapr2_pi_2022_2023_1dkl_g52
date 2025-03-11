package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import pt.ipp.isep.dei.esoft.project.application.controller.NetworkStoresSplitController;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuInterface;
import java.util.List;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkStoresSplitGUI implements Initializable, MenuInterface {

    MainGUI mainApp;
    @FXML
    private TextArea subList1;

    @FXML
    private TextArea subList2;
    @FXML
    private TextField diferenceTextField;
    @FXML
    private TextField executionTime;
    @FXML
    private TextField numberOfStores;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NetworkStoresSplitController controller = new NetworkStoresSplitController();
        List<List<StoreDTO>> lists = controller.NetworkStoresSplit(controller.getNumberStores()).get();
        for(int i = 0; i < lists.get(0).size(); i++) {
            subList1.insertText(0,lists.get(0).get(i).toString() + "\n");

        }

    for(int i = 0; i < lists.get(1).size(); i++) {
        subList2.insertText(0,lists.get(1).get(i).toString() + "\n");
    }
    diferenceTextField.insertText(0, String.valueOf(controller.getStoresListsDiference()));
    executionTime.insertText(0,String.valueOf(controller.getExecutionTimeInSeconds()));
    numberOfStores.setText(String.valueOf(controller.getNumberStores()));
}
    @FXML
    void goBack(MouseEvent event) {
        try {
            ChooseNumberOfStoresGUI chooseNumberOfStoresGUI = (ChooseNumberOfStoresGUI)  mainApp.replaceSceneContent("/fxml/chooseNumberOfStoresGUI.fxml");
            chooseNumberOfStoresGUI.setMainApp(mainApp);
        }catch (Exception ex){
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    @FXML
    private Button backButton;


    @Override
    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }
}
