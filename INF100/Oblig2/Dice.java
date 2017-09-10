import java.io.*;

class Dice {
    public static void main (String[] args) {

        // Takes in target from user through terminal
        int target = takeInput("Enter a number between 2 and 12: ", 1, 13);

        // Takes in number of required matches from user through terminal
        int matches = takeInput("Enter the number of times it needs to match: ", 0, 2147483647);

        // Calling method to throw dice
        throwDie(target, matches);
    }

    // Method that throws 2 dice, matching them to a target
    // a number of times based on input from user
    public static void throwDie(int target, int matches) {
        int curMatch = 0;
        int die1 = -1;
        int die2 = -1;
        int sum = 0;
        
        for(int rolls = 1; rolls > 0; rolls++) {
            die1 = (int)(1 + 6*Math.random());
            die2 = (int)(1 + 6*Math.random());
            sum = die1 + die2;
            
            System.out.println("Rolled: (" + die1 + ", " + die2 + "), Total of: " + sum);
            
            if(sum == target && ++curMatch == matches) {
                System.out.println("Got the target mathing " + matches +
                                   " times in " + rolls + " roll(s). Hurray...");
                break;
            }
        }
    }

    // Input method that returns input from user based on
    // message and scope of required input
    public static int takeInput(String message, int numMin, int numMax) {
        boolean fuckup = true;
        int number = 0;
        
        while(fuckup) {
            System.out.print(message);
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                number = Integer.parseInt(input.readLine());
                if(number > numMin && number < numMax)
                    fuckup = false;
                else
                    System.out.println("Not within the scope of numbers...");
            }
            catch (NumberFormatException ex) {
                System.out.println("Not a number, you idiot...");
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return number;
    }
}
