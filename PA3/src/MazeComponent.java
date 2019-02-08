
// Name: Leonidas Karnesis
// USC NetID: karnesis
// CS 455 PA3
// Spring 2018

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * MazeComponent class
 * 
 * A component that displays the maze and path through it if one has been found.
 */
public class MazeComponent extends JComponent {

	private static final int START_X = 10; // top left of corner of maze in frame
	private static final int START_Y = 10;
	private static final int BOX_WIDTH = 20; // width and height of one maze "location"
	private static final int BOX_HEIGHT = 20;
	private static final int INSET = 2; // how much smaller on each side to make entry/exit inner box
	private int entryx;
	private int entryy;
	private int exitx;
	private int exity;
	private Maze maze;
	private MazeCoord walls;

	/**
	 * Constructs the component.
	 * 
	 * @param maze
	 *            the maze to display
	 */
	public MazeComponent(Maze maze) {

		this.maze = maze;
		entryx = maze.getEntryLoc().getRow();
		entryy = maze.getEntryLoc().getCol();
		exitx = maze.getExitLoc().getRow();
		exity = maze.getExitLoc().getCol();

	}

	/**
	 * Draws the current state of maze including the path through it if one has been
	 * found.
	 * 
	 * @param g
	 *            the graphics context
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		createMaze(g2);//creates the walls and the free positions and a black line which circumscribes the maze 
		EntryExitBox(g2);//creates the entry box and exit box
		pathline(g2);//draw
	}

	private void createMaze(Graphics2D g2) {
		for (int i = 0; i < maze.numRows(); i++) {
			for (int j = 0; j < maze.numCols(); j++) {
				walls = new MazeCoord(i, j);
				if (maze.hasWallAt(walls)) {//if there is wall paint it black
					g2.setColor(Color.BLACK);
					Rectangle walls = new Rectangle(START_X + j * BOX_WIDTH, START_Y + i * BOX_HEIGHT, BOX_WIDTH,
							BOX_HEIGHT);
					g2.fill(walls);
				} else if (!maze.hasWallAt(walls)) {//if there is free position we create a white rectangle
					g2.setColor(Color.WHITE);
					Rectangle nowalls = new Rectangle(START_X + j * BOX_WIDTH, START_Y + i * BOX_HEIGHT, BOX_WIDTH,
							BOX_HEIGHT);
					g2.fill(nowalls);
				}
			}
		}
		g2.setColor(Color.BLACK);//black line around the maze
		Rectangle box = new Rectangle(START_X, START_Y, BOX_WIDTH * maze.numCols(), BOX_HEIGHT * maze.numRows());
		g2.draw(box);
	}

	private void EntryExitBox(Graphics2D g2) {
		g2.setColor(Color.YELLOW);
		Rectangle entry = new Rectangle(START_X + entryy * BOX_WIDTH + INSET, START_Y + entryx * BOX_HEIGHT + INSET,
				BOX_WIDTH - 2*INSET, BOX_HEIGHT - 2*INSET);//entry box
		g2.fill(entry);
		g2.setColor(Color.GREEN);
		Rectangle exit = new Rectangle(START_X + exity * BOX_WIDTH + INSET, START_Y + exitx * BOX_HEIGHT + INSET,
				BOX_WIDTH - 2*INSET, BOX_HEIGHT - 2*INSET);//exit box
		g2.fill(exit);
	}

	private void pathline(Graphics2D g2) {

		g2.setColor(Color.BLUE);
		LinkedList<MazeCoord> coords = maze.getPath();//coords of the path		
		for (int i = 0; i < coords.size() - 1; i++) {
			MazeCoord first = coords.get(i);
			MazeCoord next = coords.get(i + 1);
			Line2D.Double path = new Line2D.Double(START_X + first.getCol() * BOX_HEIGHT + BOX_HEIGHT / 2,
					START_Y + first.getRow() * BOX_WIDTH + BOX_WIDTH / 2,
					START_X + next.getCol() * BOX_HEIGHT + BOX_HEIGHT / 2,
					START_Y + next.getRow() * BOX_WIDTH + BOX_WIDTH / 2);
			g2.draw(path);
		}
	}
}
