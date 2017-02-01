/* 


   name:  ConnectFour.java
   author: rickroII
   date: 1/31/17
   description: Connect Four game

*/

import java.util.Scanner;
import java.lang.Math.*;

public class ConnectFour{

	private final static int WIDTH = 7, HEIGHT = 6;
	private final static int MAX_HEIGHT = HEIGHT-1, MIN_COLUMN = 1;
	private final static char BLANK = '.';
	private final static int ERROR = -1;
	
	private static String winMessage = null;
	
	private static int row = 0, column = 0;
	private static char token='x';  
	private static int turn = 1;

	public static char[][]board = new char[WIDTH][HEIGHT];

	public static void main(String[] args){
		clearBoard();
		displayRules();
		while (!isBoardFull() && !isWinner(token, winMessage)){
		System.out.println("\n"+token+"'s turn");
		
			do{
				userInput();
			} 
			while (!placeToken(column));

			winMessage = checkWin(token, row, column);
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
	System.out.println("Connect4 game rules can be found online");
	System.out.println("Use an internet browser to look up 'Connect Four Game Rules'");
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

private static boolean isWinner(char token, String winMessage){
	if(winMessage == null){
		return false;
	}
	else{
		System.out.println("\n"+token+" won the game ("+winMessage+")!");
		return true;
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
		
	System.out.println("\nEnter a column (1-7)");
	
	while(flag == false){
		while (!input.hasNextInt()) {
			//tell user to enter in an integer value and repeats until they enter valid input.	
				System.out.println("Enter an integer, please!");
				input.nextLine();
			}
			
			response = input.nextInt()- MIN_COLUMN;
			
			if (response < WIDTH+1 && response >= 0){
				column = response;
				flag = true;
			}
			else  {
				System.out.println("Not a valid column, try (1-7)");
				flag = false;
			}
		}
		return column;
}

/*	
Purpose: places users token in the column of their choice
Inputs: column of players choice
Outputs: none
returns: boolean
*/

private static boolean placeToken(int column){
	for(int height=0; height < HEIGHT; height++){
		if(board[column][MAX_HEIGHT]!= BLANK){
			System.out.println("That column is full! Please select another one.");
			return false;
		}
		else if (board[column][height]==BLANK){
			board[column][height] = token;
			return true;
		}
	}

	return false;
	
}

/*	
Purpose: checkWinConditions (vertical, horizontal, diagonal, and antidiagonal)
Inputs: token, column, row
Outputs: none
returns: boolean
*/
private static String checkWin(char token, int row, int column){
	if(checkVertical(token, column)== true){
		winMessage = "on a vertical";
	}
	else if(checkHorizontal(token, row) == true){
		winMessage = "on a horizontal";
	}
	else if(checkDiagonal(token, row, column) == true){
		winMessage="on a diagonal";
	}
	else if(checkVertical(token, column) == true && checkHorizontal(token, row) == true){
		winMessage="on a vertical & a horizontal";
	}
	else if(checkVertical(token, column) == true && checkDiagonal(token, row, column) == true){
		winMessage="on a vertical & a diagonal";
	}
	else if(checkHorizontal(token, row) == true && checkDiagonal(token, row, column) == true){
		winMessage="on a horizontal & a diagonal";	
	}
	else if(checkVertical(token, column) == true && checkHorizontal(token, row) == true && checkDiagonal(token, row, column) == true){
		winMessage="on a vertical & a horizontal & a diagonal (OH BABY A TRIPLE! OH YEAH!)";
	}
	else{
		winMessage=null;
	}
	
	return winMessage;
	
}

private static boolean checkVertical(char token, int column){
	int vertical=0;
	for(int height=0; height < HEIGHT; height++){
		if(board[column][height]==token){
			vertical++;
			if(vertical>=4){
				return true;
			}
		}
		else{
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
			if(horizontal>=4){
				return true;
			}
		}
		else{
			horizontal=0;
		}
	}
	
	return false;
	
}

private static boolean checkDiagonal(char token, int row, int column){
	int diagonal=0, antidiagonal=0;

	for(int extra = 0; extra < HEIGHT; extra++){
		for(int height = 0; height < HEIGHT; height++){
			//normal diagonal
			for(int width = 0; width < WIDTH; width++){
				if(diagonal >= 4){
					return true;
				}
				else if((width+extra)==height && board[width][height]==token){
					diagonal++;
				}
				else if((height+extra)== width && board[width][height]==token){
					diagonal++;
				}
				else if((height+extra)== width || (width+extra)==height && board[width][height]!=token){
					diagonal = 0;
				}
			}
		}
	}

	for(int height = 0; height < HEIGHT; height++){
		//anti-diagonal
		for(int width = WIDTH-1; width >= 0; width--){
			int difference = 0;
			int metacount = 0;

			difference = width - height;
			metacount = Math.abs(difference);

				if(antidiagonal >= 4){
					return true;
				}
				else if((metacount%2)==0 && board[width][height]==token){
					antidiagonal++;
					height++;
				}
				else if((metacount%2)!=0 && board[width][height]==token){
					antidiagonal = 0;
				}
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
	if(winMessage==null){
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
	else{
		//return's current token if they've won the game
		return token;
	}
}

}
