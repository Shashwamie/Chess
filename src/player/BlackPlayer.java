package player;

import java.util.Collection;

import board.Board;
import board.Move;
import pieces.Piece;
import util.PieceColor;

/*
 * class for black player
 */
public class BlackPlayer extends Player{

	public BlackPlayer(Board board, Collection<Move> blackStandardLegalMoves, Collection<Move> whiteStandardLegalMoves) {
		super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return board.getBlackPieces();
	}

	@Override
	public PieceColor getTeam() {
		return PieceColor.BLACK;
	}

	@Override
	public Player getOpponent() {
		return board.whitePlayer();
	}

}
