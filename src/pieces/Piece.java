package pieces;

import java.util.Collection;

import board.Board;
import board.Move;
import util.PieceColor;
import util.PieceType;

/*
 * abstract class for all pieces for layout of pieces
 */
public abstract class Piece {

	int piecePos;
	PieceColor pieceColor;
	boolean isFirstMove;
	PieceType pieceType;
	
	/*
	 * constructor for pieces needs
	 * position
	 * color
	 * and if its first move
	 */
	public Piece(int piecePos, PieceColor pieceColor, boolean isFirstMove) {
		this.piecePos = piecePos;
		this.pieceColor = pieceColor;
		this.isFirstMove = isFirstMove;
		this.pieceType = createPieceType(this.toString());
	}
	
	/*
	 * getter for position
	 */
	public int getPiecePos() {
		return this.piecePos;
	}
	
	/*
	 * getter for piece color
	 */
	public PieceColor getPieceColor() {
		return this.pieceColor;
	}
	
	/*
	 * getter for piece type
	 */
	public PieceType getPieceType() {
		return this.pieceType;
	}
	
	/*
	 * getter for if first move
	 */
	public boolean isFirstMove() {
		return this.isFirstMove;
	}
	
	//abstract methods for calculating mvoes and moving piece
	public abstract Collection<Move> calculateMoves(Board board);
	//public abstract Collection<Move> getAttackMoves(Board board);
 	public abstract Piece movePiece(Move move);
 	
 	public PieceType createPieceType(String pieceLetter) {
 		
 		switch(pieceLetter) {
 		case "B": return PieceType.BISHOP;
 		case "K": return PieceType.KING;
 		case "N": return PieceType.KNIGHT;
 		case "P": return PieceType.PAWN;
 		case "Q": return PieceType.QUEEN;
 		case "R": return PieceType.ROOK;
 		}
 		return null;
 	}
}
