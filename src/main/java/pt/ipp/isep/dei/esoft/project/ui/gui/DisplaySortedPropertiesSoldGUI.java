package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainGUI;
import pt.ipp.isep.dei.esoft.project.application.controller.ListPropertySoldController;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySoldDTO;
import pt.ipp.isep.dei.esoft.project.repository.PropertySoldRepository;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuInterface;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DisplaySortedPropertiesSoldGUI implements Initializable, MenuInterface {
    MainGUI mainApp;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ListPropertySoldController controller = new ListPropertySoldController();

        PropertySoldRepository.AlgorithmOptions algorithmOptions = controller.getAlgorithmOptions();

        if (GetSortAlgorithmGUI.choice == 1) {
            algorithm.setText("BubbleSort");
            algorithmOptions.sortAlgorithm = PropertySoldRepository.AlgorithmOptions.SortAlgorithm.BubbleSort;
        }
        if (GetSortAlgorithmGUI.choice == 2) {
            algorithm.setText("InsertionSort");
            algorithmOptions.sortAlgorithm = PropertySoldRepository.AlgorithmOptions.SortAlgorithm.InsertionSort;
        }
        if (GetSortOrderGUI.choice == 1) {
            areaOrder.setText("Ascending");
            algorithmOptions.sortCriteria = PropertySoldRepository.AlgorithmOptions.SortType.SortByAreaDescending;
        }
        if (GetSortOrderGUI.choice == 2) {
            algorithmOptions.sortCriteria = PropertySoldRepository.AlgorithmOptions.SortType.SortByAreaAscending;
            areaOrder.setText("Descending");
        }
        List<PropertySoldDTO> propertySoldList = controller.listPropertySold(algorithmOptions);


        for(int i = 0; i < propertySoldList.size(); i++) {
            propertySoldListDisplay.insertText(0,propertySoldList.get(i).toString() + "\n");

        }
        propertySoldListDisplay.insertText(0,"  Type   | Business |  Price   | Sold Price |       State        |        District       |        City        |                 Street                 | Zip Code |   Area   |Distance from center| Bedrooms | Bathrooms | Parking |      Available Equipment     | Basement | Inhabitable Loft | Sun Exposure " + "\n");

    }

        @FXML
        private TextField areaOrder;

        @FXML
        private TextArea propertySoldListDisplay;

        @FXML
        private TextField algorithm;

        @FXML
        private Button backButton;


        @FXML
        void goBack(MouseEvent event) {
            try {
                GetSortOrderGUI getSortOrderGUI = (GetSortOrderGUI)  mainApp.replaceSceneContent("/fxml/getSortOrder.fxml");
                getSortOrderGUI.setMainApp(mainApp);
            }catch (Exception ex){
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,null,ex);
            }

        }

    @Override
    public void setMainApp(MainGUI mainApp) {
        this.mainApp = mainApp;
    }
}
