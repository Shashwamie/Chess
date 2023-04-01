package player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import board.Board;
import board.Move;
import pieces.King;
import pieces.Piece;
import util.PieceColor;

/*
 * abstract class for player
 */
public abstract class Player {

	Board board;
	King playerKing;
	Collection<Move> legalMoves;
	Collection<Move> opponentMoves;
	boolean isInCheck;
	
	Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves){
		this.board = board;
		this.playerKing = calculatePlayersKing();
		this.legalMoves = legalMoves;
		this.opponentMoves = opponentMoves;
		this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePos(), opponentMoves).isEmpty();
	}
	
	/*
	 * get active pieces is a collection of all this players pieces on the board
	 */
	public abstract Collection<Piece> getActivePieces();
	
	/*
	 * this returns the team of this player
	 */
	public abstract PieceColor getTeam();
	
	/*
	 * this should return the the opponents class
	 */
	public abstract Player getOpponent();
	
	/*
	 * this returns this players kind piece
	 */
	public King getPlayerKing() {
		return this.playerKing;
	}
	
	/*
	 * this is a list off all of this players legal moves
	 */
	public Collection<Move> getLegalMoves(){
		return this.legalMoves;
	}
	
	/*
	 * this gets the attack moves on a specific tile
	 */
	public static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves){
		List<Move> attackMoves = new ArrayList<>();
		for(Move move : moves) {
			if(piecePosition == move.getDestinationCoordinate()) {
				attackMoves.add(move);
			}
		}
		return attackMoves;
	}
	
	/*
	 * checking to see if king has escape moves
	 */
	public boolean hasEscapeMoves() {
		for(Move kingsMove : this.playerKing.calculateMoves(board)) {
			if(calculateAttacksOnTile(kingsMove.getDestinationCoordinate(), this.opponentMoves).isEmpty()) {
				System.out.println("escape");
				return true;
			}
		}
		return false;
	}
	
	/*
	 * This gets the king of this player
	 */
	private King calculatePlayersKing() {
		for(Piece piece : getActivePieces()) {
			if(piece.getPieceType().isKing()) {
				return (King) piece;
			}
		}
		return null;
	}
	
	public boolean isMoveLegal(Move move) {
		return this.legalMoves.contains(move);
	}
	public boolean isInCheck() {
		return this.isInCheck;
	}
	public boolean isInCheckMate() {
		return this.isInCheck && !hasEscapeMoves();
	}
	public boolean isInStaleMate() {
		return !this.isInCheck && !hasEscapeMoves();
	}
}
