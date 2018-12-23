package org.mpsomaha.ConnectFour;

import java.util.Scanner;

import org.omg.IOP.CodecFactoryOperations;

public class Player {
//Constructor
	private boolean playerNum;
	protected static boolean gameOver;
	private Scanner scan;
	public static int winNum;

//Attributes
	public Player(){
		gameOver = false;
		playerNum = true;
		scan = new Scanner(System.in);
	}
//Methods
	/**
	 * determines whose turn it is and lets them play
	 * @param board
	 * @return
	 */
	public Coordinate[][] turn(Coordinate [][] board){
		if(playerNum){
			System.out.println("\n   It is Player 1's turn.");
			return play(board);
		}
		else{
			System.out.println("\n   It is player 2's turn");
			return play(board);
		}
	}
	/**
	 * asks the player which column they would like to play in
	 * checks if that is an option and plays in it
	 * changes whose turn it is if there is no computer
	 * @param board
	 * @return
	 */
	private Coordinate[][] play(Coordinate[][] board ){
		boolean done = false;
		int column;
		while(true){
			System.out.println("      Which column would you like to play in?");
			column = scan.nextInt()-1;
			if(column >= Coordinate.mySize){
				System.out.println("That is not even on the board.");
			}
			else if(Coordinate.fullCol[column]){
				System.out.println("Column " + (column+1) + " is full");
			}
			else{
				break;
			}
			
		}
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
		checkWin(board);
		if(!GameRunner.computer){
			playerNum = !playerNum;
		}
		return board;
	}
	/**
	 * tells whose turn it is
	 * @return boolean that indicates whose turn it is
	 */
	private boolean getPlayer(){
		return playerNum;
	}
	/**
	 * prints out the board
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

	/**
	 * At the beginning of the game, it finds how big the board is
	 * sets the required number of pieces in a row accordingly 
	 * @return
	 */
	public int setWinNum(){
		if(Coordinate.mySize < 3){
			winNum = Coordinate.mySize;
		}
		else if(Coordinate.mySize <= 4){
			winNum = 3;
		}
		else if(Coordinate.mySize <= 7){
			winNum = 4;
		}
		else{
			winNum = 5;
		}
		System.out.println("You need to get " + winNum + " in a row to win.\n");
		return winNum;
	}
	/**
	 * looks in every direction and figures out if there is the correct number in that direction to win
	 * @param board
	 * @return board with new piece
	 */
	public static boolean checkWin(Coordinate[][] board){
//		System.out.println("checkWin");
		for(int row = 0; row<=board.length -1; row++){
			for(int col = 0; col<=board[row].length -1; col++){
				int flatRightCounter = 1;
				int downCounter = 1;
				int downRightCounter = 1;
				int upRightCounter = 1;
				for(int i = 1; (row+i)<board[row].length && i<winNum ; i++){//horizontal
					if(board[row][col].coord.equals(board[row + i][col].coord) && (board[row][col].coord.contains("X") || board[row][col].coord.contains("O")) ){
						if(flatRightCounter == i){
							flatRightCounter++;

						}
					}
				}
				for(int i = 1; (col+i)<board[row].length && i <winNum; i++){//verticle
					if(board[row][col].coord.equals(board[row][col + i].coord) && (board[row][col].coord.contains("X") || board[row][col].coord.contains("O"))){
						if(downCounter == i){
							downCounter++;
						}
					}
				}
				for(int i = 1; (col+i)<board[row].length && (row+i)<board[row].length; i++){//diagonal down right
					if(board[row][col].coord.equals(board[row + i][col + i].coord) && (board[row][col].coord.contains("X") || board[row][col].coord.contains("O"))){
						if(downRightCounter == i){
							downRightCounter++;
						}
					}
				}
				for(int i = 1; (col-i)>0 && (row+i)<board[row].length; i++){//diagonal up Right
					if(board[row][col].coord.equals(board[row + i][col - i].coord) && (board[row][col].coord.contains("X") || board[row][col].coord.contains("O"))){
						if(upRightCounter == i){
							upRightCounter++;
						}
					}
				}
				if(flatRightCounter == winNum || downCounter == winNum || downRightCounter == winNum || upRightCounter == winNum){
					if(board[row][col].coord.contains("X")){
						System.out.println("\n\n\n     Player 1 won the game!\n");
						GameRunner.player1WinCounter++;
					}
					else{
						GameRunner.player2WinCounter++;
						if(GameRunner.computer){
							System.out.println("\n\n\n     The Computer won the game!\n");
							
							
						}
						else{
							System.out.println("\n\n\n     Player 2 won the game!\n");

						}
					}
					if(GameRunner.computer){
						System.out.println("You have won " + GameRunner.player1WinCounter + " games.");
						System.out.println("The Computer has won " + GameRunner.player2WinCounter + " games.\n\n");
					}
					else{
						System.out.println("Player 1 has won " + GameRunner.player1WinCounter + " games.");
						System.out.println("Player 2 has won " + GameRunner.player2WinCounter + " games.\n\n");
					}
					gameOver = true;
				}
				
				
			}
		}
		return gameOver;
	}
}
