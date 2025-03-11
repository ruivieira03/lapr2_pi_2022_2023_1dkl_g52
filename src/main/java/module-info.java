module pt.ipp {
    requires java.logging;
    requires AuthLib;
    requires org.apache.commons.lang3;
    requires commons.math3;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;

    exports pt.ipp.isep.dei.esoft.project.ui.gui;
    opens pt.ipp.isep.dei.esoft.project.ui.gui to javafx.fxml;
}