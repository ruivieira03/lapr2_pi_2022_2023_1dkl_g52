package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ImportLegacyDataController;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.confirm;

/**
 * The type Import legacy data ui.
 */
public class ImportLegacyDataUI implements Runnable {
    /**
     * The constant ANSI_GREEN.
     */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**
     * The constant ANSI_RESET.
     */
    public static final String ANSI_RESET = "\u001B[0m";
    private final ImportLegacyDataController CTRL = new ImportLegacyDataController();
    private String filePath;
    
    
    
    @Override
    public void run() {
        System.out.printf("\n=========================== Import Legacy Data ===========================\n\n");

        requestFilePath();

        try {
            if (fileIsValid(filePath) && dataIsConfirmed()) {
                if (CTRL.importData(filePath)) {
                    System.out.println(ANSI_GREEN + "\nData imported successfully!" + ANSI_RESET);
                } else {
                    System.err.println("Data import failed!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("\n" + e.getMessage());
        }
    }

    private void requestFilePath() {
        filePath = Utils.readLineFromConsole("Please enter the file path: ");
    }

    private boolean dataIsConfirmed() {
        System.out.println("Please confirm the file path:");
        return confirm("Confirm (Y/N): ");
    }

    private boolean fileIsValid(String filePath) {
        if (fileIsNotCSV(filePath)) {
            System.out.println("Invalid file format. Only CSV files are supported.");
            return false;
        }
        return true;
    }

    private boolean fileIsNotCSV(String filePath) {
        return !filePath.endsWith(".csv");
    }


}
