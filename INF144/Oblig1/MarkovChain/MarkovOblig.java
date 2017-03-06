import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class MarkovOblig {
    public static void main (String[] args) {
        int order = 3;
        String state = "det";
        String output = state;
        String nextLetter = "";

        // Getting length of ngrams from user
        try {
            order = Integer.parseInt(args[0]);
        }
        catch(NumberFormatException e) {
            System.out.println("The input was not a number.");
            e.printStackTrace();
        }

        // Getting the start state from the user
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("The length of the state must match your approximation input. ");
            System.out.print("Please enter the starting state: ");
            state = br.readLine();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        // Matching up states and length of ngrams
        if(state.length() != order) {
            System.out.println("The length of the state must match the input integer, please try again.");
            //System.exit(-1);
        }
        
        MarkovModel model = new MarkovModel(order);

        // The file defaults to a Norwegian fairytale
        model.loadData("Folktale.txt");

        // Generates the randomly generated text from the MarkovModel
        output = state;
        for(int i = 0; i < 100; i++) {
            nextLetter = model.getNextLetter(state);
            output += nextLetter;
            state = state.substring(1, order) + nextLetter;
        }

        System.out.println("Here is the generated text: \n" + output);
    }
}
