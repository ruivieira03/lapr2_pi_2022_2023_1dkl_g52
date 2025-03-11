package pt.ipp.isep.dei.esoft.project.util.serialization;

import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.*;

public class Serialization {

    private File file;

    public void loadFile(String path) {
        file =  new File(path);
    }

    public void serialize() throws IOException {
        boolean ignore = file.createNewFile();
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(Repositories.getInstance());
        out.close();
        fileOut.close();
    }


    public void desirialize() throws IOException, ClassNotFoundException {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            Repositories repositories = (Repositories) in.readObject(); // Deserialize the object
            in.close();
            fileIn.close();

            Repositories.setInstance(repositories);
            Repositories.deserialize();
    }
}
