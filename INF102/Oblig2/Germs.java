
import java.io.*;

public class Germs {
	private static final int M = 4650000;
	public static void main(String[] args) {
		short[] arr = new short[M];
		int filter = 0;
		int counter = 0;
		String line;
		BufferedReader reader = null;
		
		//Hash1 with 1M
		try {
			reader = new BufferedReader(new FileReader("Ecoli.1M.36mer.txt"));
			StringBuilder bobTheBuilder = new StringBuilder(); 
			line = reader.readLine();
			while(line != null)  {
				bobTheBuilder.append(line);
				bobTheBuilder.append(System.lineSeparator());
				
				String key = line;
				
				int hash = hashAway(key);
				int hash2 = hashAway2(key);
				int hash3 = hashAway3(key);
				
				line = reader.readLine();
				
				arr[hash] = 1;
				arr[hash2] = 1;
				arr[hash3] = 1;
				filter ++;
			}			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			reader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		//Check in 2M
		try {
			reader = new BufferedReader(new FileReader("Ecoli.2M.36mer.txt"));
			StringBuilder bobTheBuilder = new StringBuilder(); 
			line = reader.readLine();
			while((line) != null)  {
				bobTheBuilder.append(line);
				bobTheBuilder.append(System.lineSeparator());
				
				String key = line;
				if(arr[hashAway(key)] == 1 && arr[hashAway2(key)] == 1 && arr[hashAway3(key)] == 1) {
					counter ++;
				}
				line = reader.readLine();
			}			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			reader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		int falsePos = counter - filter;
		double percent = (double) falsePos / ((double) filter + (double) falsePos) * 100;
		System.out.println("Counter: " + counter);
		System.out.println("Filter: " + filter);
		System.out.println("Total false positives: " + falsePos);
		System.out.printf("In percent: %.2f", percent);
		System.out.println("%");
		print(arr);
	}
	
	public static int hashAway(String s) {
		return (s.hashCode() & 0x7fffffff) % M;
	}
	
	public static int hashAway2(String s) {
		return(s.hashCode() * 127 & 0x7fffffff) % M;
	}
	
	public static int hashAway3(String s) {
		char[] tomHanks = "Wilson".toCharArray();
		return(s.hashCode() * Character.getNumericValue(tomHanks[5]) & 0x7fffffff) % M;
	}
	
	public static void print(short[] arr) {
		try {
			PrintWriter write = new PrintWriter("finito.txt", "UTF-8");
			for(short s: arr) {
				write.print(s);
			}
			write.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
