package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import javafx.fxml.Initializable;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Paulo Maio pam@isep.ipp.pt
 */

public class MenuGUIItem {
    private final String description;
    private final String fxml;

    public MenuGUIItem(String description, String fxml) {
        if (StringUtils.isBlank(description)) {
            throw new IllegalArgumentException("MenuItem description cannot be null or empty.");
        }
        if (StringUtils.isBlank(fxml)) {
            throw new IllegalArgumentException("MenuItem does not support a null UI.");
        }

        this.description = description;
        this.fxml = fxml;
    }

    public boolean hasDescription(String description) {
        return this.description.equals(description);
    }

    public String getFxml() {
        return fxml;
    }

    public String toString() {
        return this.description;
    }

    public boolean hasRole(String role){
        return description.equals(role);
    }

}
