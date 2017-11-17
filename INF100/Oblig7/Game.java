class Game {
    public static void main (String[] args) {
        boolean fight = true;
        Pokemon pika = new Pokemon("Pikachu");
        Pokemon bigboy = new Pokemon("Arceus");

        System.out.println("Pre-Fight Status: \n" +
                           pika + "\n" +
                           bigboy + "\n");

        while(fight) {
            
            // Pikachu attacks
            if(pika.isConscious())
                pika.attack(bigboy);
            
            // Arceus attacks
            if(bigboy.isConscious())
                bigboy.attack(pika);
            
            // Fight ends if one of them falls unconscious
            if(!pika.isConscious() || !bigboy.isConscious())
                fight = false;
        }

        System.out.println("Aftermath: \n"
                           + pika + "\n" +
                           bigboy);
    } 
}
