import java.util.Random;
import java.util.Collections;
import java.util.Arrays;

class Cards {
    public static void main (String[] args) {
        String[] array = generateDeck();

        // Shows the work of the task, and describes the output
        
        System.out.println("This is the generated sorted deck: ");
        print(array);

        swap(array, 0, 12);
        System.out.println("\nHere, the first and last are flipped");

        print(array);

        int num = randInt(13);
        System.out.println("\nThis is a randomly generated number less than 14: " + num);

        shuffle(array);
        System.out.println("\nHere, the same array is shuffled");

        print(array);

        array = shuffleDeck();
        System.out.println("\nLast, here the entire process is done through 'shuffleDeck()'");

        print(array);

    }

    // Generates a sorted deck of Hearts, from 1 to 13
    private static String[] generateDeck() {
        String[] arr = new String[13];
        String temp = "";

        for(int i = 0; i < arr.length; i++) {
            temp = "Hearts";
            temp += i+1;
            arr[i] = temp.toString();
        }

        return arr;
    }

    // Swaps the value of the two given slots
    private static void swap(String[] deck, int i, int j) {
        String temp = "";
        temp = deck[i];
        deck[i] = deck[j];
        deck[j] = temp;
    }

    // Prints an array to console
    private static void print(String[] arr) {
        for(int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    // Generates a random integer based on the class Random
    private static int randInt(int max) {
        return (new Random()).nextInt(max+1);
    }

    // Shuffles the array using Javas library
    private static String[] shuffle(String[] deck) {
        Collections.shuffle(Arrays.asList(deck));        
        return deck;
    }

    // Generates and shuffles the deck of hearts
    private static String[] shuffleDeck() {
        return (shuffle(generateDeck()));
    }
}
