public class Test {
    public static void main (String[] args) {
        TextModel m = new TextModel();

        m.loadData("Folktale.txt");

        String output = "";

        String state = "en ";
        output += state;

        for(int i = 0; i < 100; i++) {
            String nextLetter = m.getNextLetter(state);
            output += nextLetter;
            state = state.substring(1, 3) + nextLetter;
        }

        System.out.println(output);
    }
}
