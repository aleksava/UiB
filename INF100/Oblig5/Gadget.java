import java.util.Random;
import java.util.Arrays;
import java.util.Collections;

class Gadget { // Dododododo Inspector Gadget
    public static void main (String[] args) {
        double[] array = cooldownSamples(27, 100000);
        double[] counts = countsFromArray(array, 20);
        String[][] array2d = array2dFromCounts(counts);
        printReport(array2d, minFromArray(array), maxFromArray(array));
    }
    
    public static double cooldown(double temperature) {
        Random random = new Random();

        double bodyTemperature = 37;

        bodyTemperature += random.nextGaussian();

        double cooldownTime = Math.log(bodyTemperature / temperature);
        cooldownTime *= 1 / bodyTemperature;

        cooldownTime *= 255 + random.nextGaussian();

        return cooldownTime;
    }

    // Creates and returns an array that contains the results from
    // cooldown() with a specific temperature
    public static double[] cooldownSamples(int temperature, int numSamples) {
        double[] arr = new double[numSamples];
        
        for(int i = 0; i < numSamples; i++) {
            arr[i] = cooldown(temperature);
        }

        return arr;
    }

    // Finds the minimum value in an array
    public static double minFromArray(double[] array) {
        double min = array[0];
        
        for(int i = 0; i < array.length; i++) {
            if(array[i] < min)
                min = array[i];
        }
        
        return min;
    }

    // Finds the maximum value in an array
    public static double maxFromArray(double[] array) {
        double max = array[0];
        
        for(int i = 0; i < array.length; i++) {
            if(array[i] > max)
                max = array[i];
        }
        
        return max;
    }

    // Splits the range of values in an array into equally sized ranges
    // and counts the number of samples that fall within each range
    public static double[] countsFromArray(double[] array, int numRanges) {
        double[] counts = new double[numRanges];
        double max = maxFromArray(array);
        double min = minFromArray(array);
        double rangeSize = (max-min)/(numRanges - 1);

        for(double value : array) {
            for(int i = (int) ((value - min)/rangeSize); i < array.length; i++)
                if((rangeSize * i <= (value - min)) &&    // current value larger than min
                   (value - min) < rangeSize * (i + 1)) { // current value less than max 
                    counts[i]++;
                }
        }

        return counts;
    }

    // Prints each element of the given 2d array
    public static void printArray2d(String[][] arr2D2) { // beep boop
        for(int i = 0; i < arr2D2.length; i++) {
            for(int j = 0; j < arr2D2[i].length; j++) {
                System.out.print(arr2D2[i][j]);
            }
            System.out.println();
        }
    }

    // Converts the array from countsFromArray() into a 2d array
    // that is printable
    public static String[][] array2dFromCounts(double[] counts) {
        final int PRINT_WIDTH = 50;
        String[][] arr2d = new String[counts.length][PRINT_WIDTH];
        double max = maxFromArray(counts);

        // Iterates over the 2D-array and inserts "#" for each sample
        // that is within the current range. " " is used as filler
        for(int i = 0; i < counts.length; i++) {
            for(int j = 0; j < PRINT_WIDTH; j++) {
                if(j < counts[i] * PRINT_WIDTH / max) {
                    arr2d[i][j] = "#";
                } else {
                    arr2d[i][j] = " ";
                }
            }
        }
        
        return arr2d;
    }

    // Creates a report given the rest of the program, prints to terminal
    public static void printReport(String[][] array2d, double arrayMin,
                                   double arrayMax) {
        double step = (arrayMax - arrayMin) / (array2d.length - 1);
        System.out.println("Time since death probablility distribution");
        System.out.printf("Each line corresponds to %.2f hours.\n", step);
        System.out.println("-------------------------------------------------");
        System.out.printf("%.2f hours\n\n\n", arrayMin);
        printArray2d(array2d);
        System.out.printf("\n\n%.2f hours\n", arrayMax);
        System.out.println("-------------------------------------------------");
    }
}
