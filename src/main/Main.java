package main;

import board.Board;
import gui.GUI;

/*
 * Main class is creating a new board and new gui 
 * passes the board into the gui
 */
public class Main {

	public static void main(String[] args) {
		Board board = new Board();
		GUI gui = new GUI(board);

	}

}
