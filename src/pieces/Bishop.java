package pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import board.Board;
import board.Move;
import board.Move.AttackMove;
import board.Move.NormalMove;
import util.PieceColor;


/*
 * class to represent the bishop to create a new bishop piece
 */
public class Bishop extends Piece{
	

	//constructor that assumes first move is true
	public Bishop(int piecePos, PieceColor pieceColor) {
		super(piecePos, pieceColor, true);
	}
	
	//constructor that takes in first move
	public Bishop(int piecePos, PieceColor pieceColor, boolean isFirstMove) {
		super(piecePos, pieceColor, isFirstMove);
	}
	
	//the set of integers that piece can move 
	final int[] MOVE_CANDIDATE_DIRECTION = {-9, -7, 7, 9};

	
	/*
	 * Method that calculates the moves possible by this piece
	 * needs the board being used to be passed in
	 * returns a collection of move objects
	 */
	@Override
	public Collection<Move> calculateMoves(Board board) {
		
		//creates an empty list for moves to be added to
		List<Move> moves = new ArrayList<>();
		
		
		//loops through the possible move integers
		for(int currentMoveCandidate : MOVE_CANDIDATE_DIRECTION) {
			
			//coordinate to check is the pieces position with the offset where we want to move
			int coordinateToCheck = this.piecePos + currentMoveCandidate;
			
			//checks to make sure the coordinate is within the board
			while(board.isValidCoord(coordinateToCheck)) {
				
				//checks to see if piece is on the edge and takes out the moves not possible
				if(firstColumnEdgeCheck(this.piecePos, currentMoveCandidate) ||
					eigthColumnEdgeCheck(this.piecePos, currentMoveCandidate)) {
					break;
				}
				
				//stops the vector of travel on the edge
				if(eigthColumnEdgeCheck(coordinateToCheck - currentMoveCandidate, currentMoveCandidate) ||
					firstColumnEdgeCheck(coordinateToCheck - currentMoveCandidate, currentMoveCandidate)) {
					break;
				}
				
				//if tile is not occupied add it as a move
				if(!board.getTile(coordinateToCheck).isTileOccupied()) {
					moves.add(new NormalMove(board, this, coordinateToCheck));
					
				//if tile is occupied  and the other piece is the other teams add the attack move
				}else if(board.getTile(coordinateToCheck).isTileOccupied()) {
					Piece otherPiece = board.getTile(coordinateToCheck).getPiece();
					if(this.pieceColor != otherPiece.getPieceColor()) {
						moves.add(new AttackMove(board, this, coordinateToCheck, otherPiece));
					}
					break;
				}
				//adds to the next move to check
				coordinateToCheck += currentMoveCandidate;
			}
		}
		return moves;
	}
	
	/*
	 * checks to see if is in first column and move offsets that we can not move to 
	 */
	public boolean firstColumnEdgeCheck(int piecePos, int candidateOffset) {
		return Board.FIRST_COLUMN[piecePos] && (candidateOffset == -9 || candidateOffset == 7);
	}
	
	/*
	 * checks to see if is in eighth column and move offsets that we can not move to 
	 */
	public boolean eigthColumnEdgeCheck(int piecePos, int candidateOffset) {
		return Board.EIGTH_COLUMN[piecePos] && (candidateOffset == -7 || candidateOffset == 9);
	}

	/*
	 * going to be used to move the piece
	 */
	@Override
	public Piece movePiece(Move move) {
		return new Bishop(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), false);
	}

	@Override
	public String toString() {
		return "B";
	}
}
