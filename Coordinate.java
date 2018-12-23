package org.mpsomaha.ConnectFour;

public class Coordinate {
	private int x;
	private int y;
	protected String coord;
	public boolean occupied = false;
	protected static int mySize = 0;
	public static boolean[] fullCol;
	
	public Coordinate(int xx, int yy, int s){
		occupied = getOccupied();
		mySize = s;
		fullCol = new boolean[mySize];
		x = xx + 1;
		y = yy + 1;
		coord = setCoord();
	}
/**
 * makes each coordinate
 * @return
 */
	private String setCoord(){
		return "|     ";
	}
	/**
	 * prints the board
	 */
	public void printCoord(){
		System.out.print(coord);
	}
	/**
	 * each turn that is made
	 * @param play
	 */
	public void makeMove(boolean play){
		this.coord = setCoord(play);
	}
	/**
	 * set the Coordinate to be something else when it gets played at
	 * @param play
	 * @return
	 */
	private String setCoord(boolean play){
		String mover;
		if(play){
			mover = "X";
		}
		else{
			mover = "O";
		}

		return "|  " + mover + "  ";
	}
	/**
	 * tells if a piece is in a Coordinate
	 * @return true if it is occupied
	 */
	public boolean getOccupied(){
		return this.occupied;
	}
	/**
	 * sets a Coordinate to be occupied if it is played at
	 */
	public void setOccupied() {
		this.occupied = true;
	}

}
