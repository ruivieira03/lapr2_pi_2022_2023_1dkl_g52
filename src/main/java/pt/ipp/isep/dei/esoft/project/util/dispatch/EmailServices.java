package pt.ipp.isep.dei.esoft.project.util.dispatch;

import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class EmailServices {
    private static final String configEmail = "src/main/resources/ConfigFiles/configEmail.config";
    private static final String emailValid = "validStrings";

    public boolean isEmailValid(String email) {
        Properties properties = new Properties();
        try {
            FileInputStream file = new FileInputStream(configEmail);
            properties.load(file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] input = email.split("@");
        String[] validStrings = properties.getProperty(emailValid).split(",");
        for (String validString : validStrings) {
            if (validString.trim().equalsIgnoreCase(input[1])) {
                return true;
            }
        }
        return false;
    }

    public OperationResult createFile(String recipient, String subject, String body, VisitRequestDTO visitRequestDTO) {


        try {
            String fileName = "Emails.txt";
            File file = new File(fileName);
            if (file.createNewFile()) {

                System.out.println("Email file created: " + file.getAbsolutePath());
                FileWriter writer = new FileWriter(fileName, true);
                writer.write("--------------------------------------------------\n");
                writer.write("From: ");
                writer.write("To: " + recipient + "\n");
                writer.write("Subject: " + subject + "\n\n");
                writer.write("Visit request: " + visitRequestDTO.propertySale.property.zipCode +", " + visitRequestDTO.propertySale.property.street+"\n");
                writer.write("Visit start:" + visitRequestDTO.visitStart.toString() + "\n" + "Visit end: " + visitRequestDTO.visitEnd.toString() + "\n\n");
                writer.write(body+"\n");
                writer.close();
                System.out.println("Email created: " + file.getAbsolutePath());

            } else if (file.exists()) {

                FileWriter writer = new FileWriter(fileName, true);
                writer.write("--------------------------------------------------\n");
                writer.write("To: " + recipient + "\n");
                writer.write("Subject: " + subject + "\n\n");
                writer.write("Property: " + visitRequestDTO.propertySale.property.zipCode +", " + visitRequestDTO.propertySale.property.street+"\n");
                writer.write("Visit start: " + visitRequestDTO.visitStart.toString() + "\n" + "Visit end: " + visitRequestDTO.visitEnd.toString() + "\n\n");
                writer.write(body+"\n");
                writer.close();
                System.out.println("Email created: " + file.getAbsolutePath());

            }
        } catch (IOException e) {
            e.printStackTrace();
            return OperationResult.failed("Error creating the file!");
        }
        return OperationResult.successfull();
    }
}
