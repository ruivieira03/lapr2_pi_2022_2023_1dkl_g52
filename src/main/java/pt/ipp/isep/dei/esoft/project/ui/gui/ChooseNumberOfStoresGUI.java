package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import pt.ipp.isep.dei.esoft.project.application.controller.NetworkStoresSplitController;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.StoreRepository;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuInterface;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChooseNumberOfStoresGUI implements Initializable, MenuInterface {
    MainGUI mainApp;
    private NetworkStoresSplitController controller;
    @FXML
    private Label alert;

    @FXML
    private Button buttonBack;

    @FXML
    private TextField insertedNumber;

    @FXML
    private TextField numberOFStores;

    @FXML
    private Button submitButton;

    @FXML
    void SubmitNumber(MouseEvent event) {

        if (Integer.parseInt(insertedNumber.getText())> controller.getStoreDTOList().size() || Integer.parseInt(insertedNumber.getText()) < 0 || insertedNumber.getText().equals("")) {
            alert.setVisible(true);
        }else{
            controller.setNumberStores(Integer.parseInt(insertedNumber.getText()));

            try {
                NetworkStoresSplitGUI networkStoresSplitGUI = (NetworkStoresSplitGUI) mainApp.replaceSceneContent("/fxml/NetworkStoresSplit.fxml");
                networkStoresSplitGUI.setMainApp(mainApp);
            }catch (Exception ex){
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
            }
        }

    }

    @FXML
    void goBack(MouseEvent event) {
        try {
            networkManagerGUI networkManagerGUI = (networkManagerGUI)  mainApp.replaceSceneContent("/fxml/networkManagerGUI.fxml");
            networkManagerGUI.setMainApp(mainApp);
        }catch (Exception ex){
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {

        controller = new NetworkStoresSplitController();

        numberOFStores.setText(String.valueOf(controller.getStoreDTOList().size()));


    }
    @Override
    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }
}
