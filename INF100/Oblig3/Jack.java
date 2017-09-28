class Jack {

    // Jacks position on the grid, in x and y
    static int posX = 5;
    static int posY = 5;

    public static void main (String[] args) {
        final int loops = 100000;
        double steps = 0;
        
        for(int i = 0; i < loops; i++) {

            // Setting start position for each simulation
            posX = 5;
            posY = 5;

            steps += simulate();
        }
        
        steps = steps/loops; // Averaging over all loops
        
        System.out.print("Oops! Jack was eaten by a shark " + loops + " times! \nHe walked ");
        System.out.printf("%.3f", steps);
        System.out.println(" steps (averaged over " + loops + " times)");
    }

    // Simulates the movement of Jack the Pirate on the island of Mojang
    private static int simulate() {

        // Conditions contained in variables for readabilty
        boolean checkX = (posX > 0) && (posX <= 10);
        boolean checkY = (posY > 0) && (posY <= 10);
        int simStep = 0;
        
        while(checkX && checkY) {
            move();
                
            checkX = (posX > 0) && (posX <= 10); // updating x condition
            checkY = (posY > 0) && (posY <= 10); // updating y condition
                
            simStep ++;
        }

        // Returns number of steps from this simulation
        return simStep;
    }

    // Moves Jack the Pirate, either north, east, south, or west
    private static void move() {
        int direction = (int) (Math.random() * 4);
        
        // north = 0, east = 1, south = 2, west = 3
        switch (direction) {
        case 0: posY ++;
            break;
        case 1: posX ++;
            break;
        case 2: posY --;
            break;
        case 3: posX --;
            break;
        default: System.out.println("Something very wrong happend!");
            System.exit(1);
            break;
        }
    }
}
