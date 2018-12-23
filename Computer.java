package org.mpsomaha.ConnectFour;

import java.util.Random;

public class Computer {
	Random rand = new Random();
	public static boolean randComp = false;
	public static boolean dComp = false;
	public static boolean oComp = false;
	private boolean played = false;
	private int column = 0;


	/**
	 * determines which type of computer is playing
	 * calls the correct method for that type of Computer
	 * @param board
	 * @return 
	 */
	protected Coordinate[][] turn(Coordinate[][] board ){
		if(randComp){
			randLoc(board);
		}
		else if(dComp){
			dLoc(board);
		}
		else if(oComp){
			oLoc(board);
		}
		return board;
	}
	/**
	 * when it is the computers turn to play, it picks a random number on the board
	 * checks if it is in a full column and if it is, it picks a different random number
	 * @param board
	 * @return the board with the new change.
	 */
	private Coordinate[][] randLoc(Coordinate[][] board ){
		while(true){
			column = rand.nextInt(Coordinate.mySize);
			if(!Coordinate.fullCol[column]){
				break;
			}
		}
		makeCompMove(board);
		Player.checkWin(board); // had to make   int winNum   and the checkWin() method static??? 
		return board;
	}
	private boolean first = true;
	/**
	 * checks for a horizontal line
	 * if none are found that would help it win, it plays on top of the last play
	 * unless the player played in that column so then it plays in a new column
	 * @param board
	 * @return
	 */
	private Coordinate[][] oLoc(Coordinate[][] board ){
		System.out.println("\n\n\nthis is still testing");
		if(first){
			column = Coordinate.mySize/2;	
			first = false;
		}
		boolean done = false;
		breakpoint:
		while(!done){
//			if(!played){
//				played = true;
//			}
//			else{
				for(int col = 0; col<=board.length -1; col++){   
					for(int row = 0; row<=board[col].length -1; row++){
						if(Coordinate.fullCol[column + 1]){
							column ++;
						}
						for(int i = 1; (row-i)>0 && i<Player.winNum ; i++){//verticle
							if(board[column][row-i].coord.contains("X") && board[col][row].coord.contains("O")){
								System.out.println("done = " + done);
								if(column<Coordinate.mySize - 1 && !Coordinate.fullCol[column] && !done){
									System.out.println("HELLO");
									System.out.println(column);
									column ++;
									System.out.println(column);
									done = true;
									break breakpoint;
								}
								else if(column == Coordinate.mySize - 1 && !Coordinate.fullCol[column]){
									System.out.println("why....?");
									column = 0;
									break;
								}
//								break; //for now for testing
							}

						}
					}
				}
//			}
			break;
		}
		
		makeCompMove(board);
		Player.checkWin(board);
		return board;
	}
	

	/**
	 * checks for vertical and horizontal lines of the player's piece
	 * plays at one of the locations that blocks the most
	 * if no place needs blocked, it plays at random
	 * @param board
	 * @return the new board with the computer's move
	 */
	private Coordinate[][] dLoc(Coordinate[][] board ){
		boolean lol = false;
		int lastCol = 0;
		int num = 2;
		if(Player.winNum == 3){
			num = 1;
		}
		for(int row = 0; row<=board.length -1; row++){
			for(int col = 0; col<=board[row].length -1; col++){
				int flatRightCounter = 1;
				int downCounter = 1;
				for(int i = 1; (row+i + 1)<board[row].length && i<Player.winNum ; i++){//horizontal
					if(board[row][col].coord.equals(board[row + i][col].coord) && (board[row][col].coord.contains("X")) && !board[row + i  + 1][col].coord.contains("O")){
						if(flatRightCounter == i){
							flatRightCounter++;
							 lastCol = (row + 1 + num); 
							 //3 

						}
					}
					else if(board[row][col].coord.equals(board[row + i][col].coord) && (board[row][col].coord.contains("X")) && board[row + i  + 1][col].coord.contains("O") && row != 0){
						if(flatRightCounter == i && flatRightCounter == Player.winNum - 2&& !board[row - 1][col].coord.contains("O")){
							 lastCol = (row - 1);

						}
					}
				}
				for(int i = 1; (col+i)<board[row].length && i <Player.winNum; i++){//vertical
					if(board[row][col].coord.equals(board[row][col + i].coord) && (board[row][col].coord.contains("X") && !board[row][col - 1].coord.contains("O"))){
						if(downCounter == i){
							if(board[row][col + i].coord.contains("O")){
							}
							else{
								downCounter++;
								lastCol = row;								
							}
						}
					}
				}

				if(flatRightCounter >= Player.winNum - num){
//					System.out.println("Horizontal");
					column =  lastCol;
					lol = true;
				}
				else if(downCounter == Player.winNum - num){
//					System.out.println("Verticle");
					column = lastCol;
					lol = true;
				}	
			}
		}
		if(!lol){
			System.out.println("It played at random.");
			randLoc(board);
		}
		else{
			makeCompMove(board);
			Player.checkWin(board);
		}
		return board;
	}
	/**
	 * prints the new board after the computer plays
	 * @param board
	 */
	private Coordinate[][] makeCompMove(Coordinate[][] board ){
		boolean done = false;
		boolean playerNum = false;
		System.out.println("\n   It is the Computer's turn. \n      The computer played in column " + (column+1) + "\n");
		for(int i = Coordinate.mySize - 1 ; i >= 0 && !done; i--){
			if(i == 0){
				System.out.println("Column " + (column+1) + " is now full.");
				Coordinate.fullCol[column] = true;
			}
			if(!board[column][i].getOccupied() && !done){
				board[column][i].makeMove(playerNum);
				board[column][i].setOccupied();
				done = true;
			}
		}
		printBoard(board);
		return board;
	}
	/**
	 * prints the board
	 * @param board
	 */
	public void printBoard(Coordinate[][] board){
		for(int i = 0; i<Coordinate.mySize; i++){
			if(Coordinate.mySize < 10){
				System.out.print("   " + (i+1) + "  ");
			}else{
				if(i<9){
					System.out.print("  0" + (i+1) + "  ");
				}
				else{
					System.out.print("  " + (i+1) + "  ");
				}
			}
		}
		System.out.println();
		for(int row = 0; row<board.length; row++){
			for(int col = 0; col<board[row].length; col++){
				board[col][row].printCoord();
			}
			System.out.println("|");
		}
		for(int row = 0; row<board.length; row++){
			System.out.print(" -----");
		}
		System.out.println();
	}

}
