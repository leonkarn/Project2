
// Name: Leonidas Karnesis
// USC NetID: karnesis
// CS 455 PA3
// Spring 2018


import java.util.LinkedList;

/**
 * Maze class
 * 
 * Stores information about a maze and can find a path through the maze (if
 * there is one).
 * 
 * Assumptions about structure of the maze, as given in mazeData, startLoc, and
 * endLoc (parameters to constructor), and the path: -- no outer walls given in
 * mazeData -- search assumes there is a virtual border around the maze (i.e.,
 * the maze path can't go outside of the maze boundaries) -- start location for
 * a path is maze coordinate startLoc -- exit location is maze coordinate
 * exitLoc -- mazeData input is a 2D array of booleans, where true means there
 * is a wall at that location, and false means there isn't (see public FREE /
 * WALL constants below) -- in mazeData the first index indicates the row. e.g.,
 * mazeData[row][col] -- only travel in 4 compass directions (no diagonal paths)
 * -- can't travel through walls
 * 
 */

public class Maze {

	public static final boolean FREE = false;
	public static final boolean WALL = true;
	private static final boolean VISIT = true;// true if having visited some specific coordinates
	private MazeCoord start;
	private MazeCoord exit;
	private boolean[][] maze;
	private LinkedList<MazeCoord> path = new LinkedList<MazeCoord>();
	private boolean[][] visited;//saves the values we have already visited
	private boolean[][] phantom;//new 2D array which contains a wall all around the maze so that we do not get out of bounds

	/**
	 * Constructs a maze.
	 * 
	 * @param mazeData
	 *            the maze to search. See general Maze comments above for what goes
	 *            in this array.
	 * @param startLoc
	 *            the location in maze to start the search (not necessarily on an
	 *            edge)
	 * @param exitLoc
	 *            the "exit" location of the maze (not necessarily on an edge) PRE:
	 *            0 <= startLoc.getRow() < mazeData.length and 0 <=
	 *            startLoc.getCol() < mazeData[0].length and 0 <= endLoc.getRow() <
	 *            mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length
	 * 
	 */
	public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord exitLoc) {

		start = startLoc;
		exit = exitLoc;
		maze = mazeData;
		phantom = new boolean[maze.length + 2][maze[0].length + 2];//2 more rows (one above and one below) and 2 more columns(one left one right)

		for (int i = 0; i < maze[0].length + 2; i++) {// creating the wall of the first row
			phantom[0][i] = WALL;
		}
		for (int i = 0; i < maze[0].length + 2; i++) {//creating the wall of the last row
			phantom[maze.length + 1][i] = WALL;
		}

		for (int j = 0; j < maze.length + 2; j++) {//creating the wall of the first column
			phantom[j][0] = WALL;
		}
		for (int j = 0; j < maze.length + 2; j++) {//creating the wall of the last column
			phantom[j][maze[0].length + 1] = WALL;
		}
		for (int j = 1; j < maze.length + 1; j++) {// copying the values from maze inside the walls
			for (int i = 1; i < maze[0].length + 1; i++) {
				phantom[j][i] = maze[j - 1][i - 1];
			}
		}
	}

	/**
	 * Returns the number of rows in the maze
	 * 
	 * @return number of rows
	 */
	public int numRows() {

		return maze.length;
	}

	/**
	 * Returns the number of columns in the maze
	 * 
	 * @return number of columns
	 */
	public int numCols() {
		return maze[0].length;
	}

	/**
	 * Returns true iff there is a wall at this location
	 * 
	 * @param loc
	 *            the location in maze coordinates
	 * @return whether there is a wall here PRE: 0 <= loc.getRow() < numRows() and 0
	 *         <= loc.getCol() < numCols()
	 */
	public boolean hasWallAt(MazeCoord loc) {
		if (maze[loc.getRow()][loc.getCol()] == WALL) {
			return true;
		} else
			return false;
	}

	/**
	 * Returns the entry location of this maze.
	 */
	public MazeCoord getEntryLoc() {

		return start;
	}

	/**
	 * Returns the exit location of this maze.
	 */
	public MazeCoord getExitLoc() {

		return exit;
	}

	/**
	 * Returns the path through the maze. First element is start location, and last
	 * element is exit location. If there was not path, or if this is called before
	 * a call to search, returns empty list.
	 * 
	 * @return the maze path
	 */
	public LinkedList<MazeCoord> getPath() {

		return path;

	}

	/**
	 * Find a path from start location to the exit location (see Maze constructor
	 * parameters, startLoc and exitLoc) if there is one. Client can access the path
	 * found via getPath method.
	 * 
	 * @return whether a path was found.
	 */
	public boolean search() {
		
		int r = start.getRow();
		int c = start.getCol();
		path = new LinkedList<MazeCoord>();//new linked list with MazeCoord objects with coordinates the path
		visited = new boolean[maze.length][maze[0].length];

		return search(r, c);

	}

	private boolean search(int r, int c) {
		MazeCoord pathway = new MazeCoord(r, c);
		if (phantom[r + 1][c + 1] == WALL) {// the r and c coordinates in the maze are r+1 and c+1 in the phantom proportionately
			return false;
		}//applying the algorithm below
		if (r == exit.getRow() && c == exit.getCol()) {

			path.addFirst(pathway);
			return true;
		}
		if (visited[r][c] == VISIT) {
			return false;
		}

		visited[r][c] = VISIT;

		if (search(r - 1, c) || search(r + 1, c) || search(r, c + 1) || search(r, c - 1) == true) {//recursion for all the adjacent positions
			path.addFirst(pathway);
			return true;
		}

		return false;
	}
}