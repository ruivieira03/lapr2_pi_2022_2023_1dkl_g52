package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.util.serialization.Serialization;

public class DataLoader {

    private static final String serPath = ("repositories");

    public void load() {
        Serialization serialization = new Serialization();
        serialization.loadFile(serPath);

        try {
            serialization.desirialize();

        } catch (Exception e) {
            System.out.println("Failed to load data - Using bootstrap");
            new Bootstrap().run();
        }
    }

    public void unload() {
        Serialization serialization = new Serialization();
        serialization.loadFile(serPath);

        try {
            serialization.serialize();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save data!");
        }
    }
}
