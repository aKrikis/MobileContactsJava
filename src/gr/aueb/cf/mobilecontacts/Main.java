package gr.aueb.cf.mobilecontacts;

import gr.aueb.cf.mobilecontacts.controller.MobileContactController;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final MobileContactController controller = new MobileContactController();

    public static void main(String[] args) {

        String choice;

        while(true) {
            printMenu();
            choice = getToken();
            if (choice.equals("q") || choice.equals("Q")) {
                break;
            }
            handleChoice(choice);
        }

        System.out.println("Thank you for using Mobile Contact App.");
    }

    public static void printMenu() {
        System.out.println("1. Εισαγωγή Επαφής");
        System.out.println("2. Ενημέρωση Επαφής");
        System.out.println("3. Διαγραφή Επαφής");
        System.out.println("4. Αναζήτηση Επαφής");
        System.out.println("5. Προβολή Επαφών");
        System.out.println("Q/q. Έξοδος");
    }

    public static String getToken() {
        return in.nextLine().trim();
    }

    public static void handleChoice(String choice) {

        String firstname;
        String lastname;
        String phoneNumber;
        String response;
        long id;

        switch (choice) {
            case "1" :
                System.out.println("Insert Name, Last Name, Phone Number");
                firstname = getToken();
                lastname = getToken();
                phoneNumber = getToken();
                MobileContactInsertDTO mobileContactInsertDTO = new MobileContactInsertDTO(firstname, lastname, phoneNumber);

                response = controller.insertContact(mobileContactInsertDTO);
                if ( response.startsWith("Ok")) {
                    System.out.println("Successful insert.");
                    System.out.println(response.substring(3)); // to not print "OK\n" we start from place 3
                } else {
                    System.out.println("Insert failure.");
                    System.out.println(response.substring(7));
                }
                break;

            case "2" :
                System.out.println("Insert Phone Number");
                phoneNumber = getToken();
                response = controller.getContactByPhoneNumber(phoneNumber);
                if (response.startsWith("Error")) {
                    System.out.println("Contact not found.");
                    System.out.println(response.substring(3));
                    return;
                }

                System.out.println("Insert failure.");
                System.out.println(response.substring(7));
                System.out.println("Insert current ID:");
                long oldId = Long.parseLong(in.nextLine());
                System.out.println("Insert new name");
                firstname = getToken();
                System.out.println("Insert last name");
                lastname = getToken();
                System.out.println("Insert phone number");
                phoneNumber = getToken();
                MobileContactUpdateDTO mobileContactUpdateDTO = new MobileContactUpdateDTO(oldId, firstname, lastname, phoneNumber);
                response = controller.updateContact(mobileContactUpdateDTO);
                System.out.println(response);
                break;

            case "3" :
                System.out.println("Insert contact id");
                id = Long.parseLong(in.nextLine());
                response = controller.deleteContactById(id);
                if (response.startsWith("Ok")){
                    System.out.println("Successful delete");
                    System.out.println(response.substring(3));
                } else {
                    System.out.println("Delete failure");
                    System.out.println(response.substring(7));
                }
                break;

            case "4" :
                System.out.println("Insert contact id");
                id = Long.parseLong(in.nextLine());
                response = controller.deleteContactById(id);
                if (response.startsWith("Ok")){
                    System.out.println("Successful search");
                    System.out.println(response.substring(3));
                } else {
                    System.out.println("Search failure");
                    System.out.println(response.substring(7));
                }
                break;

            case "5" :
                List<String> mobileContacts = controller.getAllContacts();
                mobileContacts.forEach(System.out::println);
                break;

            default:
                System.out.println("Wrong Choice");
        }
    }
}
