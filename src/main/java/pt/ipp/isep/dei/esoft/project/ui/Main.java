package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MainMenu;

public class Main {

    public static void main(String[] args) {
        DataLoader dataLoader = new DataLoader();
        dataLoader.load();

        Repositories repositories = Repositories.getInstance();
        try {
            MainMenu menu = new MainMenu();
            menu.run();
            dataLoader.unload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
