package pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import board.Board;
import board.Move;
import board.Move.AttackMove;
import board.Move.KingCheckMove;
import board.Move.NormalMove;
import util.PieceColor;

/*
* class to represent the king to create a new king piece
*/
public class King extends Piece{

	public King(int piecePos, PieceColor pieceColor) {
		super(piecePos, pieceColor, true);
	}
	public King(int piecePos, PieceColor pieceColor, boolean isFirstMove) {
		super(piecePos, pieceColor, isFirstMove);
	}
	
	final int[] MOVE_CANDIDATE_DIRECTION = {-9, -8, -7, -1, 1, 7, 8,9};
	
	/*
	 * Method that calculates the moves possible by this piece
	 * needs the board being used to be passed in
	 * returns a collection of move objects
	 */
	@Override
	public Collection<Move> calculateMoves(Board board) {
		//List that will contain all possible moves
		List<Move> moves = new ArrayList<>();

		//loops through all possible move integers
		for(int currentMoveCandidate : MOVE_CANDIDATE_DIRECTION)
		{
			int coordinateToCheck = this.piecePos + currentMoveCandidate;
			int counter =0;

			//loop as long as the coordinate is valid and the number of allowed moves for the piece is not exceeded
			while(board.isValidCoord(coordinateToCheck) && counter <1)
			{
				//checks to see if piece is on the edge and breaks out if so
				if(firstColumnEdgeCheck(this.piecePos,currentMoveCandidate) || eigthColumnEdgeCheck(this.piecePos, currentMoveCandidate))
					break;

				//stops the vector of travel on the edge
				if(eigthColumnEdgeCheck(coordinateToCheck - currentMoveCandidate, currentMoveCandidate) ||
						firstColumnEdgeCheck(coordinateToCheck - currentMoveCandidate, currentMoveCandidate)) {
					break;
				}

				//if the coordinate that we're checking is empty, then we add this to the list of possible moves
				if (!board.getTile(coordinateToCheck).isTileOccupied())
				{
					moves.add(new NormalMove(board, this, coordinateToCheck));
					counter = 1;
				}


				//if tile is occupied  and the other piece is the other teams add the attack move
				else if(board.getTile(coordinateToCheck).isTileOccupied())
				{
					Piece otherPiece = board.getTile(coordinateToCheck).getPiece();

					if(this.pieceColor != otherPiece.getPieceColor())
					{
						moves.add(new AttackMove(board, this, coordinateToCheck, otherPiece));
					}else {
						moves.add(new KingCheckMove(board, this, coordinateToCheck));
					}
					break;
				}

				//System.out.println(coordinateToCheck);

				//adds to the next move to check
				coordinateToCheck += currentMoveCandidate;
			}

		}
		return moves;
	}

	/*
	 * going to be used to move the piece
	 */
	@Override
	public Piece movePiece(Move move) {
		return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceColor(), false);
	}
	
	@Override
	public String toString() {
		return "K";
	}
	
	public boolean firstColumnEdgeCheck(int piecePos, int candidateOffset)
	{
		return Board.FIRST_COLUMN[piecePos] && (candidateOffset == -1 || candidateOffset == -9 || candidateOffset == 7);
	}



	/*
	 * checks to see if is in eighth column and move offsets that we can not move to
	 */
	public boolean eigthColumnEdgeCheck(int piecePos, int candidateOffset)
	{
		return Board.EIGTH_COLUMN[piecePos] && (candidateOffset == 1 || candidateOffset == 9 || candidateOffset == -7 );
	}

}
