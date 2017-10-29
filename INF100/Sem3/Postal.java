import java.util.ArrayList;

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
            } else if (command.equals("p")) {
                // print registered parcels to display
            } else if (command.equals("w")) {
                // write registered parcels to file
            } else if (command.equals("c")) {
                // clear registered parcels
            } else if (command.equals("q")) {
                System.out.println("[Quitting]");
                done = true;
            } else {
                System.out.println("[Unknown command]");
            }
        } // end of while
    }

    public static Parcel registerParcel() {
        Parcel parcel = null;

        System.out.println("Please input the sender and recipient in the " +
                           "following format: sender recipient");
        
    }
}
