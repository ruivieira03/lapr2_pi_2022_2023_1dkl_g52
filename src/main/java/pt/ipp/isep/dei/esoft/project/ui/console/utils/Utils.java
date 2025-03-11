package pt.ipp.isep.dei.esoft.project.ui.console.utils;

import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Message;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.*;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Paulo Maio pam@isep.ipp.pt
 * @author David Gonçalves
 */
public class Utils {

    static public String readLineFromConsole(String prompt) {
        try {
            System.out.println("\n" + prompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static public int readIntegerFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                int value = Integer.parseInt(input);

                return value;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number!");
            }
        } while (true);
    }

    static public double readDoubleFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                double value = Double.parseDouble(input);

                return value;
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    public static LocalDate readLocalDate(String prompt) {

        System.out.println(prompt);

        int year = 0;
        do {
            year = readIntegerFromConsole("Type Year:");
        } while (year < 0 && year < LocalDate.now().getYear());

        int month = 0;

        do {
            month = readIntegerFromConsole("Type Month:");
        } while (month < 1 || month > 12);

        int day = 0;
        do {
            day = readIntegerFromConsole("Type Day:");
        } while ((day < 1) || (day > YearMonth.of(year, month).lengthOfMonth()));

        return LocalDate.of(year, month, day);
    }

    public static LocalTime readLocalTime(String prompt) {
        System.out.println(prompt);

        int hour = 0;
        do {
            hour = readIntegerFromConsole("Type hour:");
        } while (hour < 0 || hour > 23);

        int minute = 0;
        do {
            minute = readIntegerFromConsole("Type minutes:");
        } while (minute < 0 || minute > 59);

        return LocalTime.of(hour, minute);
    }


    static public boolean confirm(String message) {
        String input;
        do {
            input = Utils.readLineFromConsole("\n" + message + "\n");
        } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("N"));

        return input.equalsIgnoreCase("Y");
    }

    static public Object showAndSelectOne(List list, String header) {
        showList(list, header);
        return selectsObject(list);
    }

    static public int showAndSelectIndex(List list, String header) {
        showList(list, header);
        return selectsIndex(list);
    }

    static public int showPropertySaleRequestAndSelectIndex(List<PropertySaleRequestDTO> list, String title) {
        showPropertySaleRequestsList(list, title);
        return selectsIndex(list);
    }

    public static Object showPropertySaleRequestAndSelectOne(List<PropertySaleRequestDTO> list, String title) {
        showPropertySaleRequestsList(list, title);
        return selectsObject(list);
    }

    static public int showPropertySaleAndSelectIndex(List<PropertySaleDTO> list, String title) {
        showPropertySaleList(list, title);
        return selectsIndex(list);
    }

    static public int showPurchaseRequestsListAndSelectIndex(List<PurchaseRequestDTO> list, String title) {
        showPurchaseRequestsList(list, title);
        return selectsIndex(list);
    }

    static public int showVisitRequestsListAndSelectIndex(List<VisitRequestDTO> list, String title) {
        showVisitRequestsList(list, title);
        return selectsIndex(list);
    }

    static public int showClientNotificationsListAndSelectIndex(List<Message> list, String title){
        showClientNotificationsList(list, title);

        return selectsIndex(list);
    }
    static public void showList(List list, String header) {
        System.out.println(header);

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println(index + ". " + o.toString());
        }
        System.out.println();
        System.out.println("0 - Cancel");
    }

    static public Object selectsObject(List list) {
        int input;
        // debug this method
        do {
            input = readIntegerFromConsole("Type your option: ");
        } while (input < 0 || input > list.size());

        if (input == 0) {
            return null;
        } else {
            return list.get(input - 1);
        }
    }

    static public int selectsIndex(List list) {
        String input;
        Integer value;
        do {
            input = Utils.readLineFromConsole("Type your option: ");
            try {
                value = Integer.valueOf(input);
            } catch (NumberFormatException ex) {
                value = -1;
            }
        } while (value < 0 || value > list.size());

        return value - 1;
    }

    static public void showClientNotificationsList(List<Message> messages, String title){
        System.out.println(title);

        int index = 1;

        for (Message message : messages){
            System.out.printf("%d - %s", index, message.getMessage().toString());
            index++;
        }

        System.out.println();
        System.out.println("Exit - 0");
    }

    static public void showPropertySaleList(List<PropertySaleDTO> propertySaleListDTO, String title) {
        System.out.println(title);

        int index = 1;

        System.out.println("|___|  Type   | Business |  Price   |       State        |      District      |        City        |                 Street                 | Zip Code |   Area   |Distance from center| Bedrooms | Bathrooms | Parking |      Available Equipment     | Basement | Inhabitable Loft | Sun Exposure |");
        System.out.println("|---|:--------|:---------|:---------|:-------------------|:-------------------|:-------------------|:---------------------------------------|:---------|:---------|:-------------------|:---------|:----------|:--------|:-----------------------------|:---------|:-----------------|:-------------|");

        for (PropertySaleDTO propertySaleDTO : propertySaleListDTO) {
            System.out.printf("|%2d.|%s%n", index, propertySaleDTO.toString());
            index++;
        }

        System.out.println();
        System.out.println("Exit - 0");

    }

    static public void showPropertySaleRequestsList(List<PropertySaleRequestDTO> list, String title) {
        System.out.println(title);

        int index = 1;

        System.out.println("|___|    Client Name     |  Type   | Business |Requested price |       State        |      District      |        City        |                 Street                 | Zip Code |   Area   |Distance from center| Bedrooms | Bathrooms | Parking |      Available Equipment     | Basement | Inhabitable Loft | Sun Exposure |");
        System.out.println("|---|:-------------------|:--------|:---------|:---------------|:-------------------|:-------------------|:-------------------|:---------------------------------------|:---------|:---------|:-------------------|:---------|:----------|:--------|:-----------------------------|:---------|:-----------------|:-------------|");

        for (PropertySaleRequestDTO dto : list) {
            System.out.printf("|%2d.|%s%n", index, dto.toString());
            index++;
        }

        System.out.println();
        System.out.println("Exit - 0");
    }

    public static void showPurchaseRequestsList(List<PurchaseRequestDTO> list, String title) {
        System.out.println(title);

        System.out.println("|___|     Client Name    | Phone Number | Request Amount $ |");
        System.out.println("|---|:-------------------|:-------------|:-----------------|");

        int index = 1;
        for (PurchaseRequestDTO dto : list) {
            System.out.printf("|%2d.|%s%n", index, dto.toString());
            index++;
        }

        System.out.println("Exit - 0");

    }

    static public void showVisitRequestsList(List<VisitRequestDTO> list, String title) {
        System.out.println(title);

        int index = 1;

        // FIXME: change this table
        System.out.println("|___|    Client Name     |  Type   | Business |Requested price |       State        |      District      |        City        |                 Street                 | Zip Code |   Area   |Distance from center| Bedrooms | Bathrooms | Parking |      Available Equipment     | Basement | Inhabitable Loft | Sun Exposure | Visit Date | Start Hour |  End Hour  |");
        System.out.println("|---|:-------------------|:--------|:---------|:---------------|:-------------------|:-------------------|:-------------------|:---------------------------------------|:---------|:---------|:-------------------|:---------|:----------|:--------|:-----------------------------|:---------|:-----------------|:-------------|------------|------------|------------|");

        for (VisitRequestDTO dto : list) {
            System.out.printf("|%2d.|%s%n", index, dto.toString());
            index++;
        }

        System.out.println();
        System.out.println("Exit - 0");
    }

    public static void showStoreSubLists(List<List<StoreDTO>> list) {


        for (int subListIndex = 0; subListIndex < list.size(); subListIndex++) {
            System.out.println("SubList - " + (subListIndex + 1));

            System.out.println("|___|        Name        |   Properties |");
            System.out.println("|---|:-------------------|:-------------|");

            int index = 1;
            for (StoreDTO dto : list.get(subListIndex)) {
                System.out.printf("|%2d.|%s%n", index, dto.toString());
                index++;
            }
            System.out.println();
        }
        System.out.println("Exit - 0");

    }

    static public void showPropertySoldList(List<PropertySoldDTO> list, String title) {
        System.out.println(title);

        int index = 1;

        System.out.println("|___|  Type   | Business |  Price   | Sold Price |       State        |      District      |        City        |                 Street                 | Zip Code |   Area   |Distance from center| Bedrooms | Bathrooms | Parking |      Available Equipment     | Basement | Inhabitable Loft | Sun Exposure |");
        System.out.println("|---|:--------|:---------|:---------|:-----------|:-------------------|:-------------------|:-------------------|:---------------------------------------|:---------|:---------|:-------------------|:---------|:----------|:--------|:-----------------------------|:---------|:-----------------|:-------------|");
        for (PropertySoldDTO dto : list) {
            System.out.printf("|%2d.|%s%n", index, dto.toString());
            index++;
        }
    }

    public static String ReadProperties(String property) {
        Properties props = new Properties();
        try {
            InputStream in = new FileInputStream("src/main/resources/ConfigFiles/config.properties");
            props.load(in);
            in.close();

        } catch (IOException ignored) {
        }

        return props.getProperty(property);
    }

    public static LocalDate readDateFromConsole(String message) {
        String input;
        LocalDate date = null;
        do {
            input = Utils.readLineFromConsole(message);
            try {
                date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid date format");
            }
        } while (date == null);

        return date;
    }
}
