import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Grading {
    public static void main (String[] args) {
        ArrayList<String> students = getNames("students.csv");
        ArrayList<String> graders = getNames("graders.csv");
        ArrayList<String> assigned = assignGraders(students, graders);
                
        // print(students);
        // print(graders);
        //print(assigned);
    }

    public static void print(ArrayList<String> arr) {
        for(int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    }

    public static ArrayList<String> getNames(String filename) {
        ArrayList<String> arrL = new ArrayList<String>();
        String temp = "";
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            
            while((temp = br.readLine()) != null) {
                arrL.add(temp);
            }
            
        } catch(FileNotFoundException e) {
            System.out.println("Cannot find the file: '" + filename +
                               "'. Please try again");
            e.printStackTrace();
            
        } catch(IOException ex) {
            System.out.println("Something went wrong reading the file");
            ex.printStackTrace();
        }

        return arrL;
    }

    public static ArrayList<String> assignGraders(ArrayList<String> students,
                                                  ArrayList<String> graders) {
        ArrayList<String> assigned = new ArrayList<String>();
        int[] assignments = new int[graders.size()];
        String temp = "";
        int grader = 0;
        int num = 0;

        for(int i = 0; i < students.size(); i++) {
            assigned.add(students.get(i) + ", " + graders.get(num));
            assignments[num++] ++;

            if(num == graders.size())
                num = 0;

            /*
            if(num % (students.size()/graders.size()) == 0) {
                System.out.println("Assigned " + num + " students to " +
                                   graders.get(grader));
                grader++;
                num = 0;
            } */
        }

        for(int j = 0; j < assignments.length; j++) {
            System.out.println("Assigned " + assignments[j] + " students to " +
                               graders.get(j));
        }

        return assigned;
    }
}
