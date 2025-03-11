package pt.ipp.isep.dei.esoft.project.util.dispatch;

import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SMSServices {

    private final String filePath = "sms.txt";

    private File loadFile() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            boolean igonre = file.createNewFile();
        }

        return file;
    }

    private OperationResult writeSMS(File file,String phoneNumber, String message) {
        try {
            FileWriter fWriter = new FileWriter(file, true);

            fWriter.write("------------------------------------------------------------\n");
            fWriter.write("TO: " + phoneNumber + "\n\n");
            fWriter.write(message + "\n\n\n");

            fWriter.close();
            return OperationResult.successfull();
        } catch (Exception e) {
            return OperationResult.failed("Error - SMSService - Failed to write to file!");
        }
    }

    public OperationResult sendSMS (String phoneNumber, String message) {
        try {
            File file = loadFile();
            return writeSMS(file, phoneNumber, message);
        } catch (Exception e) {
            return OperationResult.failed("Error - SMSService - Failed to load file!");
        }
    }
}
