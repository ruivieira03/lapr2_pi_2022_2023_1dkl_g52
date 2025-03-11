package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.ui.DataLoader;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.InputStream;
public class MainGUI extends Application {
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        DataLoader dataLoader = new DataLoader();
        dataLoader.load();
        this.stage = stage;
        stage.setTitle("Real Estate USA");
        toMainScene();

        this.stage.show();
    }

    @Override
    public void stop() throws Exception {
        new DataLoader().unload();
    }

    //    public Stage getStage() {
//        return this.stage;
//    }

    public void toMainScene() {
        try {
            MainMenuGUI mainUI = (MainMenuGUI) replaceSceneContent("/fxml/mainMenuGUI.fxml");
            mainUI.setMainApp(this);
        } catch (Exception ex) {
            Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = MainMenuGUI.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MainMenuGUI.class.getResource(fxml));
        Pane page;
        try {
            page = (Pane) loader.load(in);
        } finally {
            assert in != null;
            in.close();
        }
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
