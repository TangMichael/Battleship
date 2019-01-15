

	import java.util.Scanner;
	
	/**
	 * 
	 * The BattleShip class makes the user play a game of battleship with the computer.
	 * The int j is a counter for the ships hit by the computer.
	 * The int k is the counter for the ships hit by the human player.
	 * @author Tristan Vu 40028927
	 * @author Michael Tang 40028150<br>
	 * COMP 249<br>
	 * Assignment 1<br>
	 * Due Date: 1st February
	 * 
	 * 
	 *
	 */

public class BattleShip {
	Scanner sc=new Scanner(System.in);
	private char board[][]=new char[8][8];
	private char board2[][]=new char[8][8];
	private int turn=0;
	private int j=0; 
	private int k=0;
	private int computerMisses=0;
	private int userMisses=0;
	
	/**
	 * The runGame method initializes all other methods.
	 */
	public void runGame(){
		initBoard(board);
		initBoard(board2);
		ships();
		System.out.println();
		grenade();
		System.out.println();
		computerShips();
		computerGrenade();
		System.out.println("OK, the computer placed its ships and grenades at random. Letís play.");
		System.out.println();
		while(endGame()){
			turn();
			displayBoard(board2);
		}
		winner();	
		displayBoard(board);
		System.out.println("User missed " + userMisses + " times.");
		System.out.println("Computer missed " + computerMisses + " times.");
	}
	
	/**
	 * Initializes all positions of the array to underscores.
	 * @param board
	 * Modifying the content of the board.
	 * 
	 */
	public void initBoard(char[][]board){
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board.length;j++)
				board[i][j]='_';
		}
	}
	/**
	 * Prints the array.
	 * @param board
	 * Display the content of the board
	 */
	public void displayBoard(char[][]board){
		for(int i=0;i<board.length;i++){
			System.out.print("\t");
			for(int j=0;j<board.length;j++){
				System.out.print(board[j][i] + " ");
			}
			System.out.println();
		}
	}
	/**
	 * Allows random placement of ships for the Computer.
	 */
	public void computerShips(){
		for(int i=0;i<6;i++){
			int column=(int)(Math.random()*8);
			int row=(int)(Math.random()*8);
			if(board[row][column]=='s' || board[row][column]=='S' || board[row][column]=='G'){
				i--;
			}
			else
				board[row][column]='s';
		}	
	}
	/**
	 * Allows random placement of grenades for the Computer.
	 */
	public void computerGrenade(){
		for(int i=0;i<4;i++){
		int column=(int)(Math.random()*8);
		int row=(int)(Math.random()*8);
		if(board[row][column]=='g'||
		   board[row][column]=='s'||
		   board[row][column]=='S'|| 
		   board[row][column]=='G'){
			i--;
		}
		else
			board[row][column]='g';
		}
	}
	/**
	 * Will randomly select a positions in the array 
	 * and fire a rocket at the position.
	 * Depending on what it hits, the board will change accordingly and the turn will be update.
	 */
	public void computerRocket(){
		int column = (int)(Math.random()*8);          	
		int row = (int)(Math.random()*8);
		char letterPosition=(char)(row+65);	
		System.out.println("position of my rocket: " + letterPosition+(column+1));
		
		if(board2[row][column]!='_'){
			System.out.println("position already called");
			turn++;
		}
		
		else if(board[row][column]=='s'){
		
			board2[row][column]='s';
			System.out.println("own ship hit");
			turn++;
			k++;
		}
		else if (board[row][column]=='S'){
			board2[row][column]='S';
			System.out.println("enemy ship hit");
			j++;
			turn++;
			}
		
		else if(board[row] [column]=='g'){
			board2[row][column]='g';
			System.out.println("own grenade hit");
			computerMisses++;
			displayBoard(board2);
			rocket();	
		}
		else if(board[row][column]=='G'){
			board2[row][column]='G';
			System.out.println("enemy grenade hit");
			computerMisses++;
			displayBoard(board2);
			rocket();
		}
		
		else if(board[row][column]=='_'){
			board2[row][column]='*';
			System.out.println("nothing there");
			turn++;
		}	
	}
	/**
	 * Allows user to place ships in the array.
	 */
	public void ships(){
		for(int i=0;i<6;i++){
		System.out.print("enter coordinate of your ship #" + (i+1) + ": ");
		String ship=sc.next();
		if(ship.charAt(0)<'A' || 
		   ship.charAt(0)>'H' ||
		   ship.charAt(1)<'1' ||
		   ship.charAt(1)>'8' ||
		   ship.length()!=2){
			System.out.println("Error, out of grid");
			i--;
		}		
		else if(board[ship.charAt(0)-65][ship.charAt(1)-49]=='S'){
			System.out.println("Already taken");
			i--;
		}
		else
		board[ship.charAt(0)-65][ship.charAt(1)-49]='S';	
		}
	}
	/**
	 * Allows user to place grenades in the array.
	 */
	public void grenade(){
		for(int i=0;i<4;i++){
		System.out.print("enter coordinate of your grenade #" + (i+1) + ": ");
		String ship=sc.next();
		if(ship.charAt(0)<'A' || 
		   ship.charAt(0)>'H' ||
		   ship.charAt(1)<'1' ||
		   ship.charAt(1)>'8' ||
		   ship.length()!=2){
			System.out.println("Out of grid");
			i--;
		}
		else if(board[ship.charAt(0)-65][ship.charAt(1)-49]=='S' ||
				board[ship.charAt(0)-65][ship.charAt(1)-49]=='G'){
			System.out.println("Already Taken");
			i--;
		}
		else
		board[ship.charAt(0)-65][ship.charAt(1)-49]='G';
		}
	}
	/**
	 * Allows user to choose a position in the array and 
	 * fire a rocket at that position.
	 * The board will be updated depending on what it hits and be displayed.
	 * The turn will also update.
	 */
	public void rocket(){
		System.out.print("position of your rocket: ");
		String userRocket=sc.next();
	
		if(userRocket.charAt(0)<'A' || 
		   userRocket.charAt(0)>'H' ||
     	   userRocket.charAt(1)<'1' ||
	       userRocket.charAt(1)>'8' ||
		   userRocket.length()!=2){
			System.out.println("Error, out of grid");
		}
		else if(board2[userRocket.charAt(0)-65][userRocket.charAt(1)-49]!='_'){
			System.out.println("position already called");
			turn++;
		}
		
		
		else if (board[userRocket.charAt(0)-65][userRocket.charAt(1)-49]=='s'){//enemy ship hit		
			board2[userRocket.charAt(0)-65][userRocket.charAt(1)-49]='s';
			System.out.println("ship hit");
			k++;
			++turn;
		}
		else if (board[userRocket.charAt(0)-65][userRocket.charAt(1)-49]=='S'){//own ship hit
			board2[userRocket.charAt(0)-65][userRocket.charAt(1)-49]='S';
			System.out.println("own ship hit wups");
			++turn;
			j++;
			}
		else if(board[userRocket.charAt(0)-65][userRocket.charAt(1)-49]=='g'){//enemy grenade hit
			board2[userRocket.charAt(0)-65][userRocket.charAt(1)-49]='g';
			System.out.println("enemy grenade hit");
			userMisses++;
			displayBoard(board2);
			computerRocket();				
		}
		else if(board[userRocket.charAt(0)-65][userRocket.charAt(1)-49]=='G'){
			board2[userRocket.charAt(0)-65][userRocket.charAt(1)-49]='G';
			System.out.println("own grenade hit");
			userMisses++;
			displayBoard(board2);
			computerRocket();
		}
		
		else if(board[userRocket.charAt(0)-65][userRocket.charAt(1)-49]=='_'){
			board2[userRocket.charAt(0)-65][userRocket.charAt(1)-49]='*';
			System.out.println("nothing there");
			turn++;
		}
	}
	/**
	 * If the number is even, it goes into the rocket method 
	 * and if it is odd, then it goes into the computerRocket.
	 */
	public void turn(){
		if(turn%2==0){
			rocket();
		}
		else if(turn%2==1){
			computerRocket();
		}
	}
	/**
	 * Determines whether or not the game is done,
	 * if it is done, it returns false.
	 * @return boolean value depending on who won
	 */
	public boolean endGame(){
		if(k==6||j==6)
			return false;
		else
			return true;
	}
	/**
	 * Displays who the winner is.
	 */
	public void winner(){
	if(k==6)
		System.out.println("You win");
	if(j==6)
		System.out.println("I win");
	}
}