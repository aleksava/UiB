import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Grading {
    public static void main (String[] args) {
        ArrayList<String> students = getNames("students.csv");
        ArrayList<String> graders = getNames("graders.csv");
        ArrayList<String> assigned = assignGraders(students, graders);
        
        // print(students);
        // print(graders);
        // print(assigned);

        writeFile(assigned, "AssignedGraders.txt");

    }

    public static void print(ArrayList<String> arr) {
        for(int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    } // end of print()

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
        
    } // end of getNames()

    
    public static ArrayList<String> assignGraders(ArrayList<String> students,
                                                  ArrayList<String> graders) {
        ArrayList<String> assigned = new ArrayList<String>();
        int numStudents = students.size();
        int numGraders = graders.size();
        int currGrader = 0;
        int studPerGrader = 0;

        for(int i = 0; i < students.size(); i++) {
            assigned.add(students.get(i) + ", " + graders.get(currGrader));
            studPerGrader ++;

            if(studPerGrader >= numStudents/numGraders) {
                System.out.println("Assigned " + studPerGrader + " students to " +
                                   graders.get(currGrader));
                currGrader++;
                numStudents = numStudents - studPerGrader;
                numGraders = numGraders - 1;
                studPerGrader = 0;
            }
        }

        return assigned;
        
    } // end of assignGraders()

    public static void writeFile(ArrayList<String> assignment, String filename){

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for(int i = 0; i < assignment.size(); i++) {
                writer.write(assignment.get(i) + "\n");
            }
            writer.close();
        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    } // end of writeFile()
    
} // end of class Grading
