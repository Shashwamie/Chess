package board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;
import player.BlackPlayer;
import player.Player;
import player.WhitePlayer;
import util.PieceColor;
import util.PieceType;

public class Board {

	//the list of tiles the board is constructed of
	public List<Tile> gameBoard;
	
	//TODO collection of each pieces currently not being used
	Collection<Piece> whitePieces;
	Collection<Piece> blackPieces;
	
	//key value pairs of the integer coordinate of the board and the piece class that is on that tile
	public Map<Integer, Piece> currentBoardLayout = new HashMap<>();
	
	//TODO each player currently not being used
	WhitePlayer whitePlayer;
	BlackPlayer blackPlayer;
	
	public Player currentPlayer;
	
	private String message;
	
	public final static int NUM_TILES = 64;
	final static int NUM_TILES_ROW = 8;
	
	//Used to see if a piece position is in a certain column or row
	public final static boolean[] FIRST_COLUMN = inColumn(0);
	public final static boolean[] SECOND_COLUMN = inColumn(1);
	public final static boolean[] THIRD_COLUMN = inColumn(2);
	public final static boolean[] FOURTH_COLUMN = inColumn(3);
	public final static boolean[] FIFTH_COLUMN = inColumn(4);
	public final static boolean[] SIXTH_COLUMN = inColumn(5);
	public final static boolean[] SEVENTH_COLUMN = inColumn(6);
	public final static boolean[] EIGTH_COLUMN = inColumn(7);
	
	public final static boolean[] EIGTH_RANK = inRow(0);
	public final static boolean[] SEVENTH_RANK = inRow(8);
	public final static boolean[] SIXTH_RANK = inRow(16);
	public final static boolean[] FIFTH_RANK = inRow(24);
	public final static boolean[] FOURTH_RANK = inRow(32);
	public final static boolean[] THIRD_RANK = inRow(40);
	public final static boolean[] SECOND_RANK = inRow(48);
	public final static boolean[] FIRST_RANK = inRow(56);
	
	/*
	 * constructor
	 * uncomment the default layout and comment the example board
	 * to get the full board
	 */
	public Board() {
		//createDefaultLayout();
		createMoveExampleBoard();
		this.gameBoard = createGameBoard();
		this.whitePieces = getWhiteActivePieces(gameBoard);
		this.blackPieces = getBlackActivePieces(gameBoard);
		this.whitePlayer = new WhitePlayer(this, calculateLegalMoves(whitePieces), calculateLegalMoves(blackPieces));
		this.blackPlayer = new BlackPlayer(this, calculateLegalMoves(blackPieces), calculateLegalMoves(whitePieces));
		this.currentPlayer = whitePlayer();
	}
	
	public Board(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * getters from the board
	 * @return
	 */	
	public WhitePlayer whitePlayer() {
		return this.whitePlayer;
	}
	
	public BlackPlayer blackPlayer() {
		return this.blackPlayer;
	}
	
	public Collection<Piece> getBlackPieces(){
		return this.blackPieces;
	}
	
	public Collection<Piece> getWhitePieces(){
		return this.whitePieces;
	}
	public Tile getTile(int tileCoord) {
		return gameBoard.get(tileCoord);
	}
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	/**
	 * creating the array of tiles for the layout of the board
	 * @return
	 */
	public List<Tile> createGameBoard(){
		Tile[] tiles = new Tile[NUM_TILES];
		for(int i = 0; i < NUM_TILES; i++) {
			tiles[i] = Tile.createTile(i, currentBoardLayout.get(i));
		}
		return List.of(tiles);
	}
	
	
	/**
	 * setting pieces to their corresponding spots in table
	 */
	public void setPiece(Piece piece){
		currentBoardLayout.put(piece.getPiecePos(), piece);
	}
	
	/*
	 * setting all the new pieces to create a fresh board
	 */
	public void createDefaultLayout() {
		setPiece(new Rook(0, PieceColor.BLACK));
		setPiece(new Knight(1, PieceColor.BLACK));
		setPiece(new Bishop(2, PieceColor.BLACK));
		setPiece(new Queen(3, PieceColor.BLACK));
		setPiece(new King(4, PieceColor.BLACK));
		setPiece(new Bishop(5, PieceColor.BLACK));
		setPiece(new Knight(6, PieceColor.BLACK));
		setPiece(new Rook(7, PieceColor.BLACK));
		setPiece(new Pawn(8, PieceColor.BLACK));
		setPiece(new Pawn(9, PieceColor.BLACK));
		setPiece(new Pawn(10, PieceColor.BLACK));
		setPiece(new Pawn(11, PieceColor.BLACK));
		setPiece(new Pawn(12, PieceColor.BLACK));
		setPiece(new Pawn(13, PieceColor.BLACK));
		setPiece(new Pawn(14, PieceColor.BLACK));
		setPiece(new Pawn(15, PieceColor.BLACK));
		
		//White Layout
		setPiece(new Pawn(48, PieceColor.WHITE));
		setPiece(new Pawn(49, PieceColor.WHITE));
		setPiece(new Pawn(50, PieceColor.WHITE));
		setPiece(new Pawn(51, PieceColor.WHITE));
		setPiece(new Pawn(52, PieceColor.WHITE));
		setPiece(new Pawn(53, PieceColor.WHITE));
		setPiece(new Pawn(54, PieceColor.WHITE));
		setPiece(new Pawn(55, PieceColor.WHITE));
		setPiece(new Rook(56, PieceColor.WHITE));
		setPiece(new Knight(57, PieceColor.WHITE));
		setPiece(new Bishop(58, PieceColor.WHITE));
		setPiece(new Queen(59, PieceColor.WHITE));
		setPiece(new King(60, PieceColor.WHITE));
		setPiece(new Bishop(61, PieceColor.WHITE));
		setPiece(new Knight(62, PieceColor.WHITE));
		setPiece(new Rook(63, PieceColor.WHITE));
		
		
	}
	
	/*
	 * setting some random pieces to test move functionality
	 */
	public void createMoveExampleBoard() {
		
		
		setPiece(new Rook(27, PieceColor.BLACK));
		setPiece(new Pawn(17, PieceColor.WHITE));
		setPiece(new Pawn(34, PieceColor.WHITE));
		setPiece(new Knight(36, PieceColor.BLACK));
		setPiece(new Knight(40, PieceColor.BLACK));
		setPiece(new Bishop(47, PieceColor.BLACK));
		setPiece(new Pawn(10, PieceColor.BLACK));
		setPiece(new King(33, PieceColor.BLACK));
		setPiece(new King(49, PieceColor.WHITE));
		//setPiece(new Queen(54, PieceColor.BLACK));
		setPiece(new Rook(63, PieceColor.BLACK));
		setPiece(new Pawn(15, PieceColor.BLACK));
		setPiece(new Pawn(12, PieceColor.WHITE));
		setPiece(new Pawn(52, PieceColor.BLACK));
		
		
		
		//setPiece(new King(3, PieceColor.BLACK));
		//setPiece(new Bishop(19, PieceColor.BLACK));
		//setPiece(new King(26, PieceColor.WHITE));
	}
	
	/*
	 * method to see the piece is in the row passed in
	 */
	public static boolean[] inRow(int startingRowNum) {
		boolean[] boolArray = new boolean[NUM_TILES];
		do {
			boolArray[startingRowNum] = true;
			startingRowNum++;
		}while(startingRowNum % NUM_TILES_ROW != 0);
		return boolArray;
	}
	
	/*
	 * method to see the piece is in the column passed in
	 */
	public static boolean[] inColumn(int startingColNum) {
		boolean[] boolArray = new boolean[NUM_TILES];
		do {
			boolArray[startingColNum] = true;
			startingColNum += NUM_TILES_ROW;
		}while(startingColNum < NUM_TILES);
		return boolArray;
	}
	
	/*
	 * testing if the passed in coordinate is on the board
	 */
	public  boolean isValidCoord(int coord) {
		return coord >= 0 && coord < NUM_TILES;
	}
	
	/*
	 * get all active pieces
	 */
	public static Collection<Piece> getActivePieces(List<Tile> gameBoard){
		List<Piece> activePieces = new ArrayList<>();
		for(Tile tile : gameBoard) {
			if(tile.isTileOccupied()) {
				Piece piece = tile.getPiece();
				activePieces.add(piece);
			}
		}
		return activePieces;
	}
	
	/*
	 * get all of Blacks active pieces
	 */
	public static Collection<Piece> getBlackActivePieces(List<Tile> gameBoard){
		List<Piece> activePieces = new ArrayList<>();
		for(Tile tile : gameBoard) {
			if(tile.isTileOccupied()) {
				Piece piece = tile.getPiece();
				if(piece.getPieceColor() == PieceColor.BLACK) {
					activePieces.add(piece);
				}
			}
		}
		return activePieces;
	}
	
	/*
	 * get all of Blacks active pieces
	 */
	public static Collection<Piece> getWhiteActivePieces(List<Tile> gameBoard){
		List<Piece> activePieces = new ArrayList<>();
		for(Tile tile : gameBoard) {
			if(tile.isTileOccupied()) {
				Piece piece = tile.getPiece();
				if(piece.getPieceColor() == PieceColor.WHITE) {
					activePieces.add(piece);
				}
			}
		}
		return activePieces;
	}
	
	
	/*
	 * gets all possible moves for all the players pieces
	 */
	public Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
		List<Move> legalMoves = new ArrayList<>();
		for(Piece piece : pieces) {
			legalMoves.addAll(piece.calculateMoves(this));
		}
		return legalMoves;
	}
}
