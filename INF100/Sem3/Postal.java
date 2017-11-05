
/*


NOTE:
FIX LIST:
Fix input with name and address, need firstname lastname "Aleksander Våge" "Vestre Torggaten"
Fix printing and prompting of filling address
     Follow guideline of menu
More information to user, that things executed properly
Formatting of file, '^M' ?? And nextline between items


*/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

class Postal {
    public static void main (String[] args) {
        ArrayList<Parcel> registeredParcels = new ArrayList<Parcel>();
        Scanner sc = new Scanner(System.in);
        boolean done = false;
        
        while (!done) {
            System.out.printf("Enter command (%d parcel(s) registered)"
                              + "%nr: register parcel"
                              + "%np: print parcels to display"
                              + "%nw: write parcels to file"
                              + "%nc: clear parcel registeredParcels"
                              + "%nq: quit%n> ", registeredParcels.size());
            String command = sc.next();
            
            if (command.equals("r")) {
                // register parcel
                Parcel parcel = registerParcel();
                registeredParcels.add(parcel);
            } else if (command.equals("p")) {
                // print registered parcels to display 
                parcelsToTerminal(registeredParcels);
            } else if (command.equals("w")) {
                // write registered parcels to file
                System.out.printf("Input a filename: "
                                  + "%n> ");

                String filename = sc.next();
                
                parcelsToFile(registeredParcels, filename);
            } else if (command.equals("c")) {
                // clear registered parcels
                System.out.println("Are you sure you want to delete all " +
                                   registeredParcels.size() + " parcel(s)?");
                String s = sc.next();
                if(s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes")) {
                    registeredParcels.clear();
                    System.out.println("The parcel(s) have been cleared.");
                } else 
                    System.out.println("The parcel(s) were NOT cleared.");
            } else if (command.equals("q")) {
                System.out.println("[Quitting]");
                done = true;
            } else {
                System.out.println("[Unknown command]");
            }
        } // end of while
        
    } // end of main

    // parsel has two persons, sender and recipient
    // person has String name and Address address
    // address has String street, int streetnumber, int postalcode, string town, string country
    public static Parcel registerParcel() {
        Parcel parcel = null;
        Address sendAdd = new Address();
        Address reciAdd = new Address();

        Scanner sc = new Scanner(System.in);
        
        System.out.print("Please input the name of the sender: ");
        String senderName = sc.next();

        System.out.println("Please fill in the information of the sender");
        fillAddress(sendAdd);

        System.out.print("Please input the name of the recipient: ");
        String reciName = sc.next();
        
        System.out.println("Please fill in the information of the recipient");
        fillAddress(reciAdd);

        parcel = new Parcel(new Person(senderName, sendAdd), new Person(reciName, reciAdd));

        return parcel;
        
    }

    public static void parcelsToTerminal(ArrayList<Parcel> arrL) {
        for(int i = 0; i < arrL.size(); i ++) {
            System.out.println(arrL.get(i));
        }
    }

    public static void parcelsToFile(ArrayList<Parcel> arrL, String filename) {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(filename, "UTF-8");
        } catch(IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < arrL.size(); i++) {
            try {
                writer.println(arrL.get(i) + "\n");
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        }

        writer.close();

        System.out.println("The file " + filename + " was created.");
    }

    public static Address fillAddress(Address add) {
        boolean condition = true;
        Scanner sc = new Scanner(System.in);

        condition = true;
        while(condition) {
            System.out.print("Please input the street: ");
            add.addStreet(sc.next());
            condition = false;
        }

        condition = true;
        while(condition) {
            System.out.print("Please input the street number: ");
            try {
                add.addStreetNum(Integer.parseInt(sc.next()));
                condition = false;
            } catch(NumberFormatException e) {
                System.out.println("This was not a number, please try again");
                condition = true;
            }
        }

        condition = true;
        while(condition) {
            System.out.print("Please input the postal code: ");
            try {
                add.addPostCode(Integer.parseInt(sc.next()));
                condition = false;
            } catch(NumberFormatException e) {
                System.out.println("This was not a number, please try again");
                condition = true;
            }
        }

        condition = true;
        while(condition) {
            System.out.print("Please input the town: ");
            add.addTown(sc.next());
            condition = false;
        }

        condition = true;
        while(condition) {
            System.out.print("Please input the country: ");
            add.addCountry(sc.next());
            condition = false;
        }

        return add;
    }

}
