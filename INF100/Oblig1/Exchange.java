import java.io.*;

class Exchange {
    public static void main (String[] args) {
        double input = 0, output = 0, rate = -1;
        boolean fuckup = true;
        String currency = "";

        // Takes input from user to set rate of exchange
        while(rate == -1){ 
            System.out.print("Enter what you'd like to exchange to: ");
            currency = takeInput().toUpperCase();
            rate = checkCurrency(currency);
        } // End of while

        // Takes input from user to set amount to be exchanged
        while(fuckup){  
            System.out.print("Enter how much you'd like to exchange: ");
            
            try {
                input = Integer.parseInt(takeInput());
                fuckup = false;
            } catch (NumberFormatException e) {
                System.out.println("Not a valid number, how hard is this?");
            } // End of try-catch
            
            output = exchange(input, rate);
        } // End of while
        
        System.out.print(input + " NOK equals ");
        System.out.printf("%.2f", output);
        System.out.println(" " + currency);
    } // End of main

    // Method that takes input from user
    public static String takeInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        
        try {
            input = reader.readLine().trim();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return input;
    } // End of takeInput 

    // Exhanges currency based on gived amount and rate of exchange
    public static double exchange(double nok, double rate) {
        return nok * rate;
    } // End of Exchange

    // Checks if given currency is valid, returns current rate if true
    public static double checkCurrency(String in) {
        if(in.equalsIgnoreCase("EUR")) return 0.1080;
        else if(in.equalsIgnoreCase("USD")) return 0.1288;
        else if(in.equalsIgnoreCase("GBP")) return 0.0995;
        else {
            System.out.println("Not a supported currency, try again...");
            return -1;
        }
    } // End of checkCurrency
    
} // End of class Exchange
