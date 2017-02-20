import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RecursiveMaze {

	public static void main(String[] args) {

		Maze topBot = new Maze();

		topBot.printMaze();

		if (topBot.solveTopBot())
			System.out.println("Maze solved!");
		else
			System.out.println("No solution.");
		System.out.println("It took " + topBot.returnStep() + " steps to meet\n");

		topBot.printMaze();
	} // End of main()
	
} // End of RecursiveMaze

class Maze {
	private char[][] mazeGrid;
	int height = 20;
	int width = 30;
	int steps = 0;

	//Constructor that reads the file to the 2D char array
	public Maze() {

		String line;
		int rowNumber = 0;
		mazeGrid = new char[height][width];
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("simple_maze_20x30.txt"));
			while ((line = br.readLine()) != null) {
				for (int i = 0; i < line.length(); i++) {
					mazeGrid[rowNumber][i] = line.charAt(i);
				}
				rowNumber++;
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	} // End of Maze() (Constructor)

	//Prints the maze
	public void printMaze() {
		for (int row = 0; row < mazeGrid.length; row++) {
			for (int column = 0; column < mazeGrid[row].length; column++)
				System.out.print(mazeGrid[row][column]);
			System.out.println();
		}
		System.out.println();
	} // End of printMaze()

	//The start method that initiates the solving of the maze
	public boolean solveTopBot() {
		for (int i = 0; i < 30; i++) {
			if (mazeGrid[0][i] == '1') {
				if (solveTopBot(0, i))
					return true;
			}
		}
		return false;
	} // End of solveTopBot()

	//Solves the maze recursively, finds the shortest past by prioritizing 
	//going right before going down
	public boolean solveTopBot(int row, int column) {
		int endPosBot = 0;
		for (int i = 0; i < width; i++) {
			if (mazeGrid[height - 1][i] == '1')
				endPosBot = i;
		}

		boolean done = false;

		if (valid(row, column)) {

			mazeGrid[row][column] = '-'; // cell has been tried

			if (row == height - 1 && column == endPosBot)
				done = true; // maze is solved
			else {
				done = solveTopBot(row, column + 1); // right
				if (!done)
					done = solveTopBot(row + 1, column); // down
				if (!done)
					done = solveTopBot(row - 1, column); // up
				if (!done)
					done = solveTopBot(row, column - 1); // left
			}
			if (done) { // part of the final path
				steps++;
				mazeGrid[row][column] = '*';
			}
		}
		solveTopBotMiddle(steps / 2);
		return done;
	} // End of solveTopBot()

	public void solveTopBotMiddle(int count) {
		for (int i = 0; i < 30; i++) {
			if (mazeGrid[0][i] == '*') {
				solveTopBotMiddle(count, 0, i);
			}
		}
	} // End of solveTopBotMiddle()

	public void solveTopBotMiddle(int count, int row, int column) {
		if (count == 0) {
			System.out.println("The middle is in the coordinates: " + (row + 1) + " down, and " 
								+ (column + 1) + " across \n and it's marked with an X");
			mazeGrid[row][column] = 'X';

		} else {
			if (mazeGrid[row][column + 1] == '*')
				solveTopBotMiddle(count - 1, row, column + 1); // right
			else if (mazeGrid[row + 1][column] == '*')
				solveTopBotMiddle(count - 1, row + 1, column); // down
			else if (mazeGrid[row - 1][column] == '*')
				solveTopBotMiddle(count - 1, row - 1, column); // up
			else if (mazeGrid[row][column - 1] == '*')
				solveTopBotMiddle(count - 1, row, column - 1); // left
		}
	} // End of solveTopBotMiddle()

	public int returnStep() {
		return steps / 2;
	} // End of returnStep()

	private boolean valid(int row, int column) {
		boolean result = false;
		if (row >= 0 && row < height && column >= 0 && column < width)
			if (mazeGrid[row][column] == '1')
				result = true;

		return result;
	} // End of valid()

} // End of Maze