package org.mpsomaha.ConnectFour;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameRunner {

	public static boolean computer = false;
	static Scanner textScanner = new Scanner(System.in);
	public static int player1WinCounter = 0;
	public static int player2WinCounter = 0;
	/**
	 * picks who to play against
	 * @param args
	 */
	public static void main(String[] args) {
		String opponent;
		while(true){
			System.out.println("Would You like to play a computer or 2 player?");
			opponent = textScanner.nextLine().toLowerCase();
			if(opponent.contains("c")){
				computer = true;
				while(true){
					System.out.println("Which type of computer would you like to play against:\n Random, Offensive, or Defensive?");
					String opp = textScanner.nextLine().toLowerCase();
					if(opp.contains("r")){
						System.out.println("You will be playing against a compuuter that plays in any random Column.");
						Computer.randComp = true;
						break;
					}
					if(opp.contains("o")){
						System.out.println("You will be playing against a computer that plays in the column that makes it most likely to win.");
						Computer.oComp = true;
						break;
					}
					if(opp.contains("d")){
						System.out.println("You will be playing against a computer that plays in the colum that makes it hardest for you to win.");
						Computer.dComp = true;
						break;
					}else{
						System.out.println("please pick one of the options");
					}
				}
				break;
			}
			else if(opponent.contains("2") || opponent.contains("play")){
				break;
			}
			else{
				System.out.println("Please pick to play against either \"Player 2\" or \"computer\". ");
			}
		}
		play();
		
	}
	/**
	 * sets up the board
	 * each turn for each player.
	 */
	private static void play(){
		int size = 0;

		Scanner intScanner = new Scanner(System.in);
		while(true){
			System.out.print("How large would you like to make the board?");
			try{
				size = intScanner.nextInt();
			}
			catch(InputMismatchException e){
				System.out.println("   You need to pick a number for the size of the board");
				intScanner.next();
			}
			if(size < 100 && size > 2){
				break;
			}
			else if(size >= 100 || size <= 2){
				System.out.println("  Please pick a number between 3 and 99.");
			}
		}
		Player player = new Player();
		Computer comp = new Computer();
		Coordinate[][] board = new Coordinate[size][size];

		for(int row = 0; row<board.length; row++){
			for(int col = 0; col<board[row].length; col++){
				board[col][row] = new Coordinate(col, row, size);
			}
		}
		player.setWinNum();
		player.printBoard(board);
		
		//the actual turns for each player
		while(!Player.gameOver){
			board = player.turn(board);
			if(computer && !Player.gameOver){
				board = comp.turn(board);
			}
		}
		again();
	}
	/**
	 * after the game is over, asks to start another
	 */
	private static void again(){
		System.out.println("Would you like to play again?");
		while(true){
			String yesNo = textScanner.nextLine().toLowerCase();
			if(yesNo.contains("y")){
				play();
			}
			else if(yesNo.contains("n")){
				System.out.println("\n\n\n\n\n\n\n        FINAL STATS:\n");
				System.out.println("   Total games played: " + (player1WinCounter+ player2WinCounter));
				if(GameRunner.computer){
					System.out.println("   Player 1 was victorious in " + GameRunner.player1WinCounter + " game(s).");
					System.out.println("   The Computer  won " + GameRunner.player2WinCounter + " game(s).\n\n");
				}
				else{
					System.out.println("   Player 1 won " + GameRunner.player1WinCounter + " game(s).");
					System.out.println("   Player 2 won " + GameRunner.player2WinCounter + " game(s).\n\n\n\n\n\n");
				}
				break;
			}
			else{
				System.out.println("That was a simple yes or no question");
			}
		}
	}
	

}



