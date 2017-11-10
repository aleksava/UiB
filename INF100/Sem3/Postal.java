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
                System.out.print("\nInput a filename: \n>");
                String filename = sc.next();
                parcelsToFile(registeredParcels, filename);
                
            } else if (command.equals("c")) {
                // clear registered parcels
                System.out.println("\nAre you sure you want to delete all " +
                                   registeredParcels.size() + " parcel(s)?");
                System.out.print("[Yes/No] \n>");
                String s = sc.next();
                
                if(s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes")) {
                    registeredParcels.clear();
                    System.out.println("The parcel(s) have been cleared.\n");
                } else {
                    System.out.println("The parcel(s) were NOT cleared.\n");
                }
                
            } else if (command.equals("q")) {
                // exiting loop and program
                System.out.println("[Quitting]");
                done = true;
                
            } else {
                System.out.println("[Unknown command]");
            }
        } // end of while
        
    } // end of main

    
    // Method for registering parcels, initiates registration for
    // both sender and recipient using registerPerson()
    public static Parcel registerParcel() {
        Parcel parcel = null;
        Address sendAdd = new Address();
        Address reciAdd = new Address();

        Scanner sc = new Scanner(System.in);
        
        System.out.println("\nPlease register the sender");
        Person sender = registerPerson();

        System.out.println("\nPlease register the recipient: ");
        Person recipient = registerPerson();

        System.out.println("\n[Parcel registered]\n");
        
        return new Parcel(sender, recipient);
        
    } // end of method

    
    // Method for printing all parcels to terminal
    public static void parcelsToTerminal(ArrayList<Parcel> arrL) {
        System.out.println();
        
        for(int i = 0; i < arrL.size(); i ++) {
            System.out.println(arrL.get(i) + "\n");
        }
    } // end of method

    
    // Method for writing all parcels to file, given filename
    public static void parcelsToFile(ArrayList<Parcel> arrL, String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            
            for(int i = 0; i < arrL.size(); i++) {
                writer.write(arrL.get(i) + "\n");
            }
            
            writer.close();
            
        } catch(IOException e) {
            System.out.println("Something went wrong when writing the file.\n");
            e.printStackTrace();
        } // end of try-catch
        
        System.out.println("The file " + filename + " was created.\n");
    } // end of method

    
    // Method for registering each person, applies to both sender
    // and to recipient. Asks for details of name, and address
    public static Person registerPerson() {
        Address address = new Address();
        
        System.out.print("Enter name: \n>");
        String name = inputString();
        
        System.out.print("Enter street name: \n>");
        address.addStreet(inputString());

        System.out.print("Enter street number: (Numbers only) \n>");
        address.addStreetNum(inputInt());

        System.out.print("Enter postal code: (Numbers only) \n>");
        address.addPostCode(inputInt());

        System.out.print("Enter town: \n>");
        address.addTown(inputString());

        System.out.print("Enter country: \n>");
        address.addCountry(inputString());

        return new Person(name, address);
    } // end of method

    
    // Simplifying input of strings in registerPerson
    public static String inputString() {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        return s;
    } // end of method

    
    // Taking input from user and parsing to int, handles
    // errors using .matches if input isn't numbers.
    public static int inputInt() {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        // Checks if String matches the given regular expression
        while(!s.matches("[0-9]+")) {
            System.out.print("Invalid input, only numbers allowed. " +
                             "Try again \n>");
            
            s = sc.nextLine();
        } // end of while

        return Integer.parseInt(s);
    } // end of method
    
} // end of class
