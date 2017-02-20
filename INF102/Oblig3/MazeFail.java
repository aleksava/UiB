import java.io.FileReader;
import java.io.BufferedReader; 
import java.io.FileNotFoundException;
import java.io.IOException;


public class MazeFail {
	
	private int column;
	private int row;
	//True if wall north/south/east/west of given tile is a wall
	private boolean[][] north;
	private boolean[][] south;
	private boolean[][] east;
	private boolean[][] west;
	private boolean marked[][];
	private boolean metUp = false;
	
	public MazeFail(int c, int r) { //C = 20, R = 30
		column = c;
		row = r;
		
		north = new boolean[c][r];
		south = new boolean[c][r];
		east = new boolean[c][r];
		west = new boolean[c][r];
		marked = new boolean[c][r];
		
	}
	
	public static void main(String[] args) {
		MazeFail lab = new MazeFail(20, 30);
		int[][] myFirstMaze = lab.readFile("simple_maze_20x30.txt");
		print(myFirstMaze);
		lab.solve(myFirstMaze);
		print(myFirstMaze);
		
		//boolean[] test = new boolean[5];
		//System.out.println("Test boolean: " + test[0]);
	}
	
	public boolean solveTopBot() {
		return false;
	}
	
	public void solve(int[][] arr) {
		int start = this.findStart(arr);
		for(int row = 0; row < arr.length; row++) {
			for(int column = 0; column < arr[row].length; column++) {
				try {
					if(row == 0 && column == 0 && start != -1)
						column = start;
					
					marked[row][column] = true;
					
//					if(arr[column-1][row-1] == '0')
//						north[column][row] = true;
//					if(arr[column-1][row] == '0')
//						west[column][row] = true;
//					if(arr[column+1][row] == '0')
//						east[column][row] = true;
//					if(arr[column+1][row+1] == '0')
//						south[column][row] = true;
					
				} catch(ArrayIndexOutOfBoundsException e) {
					//System.out.print("outOf");
				}
			}
			System.out.println();
		}
		
//		print(north);
		
	}
	
	public int findStart(int[][] arr) {
		for(int i = 0; i < arr[0].length; i++) {
			if(arr[0][i] == '1')
				return i;
		}
		return -1;
	}
	
	public int findExit(char[][] arr) {
		for(int i = 0; i < arr[arr.length - 1].length; i++) {
			if (arr[arr.length - 1][i] == '1')
				return i;
		}
		return -1;
	}
	
	public int[] findPath(int row, int column, int[][] arr) {
		try {
			if(arr[row][column-1] != 0 && !marked[row][column-1]) {
				return new int[] {row, column-1};
			}
			if(arr[row-1][column] != 0 && !marked[row-1][column]) {
						
			}
			if(arr[row][column+1] != 0 && !marked[row][column+1]) {
				
			}
			if(arr[row+1][column] != 0 && !marked[row+1][column]) {
				
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			//eh, do nothing
		}
		return new int[] {-1};
	}
	
	private int[][] readFile(String filename) {
		String line = "";
		int row = 0;
		int[][] tempArr = new int[20][30];
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			while((line = reader.readLine()) != null) {
				for(int i = 0; i < line.length(); i++) {
					tempArr[row][i] = Character.getNumericValue(line.charAt(i));
				}
				row++;
			}
			
			reader.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return tempArr;
	}
	
	public static void print(int[][] arr) {
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				//if(i == 0 && j == 0) arr[i][j] = '7'; Finding [0][0]
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
		System.out.println("\n");
	}
	
	public static void print(boolean[][] arr) {
		System.out.println();
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				//if(i == 0 && j == 0) arr[i][j] = '7'; Finding [0][0]
				if(arr[i][j]) System.out.print("");
				else System.out.print("0");
			}
			System.out.println();
		}
		System.out.println("\n");
	}
}
