   /* 


   name:  ConnectFour.java
   author: RickRoII
   date: 1/17/16
   description: Connect Four game


*/

import java.util.Scanner;

public class ConnectFour{

	private final static int WIDTH = 6, HEIGHT = 7;
	private final static int MAX_COLUMN = HEIGHT, MIN_COLUMN = 1;
	private final static char BLANK = '.';
	
	private final static int ERROR = -1;
	
	private static int row = 0, column = 0;
	private static char token='x';  
	private static int win = 0, turn = 1;

	public static char[][]board = new char[WIDTH][HEIGHT];

	public static void main(String[] args){
		clearBoard();
		displayRules();
		while (!isBoardFull() && !isWinner(win)){
			do{
				userInput();
			} 
			while (!isInputValid(column));
			
			placeToken(column);
			checkVertical(token, column);
			checkHorizontal(token, row);
			displayBoard();
			token = switchUser(token);
		}
	} 

/*	
Purpose: Clears Board Array
Inputs: none
Outputs: char board array == _
returns: none
*/

private static void clearBoard(){
	for(int width=0; width < WIDTH; width++){
		for(int height=0; height < HEIGHT; height++){
			board[width][height]=BLANK;
		}
	}	
}

/*	
Purpose: Displays Rules In Console
Inputs: none
Outputs: Rules for connect four
returns: none
*/

private static void displayRules(){
	System.out.println("Connect4 Game Rules can be found online");
	System.out.println("Use an internet browser to find 'Connect Four Game Rules'");
}

/*	
Purpose: Check's if board array is full
Inputs: board
Outputs: none
returns: boolean (if true, board is full)
*/

private static boolean isBoardFull(){
	for(int width=0; width < WIDTH; width++){
		for(int height=0; height < HEIGHT; height++){
			if(board[width][height]==BLANK){
				return false;
			}
		}
	}
	System.out.println("GAME OVER: The board is full");
	return true;	
}

/*	
Purpose: Check's if there is a Winner
Inputs: win
Outputs: none
returns: boolean (if true, there is a winner)
*/

private static boolean isWinner(int win){
	if(win == 0){
		return false;
	}
	else{
		return true;
	}
}

/*	
Purpose: Checks if the input is valid (inside columns and rows)
	DOES NOT check if user overwrites other player's token
Inputs: column and row of players choice
Outputs: none
returns: boolean (if true, the input is valid)
*/

private static boolean isInputValid(int column){
	//fix THIS
	if(column < MAX_COLUMN){
		return true;
	}
	else{
		return false;
	}
}

/*	
Purpose: Allows user to input a column
Inputs: none
Outputs: none
returns: column
*/

private static int userInput(){
	int response;
	boolean flag = false;
		
	Scanner input = new Scanner(System.in);
		
	System.out.println("\nEnter a column (1-6)");
	
	while(flag == false){
		while (!input.hasNextInt()) {
			//tell user to enter in an integer value and repeats until they enter valid input.	
				System.out.println("Enter an integer, please!");
				input.nextLine();
			}
			
			response = input.nextInt()- MIN_COLUMN;
			
			if (response < WIDTH && response >= 0){
				column = response;
				System.out.println("column: "+column);
				flag = true;
			}
			else  {
				System.out.println("Not a valid column, try (1-6)");
				flag = false;
			}
		}
		return column;
}

/*	
Purpose: places users token in the column of their choice
Inputs: column of players choice
Outputs: none
returns: int (row)
*/

private static int placeToken(int column){
	for(int height=0; height < HEIGHT; height++){
		if(board[column][height]==BLANK){
			board[column][height] = token;
			row = height;
			System.out.println("placeToken method");
			System.out.println("column:" + column);
			System.out.println("row: "+row);
			return row;
		}
	}
	return ERROR;
}

/*	
Purpose: checkWinConditions (vertical, horizontal, diagonal, and antidiagonal)
Inputs: token, column, row
Outputs: none
returns: boolean
*/
private static boolean checkVertical(char token, int column){
	int vertical=0;
	for(int height=0; height < HEIGHT; height++){
		if(board[column][height]==token){
			vertical++;
			System.out.println("Vertical: "+vertical);
				if(vertical>=4){
					System.out.println("Vertical Win encountered!");
					return true;
				}
		}
		else{
			System.out.println("DEBUG: "+height);
			vertical=0;
		}
	}
	
	return false;
	
}

private static boolean checkHorizontal(char token, int row){
	int horizontal=0;
	for(int width=0; width < WIDTH; width++){
		if(board[width][row]==token){
			horizontal++;
			System.out.println("Horizontal: "+horizontal);
			if(horizontal>=4){
				System.out.println("Horizontal Win encountered!");
				return true;
			}
		}
		else{
			System.out.println("DEBUG: "+width);
			horizontal=0;
		}
	}
	
	return false;
	
}

/*	
Purpose: displayBoard
Inputs: none
Outputs: Board
returns: none
*/
private static void displayBoard(){
	for(int height = HEIGHT-1; height >= 0; height--){
		for(int width = 0; width < WIDTH; width++){
			System.out.print(board[width][height]);
		}
		System.out.println("");
	}	
}

/*	
Purpose: switch users
Inputs: token
Outputs: current user
returns: char (token)
*/
private static char switchUser(char token){
	turn++;
	if(turn%2 == 1){
		token='x';
		return token;
	}
	else{
		token='o';
		return token;
	}
}

}
