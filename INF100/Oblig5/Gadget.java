import java.util.Random;
import java.util.Arrays;
import java.util.Collections;

class Gadget { // Dododododo Inspector Gadget
    public static void main (String[] args) {
        print(countsFromArray(cooldownSamples(27, 10), 5));
        String[][] arr = {{"1", "2"}, {"3", "4"}};
        printArray2d(arr);


        double[] array = {2.0088799960771184, 2.121420889236832, 1.9396865921089017,
                          2.4044747294759574, 2.2430778650951178, 2.083040119880876,
                          2.0595035785038114, 2.1782979876210806, 1.8812817807415378,
                          2.232108837421659};
        
        double[] counts = countsFromArray(array, 10);
        String[][] array2d = array2dFromCounts(counts);
        
        printArray2d(array2d);
    }

    public static void print(double[] array) {
        for(int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
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

    public static double[] cooldownSamples(int temperature, int numSamples) {
        double[] arr = new double[numSamples];
        
        for(int i = 0; i < numSamples; i++) {
            arr[i] = cooldown(temperature);
        }

        return arr;
    }

    public static double minFromArray(double[] array) {
        double min = array[0];
        
        for(int i = 0; i < array.length; i++) {
            if(array[i] < min)
                min = array[i];
        }
        
        return min;
    }

    public static double maxFromArray(double[] array) {
        double max = array[0];
        
        for(int i = 0; i < array.length; i++) {
            if(array[i] > max)
                max = array[i];
        }
        
        return max;
    }

    public static double[] countsFromArray(double[] array, int numRanges) {
        double[] counts = new double[numRanges];
        double max = maxFromArray(array);
        double min = minFromArray(array);
        double rangeSize = (max-min)/(numRanges - 1);

        for(int i = 0; i < array.length; i++) {

            // THIS IS WRONG, FIX THIS
            if(rangeSize * i >= (array[i]-min) && (array[i]-min) < rangeSize * (i+1)) {
                // counts[rangeSize*i] ++; need to control incrementations                
            }
        }

        return counts;
    }

    public static void printArray2d(String[][] arr2D2) { // beep boop
        for(int i = 0; i < arr2D2.length; i++) {
            for(int j = 0; j < arr2D2[i].length; j++) {
                System.out.print(arr2D2[i][j]);
            }
            System.out.println();
        }
    }

    public static String[][] array2dFromCounts(double[] counts) {
        final int PRINT_WIDTH = 50;
        String[][] arr2d = new String[counts.length][PRINT_WIDTH];
        double max = maxFromArray(counts);

        for(int i = 0; i < counts.length; i++) {
            for(int j = 0; j < counts.length; j++) {

                // k = counts[i]
                // THIS IS WRONG, FIX THIS
                if(j < (counts[i] * PRINT_WIDTH / max)) {
                    arr2d[i][j] = "#";
                } else {
                    arr2d[i][j] = " ";
                }
            }
        }
        
        return arr2d;
    }
}
