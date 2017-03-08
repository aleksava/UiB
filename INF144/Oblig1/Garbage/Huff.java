import java.util.*;
import java.io.*;

/**
 * This class contains methods that aid in generation of huffman code using a priority queue.
 * @author Shweta
 *
 */
public class Huff {
	
    //the total number of alphabets in the input file
    private int total_letters;
	
    // the number of alphabets - 26. This will be the max size of the queue
    private int n;
	
    //array containing all symbols from a to z
    private char[] S;
	
    //array containing the frequency of each symbol in S
    private float[] P;
	
    //array containing the huffman code of each symbol in S
    private String[] code;

    //priority queue used for generating the prefix code tree
    private PriorityQueue priorityQueue;
	
    //input file name
    private String fileName;
	
    /**
     * Initializes the arrays S, P and code
     * @param _n
     */
    public Huff(int _n) {		

        n=_n;
        //initialize S with the characters from a to z
        this.S = new char[n + 1];
        char j = 'a';
        for(int i = 1; i <= n; i++, j++) {
            S[i] = j;
        }
		
        //initialize total letters and P with 0
        this.total_letters = 0;
        this.P = new float[n + 1];
        for(int i = 1; i <= n; i++) {
            P[i] = 0;
        }
		
        //initialize code
        this.code = new String[n+1];
        for(int i = 1; i <= n; i++) {
            code[i] = "";
        }
		
    }
	
    /**
     * 1. Parses the file to fill S and compute total_letters
     * 2. Fills the priority queue with TreeNodes containing symbols and the frequency of the symbol in the input file
     * 3. Constructs a prefix code binary tree
     * 4. Generates the huffman code of each symbol using depth 1st
     * 5. Finally produces an output file containing the symbols, their frequencies and code.
     */
    public void run(){
		
        parseFile(fileName);
        fillQueue();
        constructPrefixCodeTree();
        TreeNode node = priorityQueue.extractMin();
        traverseDepthFirst(node, "");
        writeCode();
    }
	
    /**
     * Generates an output file containing the symbols, their frequencies and code.
     */
    public void writeCode(){
        //writing output to the console
        StringBuilder outputString= new StringBuilder();
        outputString.append("Symbol\tFrequency\tCode\n\n");
        float abl = 0;
        for(int i=1; i<= n; i++){
            abl=abl+ this.P[i]*this.code[i].length();
            outputString.append(S[i]+"\t"+P[i]+"\t"+code[i]+"\t"+"\n");
        }
        outputString.append("\nAverage Bits per Letter (ABL) is "+abl);
        System.out.println(outputString.toString());
		
        //rewriting the input file with huffman code in fileName.huffman
        try{
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            outputString = new StringBuilder();
            int input;
            char inputC;
			
            while((input = in.read()) != -1) {
                inputC = (char) input;
                if(!((inputC >= 'a' && inputC <= 'z') || (inputC >= 'A' && inputC <= 'Z'))) continue;
                input = (int)Character.toLowerCase(inputC) - (int)'a';
                outputString.append(code[input]);
            }				
            FileWriter fr = new FileWriter(fileName+".huffman");
            BufferedWriter output= new BufferedWriter(fr);
            output.write(outputString.toString());
            output.close();
        }
        catch(IOException exc){
            exc.printStackTrace();
        }
		
    }
	
    /**
     * Traverses in a depth first manner and generates huffman code for each symbol
     * @param node
     * @param code
     */
    private void traverseDepthFirst(TreeNode node, String code) {
        //only proceed if tree is not null
        if(node != null) {
            //compute the index of the symbol in S
            int index=0;
            for(int i=1;i<=n;i++){
                if(S[i] == node.getSymbol()){
                    index= i;
                    break;
                }
            }
            // store the current code in the code array at the index of the symbol
            this.code[index] = code;
            //traverse left
            this.traverseDepthFirst(node.getLeftChild(), code + "1");
            //traverse right
            this.traverseDepthFirst(node.getRightChild(), code + "0");
        }
		
    }
	
    /**
     * Generates the prefix code tree
     */
    void constructPrefixCodeTree(){
		
        //construct binary tree with the symbols in S stored in PQ
        for(int i = 1; i <= n-1; i++) {
            //create the tree
            TreeNode parent = new TreeNode(' ', 0, null, null);
			
            //extract the minimum from priority queue and make them it the rightchild
            TreeNode rightChild = priorityQueue.extractMin();
            parent.setRightChild(rightChild);
			
            //extract the new minimum from priority queue and make them it the leftchild
            TreeNode leftChild = priorityQueue.extractMin();
            parent.setLeftChild(leftChild);		
            //set the frequency to the combined frequency of leftChild and rightChild
            parent.setFreq(rightChild.getFreq() + leftChild.getFreq());
            priorityQueue.insert(parent);
        }
    }
	
    /**
     * Build the priority queue with the symbol with least frequency on top of the queue
     */
    void fillQueue(){	
        priorityQueue = PriorityQueue.startHeap(n);
        TreeNode node;
        for(int i = 1; i <= n; i++) {
            node = new TreeNode (S[i], P[i], null, null);
            priorityQueue.insert(node);
        }
    }
	
    /**
     * Parses the input file to fill in S and compute total_letters
     * @param fileName
     */
    void parseFile(String fileName){
        BufferedReader input=null;
        // Store the contents of the file in a string
        StringBuilder contents = new StringBuilder();
        try {
            FileReader fr = new FileReader(fileName);
            input = new BufferedReader(fr);

            String line = "";
            while (( line = input.readLine()) != null){
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try {
                input.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        String s = contents.toString();
        int letter;
	
        // Scan each symbol to fill S and compute total_letters
        for(int position = 0; position < s.length(); position++){
            // if the letter is in upper case convert it to lower case
            if(s.charAt(position) >=65 && s.charAt(position) <= 90){
                total_letters++;
                letter = s.charAt(position) +32;
                P[letter-96] = P[letter-96]+1;
            }
            // do not convert if already in lower case
            else if(s.charAt(position) >=97 && s.charAt(position) <= 122){
                total_letters++;
                letter = s.charAt(position);
                P[letter-96] = P[letter-96]+1;
            }
        }
        // For each symbol in S, store the frequency of the symbol in P at the same index as in S
        for (int i = 1; i <= n; i++){
            P[i] = P[i]/total_letters;
        }
    }
	
	
    public static void main(String args[]){
		
        if(args.length < 1){
            System.out.println("Please enter the file as a command line argument");
        }
        Huff huffman=new Huff(26);
        huffman.fileName = args[0];
        huffman.run();
    }

}
